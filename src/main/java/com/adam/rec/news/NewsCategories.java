package com.adam.rec.news;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adam
 * Created at 2018/4/2 10:00.
 * 新闻附加实用方法。
 */

@Repository
public class NewsCategories {

    /**
     * 存储新闻分类的Map。
     */
    private static Map<String,String> categories = new HashMap<>();
    static {
        categories.put("gongyi.sohu.com","公益");
        categories.put("roll.sohu.com","滚动");
        categories.put("mil.sohu.com","军事");
        categories.put("sports.sohu.com","体育");
        categories.put("cul.sohu.com","文化");
        categories.put("learning.sohu.com","教育");
        categories.put("travel.sohu.com","旅游");
        categories.put("auto.sohu.com","汽车");
        categories.put("it.sohu.com","科技");
        categories.put("business.sohu.com","财经");
        categories.put("yule.sohu.com","娱乐");
        categories.put("s.sohu.com","体育视频");
    }

    public Map<String,String> getCategories() {
        return categories;
    }

}