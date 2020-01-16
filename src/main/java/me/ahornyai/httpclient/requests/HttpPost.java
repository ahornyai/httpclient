package me.ahornyai.httpclient.requests;

import me.ahornyai.httpclient.HttpClient;
import me.ahornyai.httpclient.HttpRequest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.function.BiConsumer;

public class HttpPost extends HttpRequest {
    /*
     * CONSTRUCTORS
     */

    public HttpPost(String url, HashMap<String, String> params, HashMap<String, String> headers) {
        super(url, params, headers);
    }

    public HttpPost(String url, HashMap<String, String> params) {
        super(url, params);
    }

    public HttpPost(String url) {
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
            byte[] paramsBytes = paramBuilder(params).getBytes(StandardCharsets.UTF_8);

            URL urlObj = new URL(url);
            HttpURLConnection uc = (HttpURLConnection) urlObj.openConnection();
            uc.setDoOutput(true);
            uc.setRequestMethod("POST");
            uc.addRequestProperty("User-Agent", client.getUserAgent());
            uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            uc.setRequestProperty("Content-Length", paramsBytes.length + "");
            applyHeaders(uc);

            try(DataOutputStream wr = new DataOutputStream(uc.getOutputStream())) {
                wr.write(paramsBytes);
            }

            if (uc.getResponseCode() > 399 && uc.getResponseCode() < 600)
                consumer.accept(readOutput(uc.getErrorStream()), uc.getResponseCode());
            else
                consumer.accept(readOutput(uc.getInputStream()), uc.getResponseCode());
        }
    }
}
