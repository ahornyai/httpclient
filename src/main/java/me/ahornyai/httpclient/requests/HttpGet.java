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

public class HttpGet extends HttpRequest {
    /*
     * CONSTRUCTORS
     */

    public HttpGet(String url, HashMap<String, String> urlParams, HashMap<String, String> bodyParams, HashMap<String, String> headers) {
        super(url, urlParams, bodyParams, headers);
    }

    public HttpGet(String url, HashMap<String, String> urlParams, HashMap<String, String> bodyParams) {
        super(url, urlParams, bodyParams);
    }

    public HttpGet(String url, HashMap<String, String> params, boolean isUrlParams) {
        super(url, params, isUrlParams);
    }

    public HttpGet(String url) {
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
                uc.addRequestProperty("User-Agent", client.getUserAgent());
                applyHeaders(uc);

                if (bodyParams != null) {
                    if (!bodyParams.isEmpty()) {
                        byte[] paramsBytes = paramBuilder(bodyParams).getBytes(StandardCharsets.UTF_8);

                        uc.setDoOutput(true);
                        uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        uc.setRequestProperty("Content-Length", paramsBytes.length + "");

                        try (DataOutputStream wr = new DataOutputStream(uc.getOutputStream())) {
                            wr.write(paramsBytes);
                        }
                    }else if(body != null) {
                        byte[] paramsBytes = body.getBytes(StandardCharsets.UTF_8);

                        uc.setDoOutput(true);
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

                uc.disconnect();
            } catch (Exception ex) {exCallback.accept(ex);}
        }
    }
}
