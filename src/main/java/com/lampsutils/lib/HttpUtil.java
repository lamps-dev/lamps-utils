package com.lampsutils.lib;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Simple HTTP utility for making requests.
 * Other mods can use this as part of the lamps-utils library.
 */
public class HttpUtil {

    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    /**
     * Perform a GET request and return the response body as a string.
     *
     * @param url the URL to fetch
     * @return the response body
     * @throws Exception if the request fails
     */
    public static String get(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    /**
     * Perform a GET request and return the full HttpResponse.
     *
     * @param url the URL to fetch
     * @return the full response including status code and headers
     * @throws Exception if the request fails
     */
    public static HttpResponse<String> getResponse(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Perform a POST request with a body and return the response body as a string.
     *
     * @param url         the URL to post to
     * @param body        the request body
     * @param contentType the Content-Type header value
     * @return the response body
     * @throws Exception if the request fails
     */
    public static String post(String url, String body, String contentType) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", contentType)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
