<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ws problem</title>
</head>
<body>
	<a href="#" id="send">Request user specific msg</a><br/><br/>
	Received Message To Me Only: <span id="received"></span><br/><br/>
	
	<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
	<!--  <script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>-->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.1/sockjs.js"></script>
	<script src="resources/stomp.js"></script>
	<script>
		$(document).ready(function() {
			var socket = new SockJS("/ws");
			var stompClient = Stomp.over(socket);
			
			// connect via websocket. username password here should be ignore
			// as we're using spring security
			stompClient.connect('guest', 'guest', function(frame1) {
				// connect callback
				// subscribe to 
				//stompClient.subscribe('/user/' + 'bob' + '/reply', function(frame2) {
				stompClient.subscribe('/user/queue/reply', function(frame2) {
					var msg = JSON.parse(frame2.body);
					$('#received').html(msg);
				});
			}, function() {
				// error callback
				alert(error.headers.message);
			});
			
			// Send websocket to server when clicked
			$('#send').click(function(e) {
				e.preventDefault();
				stompClient.send('/app/greeting');
			});
		});
	</script>
</body>
</html>