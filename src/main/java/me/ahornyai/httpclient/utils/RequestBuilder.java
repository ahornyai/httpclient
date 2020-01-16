package me.ahornyai.httpclient.utils;

import me.ahornyai.httpclient.HttpMethod;
import me.ahornyai.httpclient.HttpRequest;
import me.ahornyai.httpclient.requests.HttpGet;
import me.ahornyai.httpclient.requests.HttpPost;

import java.util.HashMap;

public class RequestBuilder {
    private HttpMethod method;
    private String url;
    private HashMap<String, String> headers = new HashMap<>();
    private HashMap<String, String> params = new HashMap<>();

    public RequestBuilder method(HttpMethod method) {
        this.method = method;
        return this;
    }

    public RequestBuilder url(String url) {
        this.url = url;
        return this;
    }

    public RequestBuilder addHeader(String header, String value) {
        headers.put(header, value);
        return this;
    }

    public RequestBuilder addParam(String param, String value) {
        params.put(param, value);
        return this;
    }

    public RequestBuilder headers(HashMap<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public RequestBuilder params(HashMap<String, String> params) {
        this.params = params;
        return this;
    }

    public HttpRequest build() {
        switch (method) {
            case POST: return new HttpPost(url, params, headers);
            default: return new HttpGet(url, params, headers);
        }
    }

}
