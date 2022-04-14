package com.example.centerService.service;

import static com.example.centerService.Event.CenterIncomingEvent.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.centerService.Event.KafkaProducerEventService;
import com.example.centerService.Event.KafkaUtil;
import com.example.centerService.model.Center;
import com.example.centerService.model.Inventory;
import com.example.centerService.model.Shipment;
import com.example.centerService.model.Vaccine;
import com.example.centerService.repository.CenterRepo;
import com.example.centerService.repository.InventoryRepo;
import com.example.centerService.repository.VaccineRepo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Service {
	
	@Autowired
	private CenterRepo  centerRepo;
	
	@Autowired
	private VaccineRepo vaccineRepo;
	
	@Autowired
	private InventoryRepo inventoryRepo;
	
	@Autowired
	private KafkaProducerEventService<Long> eventProducer;
	
	@Value("${spring.application.event.outgoing.topic.restock}")
	private String vaccineInventoryTopic;
	
	@Value("${spring.application.event.outgoing.topic.reminder}")
	private String publishReminderTopic;
	
	// This topic is for testing only.
	@Value("${spring.application.event.incoming.topic.shotAdministrated}")
	private String shotAdministratedTopic;
	
	private final Random rand = new Random();
	
	public Center add(Center center) {
		return centerRepo.save(center);
	}
	
	public Vaccine add(Vaccine vaccine) {
		return vaccineRepo.save(vaccine);
	}
	
	public List<Center> listCenter() {
		return centerRepo.findAll();
	}
	
	public Optional<Center> getCeneter(long id) {
		return centerRepo.findById(id);
	}
	
	public List<Vaccine> listVaccine() {
		return vaccineRepo.findAll();
	}
	
	public Optional<Vaccine> getVaccine(long id) {
		return vaccineRepo.findById(id);
	}
	
	public void restock(Shipment shipment) {
		try {
			// update db inventory.
			Vaccine vaccine = vaccineRepo.findById(shipment.getVaccineId()).orElseThrow();

			Center center = centerRepo.findById(assignShipmentToCenter()).orElseThrow();

			Inventory inventory = Inventory.of(shipment);
			inventory.setCenter(center);
			inventory.setVaccine(vaccine);

			inventoryRepo.save(inventory);

			// dispatch event to other service.
			// inventoryId:Long,vaccineId:Long, lot:String, lotSize:Integer, centerId:Long
			Map<String, Object> msg = new HashMap<>();
			msg.put(INVENTORY_ID_ATTR, inventory.getId());
			msg.put(VACCINE_ID_ATTR, inventory.getVaccine().getId());
			msg.put(LOT_ATTR, inventory.getLot());
			msg.put(LOT_SIZE_ATTR, inventory.getLotSize());
			msg.put(CENTER_ID_ATTR, inventory.getCenter().getId());

			String payload = KafkaUtil.mapToPayload(msg);
			eventProducer.sendEvent(vaccineInventoryTopic, inventory.getId(), payload);
		} catch (NoSuchElementException nse) {
			log.error("failed to find either corresponding vaccine or center, shipment:{}", shipment);
			throw new RuntimeException("server error."); // convert to server exception for error handling later.
		}
	}
	
	private long assignShipmentToCenter() {
		List<Center> centers = centerRepo.findAll();
		int selectedCenter = rand.nextInt(centers.size());
		return centers.get(selectedCenter).getId();     
	}
	
	public boolean publishReminder(final Date date) {
		long availableShot = inventoryRepo.findAll()
												.stream()
												.filter(i-> i.getExpiration().after(date))
												.collect(Collectors.summingLong(Inventory::getAvailable));
		if(availableShot < 1) {
			log.warn("There is no more available vaccine.");
			return false;
		}
		
		// send reminder event to appointment service.
		Map<String, Object> msgMap = new HashMap<>();
		msgMap.put(REMINDER_DATE_ATTR, date);
		msgMap.put(AVAILABLE_SHOT_ATTR, availableShot);
		
		String payload = KafkaUtil.mapToPayload(msgMap);
		eventProducer.sendEvent(publishReminderTopic, date.getTime(), payload);
		return true;
	}
	
	public void testShotAdministratedFeature() {
		Map<String, Object> msg = new HashMap<>();
		
		msg.put(INVENTORY_ID_ATTR, 1);
		msg.put(VACCINE_ID_ATTR, 1);
		msg.put(LOT_ATTR, "lot attr");
		msg.put(USER_ID_ATTR, 1);
		
		String payload = KafkaUtil.mapToPayload(msg);
		eventProducer.sendEvent(shotAdministratedTopic, 1L, payload);
	}
}
