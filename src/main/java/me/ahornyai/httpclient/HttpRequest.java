package me.ahornyai.httpclient;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Getter
@Setter
public abstract class HttpRequest {
    protected String url;
    protected String body;
    protected HashMap<String, String> headers;
    protected HashMap<String, String> urlParams;
    protected HashMap<String, String> bodyParams;

    public HttpRequest(String url, HashMap<String, String> urlParams, HashMap<String, String> bodyParams, HashMap<String, String> headers) {
        this.url = url;
        this.urlParams = urlParams;
        this.bodyParams = bodyParams;
        this.headers = headers;
    }

    public HttpRequest(String url, HashMap<String, String> urlParams, HashMap<String, String> bodyParams) {
        this.url = url;
        this.urlParams = urlParams;
        this.bodyParams = bodyParams;
    }

    public HttpRequest(String url, HashMap<String, String> params, boolean isUrlParams) {
        this.url = url;
        if (isUrlParams)
            this.urlParams = params;
        else
            this.bodyParams = params;
    }

    public HttpRequest(String url) {
        this.url = url;
    }

    public abstract void run(HttpClient client, BiConsumer<String, Integer> consumer, Consumer<Exception> exCallback, boolean async);

    protected String paramBuilder(HashMap<String, String> params) {
        if (params == null) return "";
        boolean isFirst = true;
        StringBuilder paramBuilder = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (isFirst) paramBuilder.append(encode(entry.getKey())).append("=").append(encode(entry.getValue()));
            else paramBuilder.append("&").append(encode(entry.getKey())).append("=").append(encode(entry.getValue()));

            isFirst = false;
        }

        return paramBuilder.toString();
    }

    protected String urlParamBuilder(HashMap<String, String> params) {
        if (params == null) return "";
        boolean isFirst = true;
        StringBuilder paramBuilder = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (isFirst) paramBuilder.append("?").append(encode(entry.getKey())).append("=").append(encode(entry.getValue()));
            else paramBuilder.append("&").append(encode(entry.getKey())).append("=").append(encode(entry.getValue()));

            isFirst = false;
        }

        return paramBuilder.toString();
    }

    protected String readOutput(InputStream stream) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            sb.append(inputLine);

        return sb.toString();
    }

    protected void applyHeaders(URLConnection connection) {
        if (headers == null) return;
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            connection.addRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    private String encode(String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8);
    }
}
