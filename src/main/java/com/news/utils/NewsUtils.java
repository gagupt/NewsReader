package com.news.utils;

import org.springframework.web.client.RestTemplate;

public class NewsUtils {
    public static String getNews() {
        final String uri = "https://newsapi.org/v2/everything?language=en&apiKey=e319dd7131664357976cb33c1f22649f&pageSize=100&q=corona&sortBy=publishedAt";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }
}
