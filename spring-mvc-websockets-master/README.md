spring-mvc-websockets
===============

Code for http://kimrudolph.de/blog/spring-4-websockets-tutorial/


- ## 实现功能

服务端生成随机数，在客户端以图标的形式展示，类似于实现股票实时更新的功能

- ## 教程翻译

本教程的目的是实现一个向在线订阅者发送随机数的服务，主要集成了一下功能：

- 订阅者通过[ScokJS](https://github.com/sockjs/sockjs-client)打开一个tcp套接字，连接到应用
- 应用产生随机数并发送给所有订阅者
- 订阅者通过[Highcharts](http://www.highcharts.com/)渲染收到的数据

![最终实现效果](http://kimrudolph.de/images/websockets_highchart.jpg)

1. web应用配置

首先基于spring创建一个web应用，重点是开启setAsyncSupported(true)，
使web容器（tomcat等）可以执行异步操作，代码如下：

``` java
package de.kimrudolph.tutorials.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(final ServletContext context) throws ServletException {

        final AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();

        root.scan("de.kimrudolph.tutorials");

        context.addListener(new ContextLoaderListener(root));

        final ServletRegistration.Dynamic appServlet = context.addServlet(
            "appServlet",
            new DispatcherServlet(new GenericWebApplicationContext()));
        appServlet.setAsyncSupported(true);
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/*");
    }

}
An @EnableScheduling annotation is added as it is needed for the random data generator.

package de.kimrudolph.tutorials.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@EnableScheduling
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void configureDefaultServletHandling(
        final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}

```

订阅者需要认证，为了测试这里使用`user:password`配置即可：

```java
package de.kimrudolph.tutorials.configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests().anyRequest().authenticated();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth)
        throws Exception {
        auth
            .inMemoryAuthentication()
            .withUser("user").password("password");
    }
}
```
通过`WebSocketMessageBrokerConfigurer`配置endpoint（/random），即客户端websocket连接的地址：

```java
package de.kimrudolph.tutorials.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint("/random").withSockJS();
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

}
```
2. 随机数生成器

通过`@scheduled`注解配置`sendDataUpdates()`方法，使其每隔一秒生成一个随机数并广播给订阅者，
订阅者所订阅的主题为`/data`，代码如下：
```java
package de.kimrudolph.tutorials.utils;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RandomDataGenerator implements
    ApplicationListener<BrokerAvailabilityEvent> {

    private final MessageSendingOperations<String> messagingTemplate;

    @Autowired
    public RandomDataGenerator(
        final MessageSendingOperations<String> messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void onApplicationEvent(final BrokerAvailabilityEvent event) {
    }

    @Scheduled(fixedDelay = 1000)
    public void sendDataUpdates() {

        this.messagingTemplate.convertAndSend(
            "/data", new Random().nextInt(100));

    }
}
```
3. Javascript代码

客户端的javascript代码主要包含两部分，首先是highchart的代码：

```javascript
var randomData;

$('#randomDataChart').highcharts({
  chart : {
    type : 'line',
    events : {
      load : function() {
        randomData = this.series[0];
      }
    }
  },
  title : {
    text : false
  },
  xAxis : {
    type : 'datetime',
    minRange : 60 * 1000
  },
  yAxis : {
    title : {
      text : false
    }
  },
  legend : {
    enabled : false
  },
  plotOptions : {
    series : {
      threshold : 0,
      marker : {
        enabled : false
      }
    }
  },
  series : [ {
    name : 'Data',
      data : [ ]
    } ]
});
```
然后是SockJS和Stomp的代码，这一部分主要是是由SockJS建立websocket连接，用Stomp来建立订阅，代码如下：

```javascript
...
var socket = new SockJS('/spring-mvc-websockets/random');
var client = Stomp.over(socket);

client.connect('user', 'password', function(frame) {

  client.subscribe("/data", function(message) {
    var point = [ (new Date()).getTime(), parseInt(message.body) ];
    var shift = randomData.data.length > 60;
    randomData.addPoint(point, true, shift);
  });

});
```
4. html文件

```html
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-us" lang="en-us">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>WebSocket chart example</title>
  </head>
  <body>
    <div id="randomDataChart"></div>
  </body>
  <script type="text/javascript" src="resources/jquery.js" /></script>
  <script type="text/javascript" src="resources/sockjs.js" /></script>
  <script type="text/javascript" src="resources/highcharts.js" /></script>
  <script type="text/javascript" src="resources/stomp.js" /></script>
  <script type="text/javascript" src="resources/application.js" /></script>
</html>
```


