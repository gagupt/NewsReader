package com.news.controllers;

import com.news.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsController {

    @Autowired
    private NewsService newsService;

    @RequestMapping(
            value = {"ping"},
            method = RequestMethod.GET)
    public String statusCheck() {
        return "pong";
    }


    @RequestMapping(value = {"topstories"}, method = RequestMethod.GET)
    public List<Integer> topStories() {
        return newsService.getTopStories();
    }

    @RequestMapping(value = {"item/{id}"}, method = RequestMethod.GET)
    public String item(@PathVariable Integer id) {
        return newsService.getNewsById(id);
    }

    @RequestMapping(value = {"update"}, method = RequestMethod.GET)
    public void update() {
        newsService.update();
    }

    @Scheduled(fixedRate = 300000)
    public void updateCronNews() {
        newsService.update();
    }
}