package me.ahornyai.httpclient.requests;

import me.ahornyai.httpclient.HttpClient;
import me.ahornyai.httpclient.HttpRequest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.function.BiConsumer;

public class HttpGet extends HttpRequest {
    /*
     * CONSTRUCTORS
     */

    public HttpGet(String url, HashMap<String, String> params, HashMap<String, String> headers) {
        super(url, params, headers);
    }

    public HttpGet(String url, HashMap<String, String> params) {
        super(url, params);
    }

    public HttpGet(String url) {
        super(url);
    }

    /*
     * METHODS
     */

    @Override
    public void run(HttpClient client, BiConsumer<String, Integer> consumer, boolean async) throws IOException {
        if (async) {
            HttpClient.executor.execute(() -> {
                try {
                    run(client, consumer, false);
                } catch (IOException e) {
                    consumer.accept(null, -1);
                    throw new RuntimeException(e);
                }
            });
        }else {
            URL urlObj = new URL(url + getParamBuilder(params));
            HttpURLConnection uc = (HttpURLConnection) urlObj.openConnection();
            uc.addRequestProperty("User-Agent", client.getUserAgent());
            applyHeaders(uc);

            if (uc.getResponseCode() > 399 && uc.getResponseCode() < 600)
                consumer.accept(readOutput(uc.getErrorStream()), uc.getResponseCode());
            else
                consumer.accept(readOutput(uc.getInputStream()), uc.getResponseCode());
        }
    }
}
