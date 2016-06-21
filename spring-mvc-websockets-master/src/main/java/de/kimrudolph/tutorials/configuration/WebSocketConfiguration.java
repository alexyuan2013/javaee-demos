package de.kimrudolph.tutorials.configuration;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint("/random").withSockJS();
        registry.addEndpoint("/chat").withSockJS().setInterceptors(new HandshakeInterceptor());
    }

    @Override
    public void configureClientInboundChannel(
        final ChannelRegistration registration) {
    }

    @Override
    public void configureClientOutboundChannel(
        final ChannelRegistration registration) {
    }

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
    }

	@Override
	public boolean configureMessageConverters(List<MessageConverter> arg0) {
		// must return "true", from the stackoverflow
		// (http://stackoverflow.com/questions/25738683/spring-4-autowired-failed)
		return true;
	}

	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration arg0) {
		// TODO Auto-generated method stub
		
	}

}
