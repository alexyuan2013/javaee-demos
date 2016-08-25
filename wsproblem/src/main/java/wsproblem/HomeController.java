package wsproblem;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired private SimpMessagingTemplate simpMessagingTemplate;

	@RequestMapping(method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	@MessageMapping("/greeting")
	public void greeting(Principal principal) {
		String reply = "hello " + principal.getName();
		System.out.println("sending " + reply);
		//simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/reply", reply);
		simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/queue/reply", reply);
	}
}
