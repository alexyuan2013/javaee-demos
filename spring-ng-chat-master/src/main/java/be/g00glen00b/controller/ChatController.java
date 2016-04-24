package be.g00glen00b.controller;

import java.util.Date;

import org.slf4j.*;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import be.g00glen00b.dto.*;

@Controller
@RequestMapping("/")
public class ChatController {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @RequestMapping(method = RequestMethod.GET)
  public String viewApplication() {
    return "index";
  }

  @MessageMapping("/chat")
  @SendTo("/topic/message")
  public OutputMessage sendMessage(Message message) {
    logger.info("Message sent");
    return new OutputMessage(message, new Date());
  }
  
  @Scheduled(fixedDelay = 1000)
  public void heartBeat(){
	  
  }
  /*
  @MessageMapping("/chat2")
  @SendTo("/topic/message2")
  public OutputMessage sendMessage1(Message message) {
    logger.info("Message2 sent");
    return new OutputMessage(message, new Date());
  }*/
  
}
