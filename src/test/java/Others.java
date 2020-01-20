import me.ahornyai.httpclient.HttpClient;
import me.ahornyai.httpclient.HttpMethod;
import me.ahornyai.httpclient.HttpRequest;
import me.ahornyai.httpclient.utils.RequestBuilder;
import org.junit.Test;

public class Others extends RequestTest {

    /*
     * ERROR HANDLING
     */

    @Test
    public void asyncInvalidUrl() throws InterruptedException {
        runTest("async invalid url");

        AsyncJUnit asyncJUnit = new AsyncJUnit();
        HttpClient client = new HttpClient();
        HttpRequest request = new RequestBuilder()
                .method(HttpMethod.GET)
                .url("https://cfasd123sxda.com")
                .build();

        request.run(client, (response, status) -> {
            System.out.println("response: " + response + " \nstatus: " + status);
            asyncJUnit.finish();
        }, (e) -> {
            System.out.println("SUCCESS ERROR HANDLING");
            asyncJUnit.finish();
        }, true);
        System.out.println("Waiting for complete async request...");
        asyncJUnit.waitForFinish();
    }

    @Test
    public void invalidUrl() {
        runTest("invalid url");

        HttpClient client = new HttpClient();
        HttpRequest request = new RequestBuilder()
                .method(HttpMethod.GET)
                .url("https://cfasd123sxda.com")
                .build();

        request.run(client, (response, status) ->
                        System.out.println("response: " + response + " \nstatus: " + status),
                (e) ->
                        System.out.println("SUCCESS ERROR HANDLING"), false);
    }

    /*
     * HTTPCLIENT
     */

    @Test
    public void customUserAgent() {
        runTest("custom user agent");

        HttpClient client = new HttpClient("testagent");
        HttpRequest request = new RequestBuilder()
                .method(HttpMethod.GET)
                .url("https://postman-echo.com/get")
                .build();

        request.run(client, (response, status) -> System.out.println("response: " + response + " \nstatus: " + status), (e) -> {}, false);
    }

}
