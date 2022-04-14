package com.example.centerService;

import static com.example.centerService.Event.CenterIncomingEvent.AVAILABLE_SHOT_ATTR;
import static com.example.centerService.Event.CenterIncomingEvent.INVENTORY_ID_ATTR;
import static com.example.centerService.Event.CenterIncomingEvent.LOT_ATTR;
import static com.example.centerService.Event.CenterIncomingEvent.REMINDER_DATE_ATTR;
import static com.example.centerService.Event.CenterIncomingEvent.USER_ID_ATTR;
import static com.example.centerService.Event.CenterIncomingEvent.VACCINE_ID_ATTR;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.centerService.Event.CenterIncomingEvent;
import com.example.centerService.Event.KafkaUtil;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class CenterServiceApplication {

	private static ApplicationContext applicationContext;
	
	public static void main(String[] args) {
		applicationContext = SpringApplication.run(CenterServiceApplication.class, args);
		
		CenterIncomingEvent centerIncomingEvent = applicationContext.getBean(CenterIncomingEvent.class);
		centerIncomingEvent.register();
		
		
		// testing info.
		Map<String, Object> msg = new HashMap<>();
		msg.put(REMINDER_DATE_ATTR, Date.valueOf("2022-04-14"));
		msg.put(AVAILABLE_SHOT_ATTR, 101);
		log.info("JSON sample for publishReminder msg: {}", KafkaUtil.mapToPayload(msg));
		
		// inventoryId:Integer,vaccineId:Integer, lot:String,userId:Integer
		msg.clear();
		msg.put(INVENTORY_ID_ATTR, 1);
		msg.put(VACCINE_ID_ATTR, 1);
		msg.put(LOT_ATTR, "lot attr");
		msg.put(USER_ID_ATTR, 1);
		log.info("JSON sample for shotAdministrated msg: {}", KafkaUtil.mapToPayload(msg));
	}

}
