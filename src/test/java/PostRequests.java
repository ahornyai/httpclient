import me.ahornyai.httpclient.HttpClient;
import me.ahornyai.httpclient.HttpMethod;
import me.ahornyai.httpclient.HttpRequest;
import me.ahornyai.httpclient.requests.HttpPost;
import me.ahornyai.httpclient.utils.RequestBuilder;
import org.junit.Test;

import java.util.HashMap;

public class PostRequests extends RequestTest {

    @Test
    public void post() {
        runTest("simple post");

        HttpClient client = new HttpClient();
        HashMap<String, String> params = new HashMap<>();
        params.put("param1", "1");
        params.put("param2", "2");
        HashMap<String, String> headers = new HashMap<>();
        headers.put("header1", "1");
        headers.put("header2", "2");

        HttpPost post = new HttpPost("https://postman-echo.com/post", params, params, headers);
        post.run(client, (response, status) -> System.out.println("response: " + response + " \nstatus: " + status), (e) -> {}, false);
    }

    @Test
    public void postWithBuilder() {
        runTest("post with builder");

        HttpClient client = new HttpClient();
        HttpRequest request = new RequestBuilder()
                .method(HttpMethod.POST)
                .url("https://postman-echo.com/post")
                .addUrlParam("param1", "1")
                .addBodyParam("param2", "2")
                .addHeader("header1", "1")
                .addHeader("header2", "2")
                .build();

        request.run(client, (response, status) -> System.out.println("response: " + response + " \nstatus: " + status), (e) -> {}, false);
    }

    @Test
    public void asyncPost() throws InterruptedException {
        runTest("async post");
        AsyncJUnit asyncJUnit = new AsyncJUnit();

        HttpClient client = new HttpClient();
        HttpRequest request = new RequestBuilder()
                .method(HttpMethod.POST)
                .url("https://postman-echo.com/post")
                .addUrlParam("param1", "1")
                .addBodyParam("param2", "2")
                .addHeader("header1", "1")
                .addHeader("header2", "2")
                .build();

        request.run(client, (response, status) -> {
            System.out.println("response: " + response + " \nstatus: " + status);
            asyncJUnit.finish();
        }, (e) ->{
            e.printStackTrace();
            asyncJUnit.finish();
        }, true);
        System.out.println("Waiting for complete async request...");
        asyncJUnit.waitForFinish();
    }
}
