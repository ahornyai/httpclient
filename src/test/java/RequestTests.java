import me.ahornyai.httpclient.HttpClient;
import me.ahornyai.httpclient.HttpMethod;
import me.ahornyai.httpclient.HttpRequest;
import me.ahornyai.httpclient.requests.HttpGet;
import me.ahornyai.httpclient.requests.HttpPost;
import me.ahornyai.httpclient.utils.RequestBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

public class RequestTests {

    /*
     * GET
     */

    @Test
    public void get() throws IOException {
        HttpClient client = new HttpClient();
        HashMap<String, String> params = new HashMap<>();
        params.put("param1", "1");
        params.put("param2", "2");
        HashMap<String, String> headers = new HashMap<>();
        headers.put("header1", "1");
        headers.put("header2", "2");

        HttpGet get = new HttpGet("https://postman-echo.com/get", params, headers);
        get.run(client, (response, status) -> System.out.println("response: " + response + " \nstatus: " + status), false);
    }

    @Test
    public void getWithBuilder() throws IOException {
        HttpClient client = new HttpClient();
        HttpRequest request = new RequestBuilder()
                .method(HttpMethod.GET)
                .url("https://postman-echo.com/get")
                .addParam("param1", "1")
                .addParam("param2", "2")
                .addHeader("header1", "1")
                .addHeader("header2", "2")
                .build();

        request.run(client, (response, status) -> System.out.println("response: " + response + " \nstatus: " + status), false);
    }

    @Test
    public void asyncGet() throws IOException, InterruptedException {
        AsyncJUnit asyncJUnit = new AsyncJUnit();

        HttpClient client = new HttpClient();
        HttpRequest request = new RequestBuilder()
                .method(HttpMethod.GET)
                .url("https://postman-echo.com/get")
                .addParam("param1áé", "úúőé")
                .addParam("param2", "2")
                .addHeader("header1", "1")
                .addHeader("header2", "2")
                .build();

        request.run(client, (response, status) -> {
            System.out.println("response: " + response + " \nstatus: " + status);
            asyncJUnit.finish();
        }, true);
        System.out.println("Waiting for complete async request...");
        asyncJUnit.waitForFinish();
    }

    /*
     * POST
     */

    @Test
    public void post() throws IOException {
        HttpClient client = new HttpClient();
        HashMap<String, String> params = new HashMap<>();
        params.put("param1", "1");
        params.put("param2", "2");
        HashMap<String, String> headers = new HashMap<>();
        headers.put("header1", "1");
        headers.put("header2", "2");

        HttpPost post = new HttpPost("https://postman-echo.com/post", params, headers);
        post.run(client, (response, status) -> System.out.println("response: " + response + " \nstatus: " + status), false);
    }

    @Test
    public void postWithBuilder() throws IOException {
        HttpClient client = new HttpClient();
        HttpRequest request = new RequestBuilder()
                .method(HttpMethod.POST)
                .url("https://postman-echo.com/post")
                .addParam("param1", "1")
                .addParam("param2", "2")
                .addHeader("header1", "1")
                .addHeader("header2", "2")
                .build();

        request.run(client, (response, status) -> System.out.println("response: " + response + " \nstatus: " + status), false);
    }

    @Test
    public void asyncPost() throws IOException, InterruptedException {
        AsyncJUnit asyncJUnit = new AsyncJUnit();

        HttpClient client = new HttpClient();
        HttpRequest request = new RequestBuilder()
                .method(HttpMethod.POST)
                .url("https://postman-echo.com/post")
                .addParam("param1", "1")
                .addParam("param2", "2")
                .addHeader("header1", "1")
                .addHeader("header2", "2")
                .build();

        request.run(client, (response, status) -> {
            System.out.println("response: " + response + " \nstatus: " + status);
            asyncJUnit.finish();
        }, true);
        System.out.println("Waiting for complete async request...");
        asyncJUnit.waitForFinish();
    }

    /*
     * HTTPCLIENT
     */

    @Test
    public void customUserAgent() throws IOException {
        HttpClient client = new HttpClient("test user agent");
        HttpRequest request = new RequestBuilder()
                .method(HttpMethod.GET)
                .url("https://postman-echo.com/get")
                .build();

        request.run(client, (response, status) -> System.out.println("response: " + response + " \nstatus: " + status), false);
    }

    /*
     * UTILS
     */

    private class AsyncJUnit {
        boolean finished = false;

        void finish() {
            finished = true;
        }

        void waitForFinish() throws InterruptedException {
            while (!finished) Thread.sleep(1);
        }
    }

}
