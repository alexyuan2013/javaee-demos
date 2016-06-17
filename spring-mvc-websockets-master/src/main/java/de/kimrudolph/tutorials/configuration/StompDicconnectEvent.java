package de.kimrudolph.tutorials.configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class StompDicconnectEvent implements ApplicationListener<SessionDisconnectEvent>{
	
	private final Log logger = LogFactory.getLog(StompConnectEvent.class);

	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
		event.getSessionId();
	    System.out.println("Disconnect event [sessionId: " + event.getSessionId() );
	    logger.debug("Disconnect event [sessionId: " + event.getSessionId());
	}

}
