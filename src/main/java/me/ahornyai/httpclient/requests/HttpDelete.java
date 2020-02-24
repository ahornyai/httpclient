package me.ahornyai.httpclient.requests;

import me.ahornyai.httpclient.HttpClient;
import me.ahornyai.httpclient.HttpRequest;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class HttpDelete extends HttpRequest {
    /*
     * CONSTRUCTORS
     */

    public HttpDelete(String url, HashMap<String, String> urlParams, HashMap<String, String> bodyParams, HashMap<String, String> headers) {
        super(url, urlParams, bodyParams, headers);
    }

    public HttpDelete(String url, HashMap<String, String> params, boolean isUrlParams) {
        super(url, params, isUrlParams);
    }

    public HttpDelete(String url) {
        super(url);
    }

    /*
     * METHODS
     */

    @Override
    public void run(HttpClient client, BiConsumer<String, Integer> consumer, Consumer<Exception> exCallback, boolean async) {
        if (async) {
            HttpClient.executor.execute(() -> run(client, consumer, exCallback,false));
        }else {
            try {
                URL urlObj = new URL(url + urlParamBuilder(urlParams));
                HttpURLConnection uc = (HttpURLConnection) urlObj.openConnection();
                uc.setDoOutput(true);
                uc.setRequestMethod("DELETE");
                uc.addRequestProperty("User-Agent", client.getUserAgent());

                if (bodyParams != null) {
                    if (!bodyParams.isEmpty()) {
                        byte[] paramsBytes = paramBuilder(bodyParams).getBytes(StandardCharsets.UTF_8);

                        uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        uc.setRequestProperty("Content-Length", paramsBytes.length + "");
                        applyHeaders(uc);

                        try (DataOutputStream wr = new DataOutputStream(uc.getOutputStream())) {
                            wr.write(paramsBytes);
                        }
                    }else {
                        byte[] paramsBytes = body.getBytes(StandardCharsets.UTF_8);

                        uc.setRequestProperty("Content-Length", paramsBytes.length + "");
                        applyHeaders(uc);

                        try (DataOutputStream wr = new DataOutputStream(uc.getOutputStream())) {
                            wr.write(paramsBytes);
                        }
                    }
                }

                if (uc.getResponseCode() > 399 && uc.getResponseCode() < 600)
                    consumer.accept(readOutput(uc.getErrorStream()), uc.getResponseCode());
                else
                    consumer.accept(readOutput(uc.getInputStream()), uc.getResponseCode());
            }catch (Exception ex) {exCallback.accept(ex);}
        }
    }
}
