package me.ahornyai.httpclient.utils;

import me.ahornyai.httpclient.HttpMethod;
import me.ahornyai.httpclient.HttpRequest;
import me.ahornyai.httpclient.requests.HttpDelete;
import me.ahornyai.httpclient.requests.HttpGet;
import me.ahornyai.httpclient.requests.HttpPost;
import me.ahornyai.httpclient.requests.HttpPut;

import java.util.HashMap;

public class RequestBuilder {
    private HttpMethod method;
    private String url;
    private HashMap<String, String> headers = new HashMap<>();
    private HashMap<String, String> urlParams = new HashMap<>();
    private HashMap<String, String> bodyParams = new HashMap<>();
    private String body;

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

    public RequestBuilder addBodyParam(String param, String value) {
        bodyParams.put(param, value);
        return this;
    }

    public RequestBuilder addUrlParam(String param, String value) {
        urlParams.put(param, value);
        return this;
    }

    public RequestBuilder headers(HashMap<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public RequestBuilder body(String body) {
        this.body = body;
        return this;
    }

    public RequestBuilder bodyParams(HashMap<String, String> params) {
        this.bodyParams = params;
        return this;
    }

    public RequestBuilder urlParams(HashMap<String, String> params) {
        this.urlParams = params;
        return this;
    }

    public HttpRequest build() {
        switch (method) {
            case PUT: {
                HttpPut res = new HttpPut(url, urlParams, bodyParams, headers);
                res.setBody(body);
                return res;
            }
            case DELETE: {
                HttpDelete res = new HttpDelete(url, urlParams, bodyParams, headers);
                res.setBody(body);
                return res;
            }
            case POST: {
                HttpPost res = new HttpPost(url, urlParams, bodyParams, headers);
                res.setBody(body);
                return res;
            }
            default: {
                HttpGet res = new HttpGet(url, urlParams, bodyParams, headers);
                res.setBody(body);
                return res;
            }
        }
    }

}
