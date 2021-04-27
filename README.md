# HTTP Client
Easy to use Http client.
# FEATURES
- Builder
- Custom user agent
- URL parameters
- Body parameters

> METHODS:
GET, POST, PUT, DELETE

# USAGE
**Simple GET request**
```java
import me.ahornyai.httpclient.HttpClient;  
import me.ahornyai.httpclient.requests.HttpGet;
import java.util.HashMap;

public void get() {
	HttpClient client = new HttpClient(); //default user agent is random
	HashMap<String, String> params = new HashMap<>();
	params.put("param1", "1");
	params.put("param2", "2");

	HashMap<String, String> headers = new HashMap<>();
	headers.put("header1", "1");
	headers.put("header2", "2");

	HttpGet get = new HttpGet("https://postman-echo.com/get", params, null, headers);  //url, params, body params, headers

	get.run(client, (response, status) -> System.out.println("response: " + response + " \nstatus: " + status), (e) -> {
		//error callback
	}, false); //async boolean
}
```
**GET request with builder**
```java
import me.ahornyai.httpclient.HttpClient;  
import me.ahornyai.httpclient.HttpRequest;
import me.ahornyai.httpclient.utils.RequestBuilder;

public void getWithBuilder() {
      HttpClient client = new HttpClient();  
      HttpRequest request = new RequestBuilder()  
	     .method(HttpMethod.GET)  
	     .url("https://postman-echo.com/get")  
	     .addUrlParam("param1", "1")  
	     .addUrlParam("param2", "2")  
	     .addHeader("header1", "1")  
	     .addHeader("header2", "2")  
	     .build();  

      request.run(client, (response, status) -> System.out.println("response: " + response + " \nstatus: " + status), (e) -> {
	      //error callback
      }, false); //async
}
```
**Asynchronized GET request**
```java
public void asyncGet() {
      HttpClient client = new HttpClient();  
      HttpRequest request = new RequestBuilder()  
	     .method(HttpMethod.GET)  
	     .url("https://postman-echo.com/get")  
	     .addUrlParam("param1", "1")  
	     .addUrlParam("param2", "2")  
	     .addHeader("header1", "1")  
	     .addHeader("header2", "2")  
	     .build();  

     request.run(client, (response, status) -> {
	System.out.println("response: " + response + " \nstatus: " + status);
     }, (e) -> {
	//error handling
     }, true); //async

     System.out.println("Waiting for complete async request..."); 
}
```
**All other examples (custom user agent, more methods, error handling, etc... in: [test folder](https://github.com/ahornyai/httpclient/tree/master/src/test/java))**

# LICENSE

    MIT License

    Copyright (c) 2020 Alex Hornyai
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.

