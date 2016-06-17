package de.kimrudolph.tutorials.configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class StompConnectedEvent implements ApplicationListener<SessionConnectedEvent>{

	private final Log logger = LogFactory.getLog(StompConnectedEvent.class);
	@Override
	public void onApplicationEvent(SessionConnectedEvent event) {
		StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        System.out.println("Connected event [sessionId: " + sha.getSessionId());
        logger.debug("Connected event [sessionId: " + sha.getSessionId());
	}

}
