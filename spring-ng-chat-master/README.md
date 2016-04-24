Using WebSockets with Spring, AngularJS and SockJS
===
This application explains how to write a simple Spring web application that communicates through WebSockets by using the spring-websocket framework and the SockJS client library in combination with AngularJS.

* [View the tutorial](http://g00glen00b.be/spring-angular-sockjs)


- ##实现功能

聊天室功能，类似于群聊，是一种发布订阅的模式，所有通过websocket连接到服务的客户端发送信息，服务端接收到以后，广播给所有的订阅该服务的客户端，所有服务端收到的内容相同。这个功能类似于目前做篮球直播的功能，实际应该也是用的websocket。

