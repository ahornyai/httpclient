import me.ahornyai.httpclient.HttpClient;
import me.ahornyai.httpclient.HttpMethod;
import me.ahornyai.httpclient.HttpRequest;
import me.ahornyai.httpclient.requests.HttpPut;
import me.ahornyai.httpclient.utils.RequestBuilder;
import org.junit.Test;

import java.util.HashMap;

public class PutRequests extends RequestTest {

    @Test
    public void put() {
        runTest("simple put");

        HttpClient client = new HttpClient();
        HashMap<String, String> params = new HashMap<>();
        params.put("param1", "1");
        params.put("param2", "2");
        HashMap<String, String> headers = new HashMap<>();
        headers.put("header1", "1");
        headers.put("header2", "2");

        HttpPut get = new HttpPut("https://postman-echo.com/put", params, params, headers);
        get.run(client, (response, status) -> System.out.println("response: " + response + " \nstatus: " + status), (e) -> {}, false);
    }

    @Test
    public void putWithBuilder() {
        runTest("put with builder");

        HttpClient client = new HttpClient();
        HttpRequest request = new RequestBuilder()
                .method(HttpMethod.PUT)
                .url("https://postman-echo.com/put")
                .addUrlParam("param1", "1")
                .addBodyParam("param2", "2")
                .addHeader("header1", "1")
                .addHeader("header2", "2")
                .build();

        request.run(client, (response, status) -> System.out.println("response: " + response + " \nstatus: " + status), (e) -> {}, false);
    }

    @Test
    public void asyncPut() throws InterruptedException {
        runTest("async put");
        AsyncJUnit asyncJUnit = new AsyncJUnit();

        HttpClient client = new HttpClient();
        HttpRequest request = new RequestBuilder()
                .method(HttpMethod.PUT)
                .url("https://postman-echo.com/put")
                .addUrlParam("param1", "1")
                .addBodyParam("param2", "2")
                .addHeader("header1", "1")
                .addHeader("header2", "2")
                .build();

        request.run(client, (response, status) -> {
            System.out.println("response: " + response + " \nstatus: " + status);
            asyncJUnit.finish();
        }, (e) -> {
            asyncJUnit.finish();
            e.printStackTrace();
        },true);
        System.out.println("Waiting for complete async request...");
        asyncJUnit.waitForFinish();
    }
}
