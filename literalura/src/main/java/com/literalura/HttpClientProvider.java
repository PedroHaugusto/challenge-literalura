package com.literalura;

import java.net.http.HttpClient;

public class HttpClientProvider {

    public HttpClient createClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }
}
