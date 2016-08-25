To run this project use jdk 1.7 and maven 3.1 and run the command

`mvn jetty:run`

Steps to reproduce problem:

* Run the project using `mvn jetty:run`
* Open browser to http://localhost:8080, a login page will appear
* Enter either of these username/password: bob / test, jim / test
* Open a different browser (or incognito) and login with a different user
* On the first user, click "Request user specific msg"
* Expected: a message "hello {username}" appears on the "Recieved message to me only" for this user only
* Actual: no message appears