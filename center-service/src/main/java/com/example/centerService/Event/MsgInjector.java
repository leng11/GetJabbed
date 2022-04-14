package com.example.centerService.Event;

import static com.example.centerService.Event.CenterIncomingEvent.AVAILABLE_SHOT_ATTR;
import static com.example.centerService.Event.CenterIncomingEvent.REMINDER_DATE_ATTR;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MsgInjector {
	
	@Autowired
	private KafkaProducerEventService<Long> eventProducer;

	public static void main(String[] args) {
		Map<String, Object> msg = new HashMap<>();
		msg.put(REMINDER_DATE_ATTR, Date.valueOf("2022-04-14"));
		msg.put(AVAILABLE_SHOT_ATTR, 101);
		log.info("JSON sample for publishReminder msg: {}", KafkaUtil.mapToPayload(msg));
		
	}
}
