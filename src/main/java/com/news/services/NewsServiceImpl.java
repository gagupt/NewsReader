package com.news.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.models.NewsObj;
import com.news.utils.NewsUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {
    static Map<Integer, NewsObj> newsObjMap = new HashMap<>();
    private static final Logger logger = LogManager.getLogger(NewsServiceImpl.class);
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void update() {
        updateNews();
    }

    @Override
    public List<Integer> getTopStories() {
        return newsObjMap.keySet().stream().collect(Collectors.toList());
    }

    @Override
    public String getNewsById(Integer newsId) {
        try {
            return objectMapper.writeValueAsString(newsObjMap.get(newsId));
        } catch (JsonProcessingException ex) {
            logger.error("Exception Caught", ex);
        }
        return null;
    }

    private void updateNews() {
        logger.info("updating news from google api");
        String newsString = NewsUtils.getNews();
        JSONObject jsonObject = new JSONObject(newsString);

        JSONArray articles = jsonObject.getJSONArray("articles");
        for (int i = 0; i < articles.length(); i++) {
            NewsObj newsObj = new NewsObj();

            JSONObject articlesJSONObject = articles.getJSONObject(i);
            newsObj.setAuthor(articlesJSONObject.get("author").toString());
            newsObj.setTitle(articlesJSONObject.get("title").toString());
            newsObj.setDescription(articlesJSONObject.get("description").toString());
            newsObj.setUrl(articlesJSONObject.get("url").toString());
            newsObj.setUrlToImage(articlesJSONObject.get("urlToImage").toString());
            newsObj.setPublishedAt(articlesJSONObject.get("publishedAt").toString());
            newsObj.setContent(articlesJSONObject.get("content").toString());

            newsObjMap.put(i, newsObj);
        }
    }
}
