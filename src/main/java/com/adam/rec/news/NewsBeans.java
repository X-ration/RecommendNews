package com.adam.rec.news;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Adam
 * Created at 2018/4/5 12:47.
 */

@Component
public class NewsBeans {

    private NewsCategories newsCategories;

    @Bean
    public NewsCategories getNewsCategories() {
        if(newsCategories != null) {
            newsCategories = new NewsCategories();
        }
        return newsCategories;
    }


    @Bean
    public int getWindowInterval() {
        return 10;
    }

}
