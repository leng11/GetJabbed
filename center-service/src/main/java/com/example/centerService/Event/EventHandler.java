package com.example.centerService.Event;

import java.util.Map;

public interface EventHandler {
	public void processHandler(final Map<String, Object> headers, final String payload);
}
