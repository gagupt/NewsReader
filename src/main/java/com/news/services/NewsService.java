package com.news.services;

import java.util.List;

public interface NewsService {
    void update();

    List<Integer> getTopStories();

    String getNewsById(Integer newsId);
}
