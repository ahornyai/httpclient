package me.ahornyai.httpclient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ahornyai.httpclient.utils.RandomUserAgent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HttpClient {
    public static final ExecutorService executor = Executors.newFixedThreadPool(20);

    private String userAgent = RandomUserAgent.getRandomUserAgent();
}
