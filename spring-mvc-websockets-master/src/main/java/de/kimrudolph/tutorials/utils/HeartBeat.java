package de.kimrudolph.tutorials.utils;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HeartBeat implements
    ApplicationListener<BrokerAvailabilityEvent> {

    private final MessageSendingOperations<String> messagingTemplate;

    @Autowired
    public HeartBeat(
        final MessageSendingOperations<String> messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void onApplicationEvent(final BrokerAvailabilityEvent event) {
    }

    @Scheduled(fixedDelay = 10000)
    public void sendDataUpdates() {

    	//System.out.println("heart beat");
    	Date now = new Date();
    	String timeStamp = ""  + now.getTime();
    	//JSONObject obj = new JSONObject();
    	//String heart = "{'T': '" + timeStamp + "', 'V':'" + timeStamp + "', 'D': '" + timeStamp + "'}";
        HeartMessage message = new HeartMessage();
        message.setT(timeStamp);
        message.setD(timeStamp);
        message.setV(timeStamp);
    	this.messagingTemplate.convertAndSend(
            "/topic/message", message);

    }
}