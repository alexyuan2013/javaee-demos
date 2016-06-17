package de.kimrudolph.tutorials.configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

@Component
public class StompConnectEvent implements ApplicationListener<SessionConnectEvent> {

	private final Log logger = LogFactory.getLog(StompConnectEvent.class);
	@Override
	public void onApplicationEvent(SessionConnectEvent event) {
		StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
		 
        String  company = sha.getNativeHeader("company").get(0);
        System.out.println("Connect event [sessionId: " + sha.getSessionId() +"; company: "+ company + " ]");
        logger.debug("Connect event [sessionId: " + sha.getSessionId() +"; company: "+ company + " ]");
	}

}
