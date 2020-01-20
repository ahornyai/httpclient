package me.ahornyai.httpclient;

import lombok.Getter;
import me.ahornyai.httpclient.utils.RandomUserAgent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class HttpClient {
    public static final ExecutorService executor = Executors.newFixedThreadPool(10);

    private String userAgent = RandomUserAgent.getRandomUserAgent();

    public HttpClient() {}

    public HttpClient(String userAgent) {
        this.userAgent = userAgent;
    }
}
