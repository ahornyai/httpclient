import me.ahornyai.httpclient.HttpClient;
import me.ahornyai.httpclient.HttpMethod;
import me.ahornyai.httpclient.HttpRequest;
import me.ahornyai.httpclient.requests.HttpGet;
import me.ahornyai.httpclient.utils.RequestBuilder;
import org.junit.Test;

import java.util.HashMap;

public class GetRequests extends RequestTest {

    @Test
    public void get() {
        runTest("simple get");

        HttpClient client = new HttpClient();
        HashMap<String, String> params = new HashMap<>();
        params.put("param1", "1");
        params.put("param2", "2");
        HashMap<String, String> headers = new HashMap<>();
        headers.put("header1", "1");
        headers.put("header2", "2");

        HttpGet get = new HttpGet("https://postman-echo.com/get", params, null, headers);
        get.run(client, (response, status) -> System.out.println("response: " + response + " \nstatus: " + status), (e) -> {}, false);
    }

    @Test
    public void getWithBuilder() {
        runTest("get with builder");

        HttpClient client = new HttpClient();
        HttpRequest request = new RequestBuilder()
                .method(HttpMethod.GET)
                .url("https://postman-echo.com/get")
                .addUrlParam("param1", "1")
                .addUrlParam("param2", "2")
                .addHeader("header1", "1")
                .addHeader("header2", "2")
                .build();

        request.run(client, (response, status) -> System.out.println("response: " + response + " \nstatus: " + status), (e) -> {}, false);
    }

    @Test
    public void asyncGet() throws InterruptedException {
        runTest("async get");
        AsyncJUnit asyncJUnit = new AsyncJUnit();

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
            asyncJUnit.finish();
        }, (e) -> {
            asyncJUnit.finish();
            e.printStackTrace();
        },true);
        System.out.println("Waiting for complete async request...");
        asyncJUnit.waitForFinish();
    }
}
