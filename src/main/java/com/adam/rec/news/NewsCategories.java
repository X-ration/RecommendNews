package com.adam.rec.news;

import org.springframework.stereotype.Repository;

import java.util.*;

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
    private static Map<String,String> categoriesMap = new HashMap<>();
    static {
        categoriesMap.put("gongyi.sohu.com","公益");
        categoriesMap.put("roll.sohu.com","滚动");
        categoriesMap.put("mil.sohu.com","军事");
        categoriesMap.put("sports.sohu.com","体育");
        categoriesMap.put("cul.sohu.com","文化");
        categoriesMap.put("learning.sohu.com","教育");
        categoriesMap.put("travel.sohu.com","旅游");
        categoriesMap.put("auto.sohu.com","汽车");
        categoriesMap.put("it.sohu.com","科技");
        categoriesMap.put("business.sohu.com","财经");
        categoriesMap.put("yule.sohu.com","娱乐");
        categoriesMap.put("s.sohu.com","体育视频");
    }
    /**
     * 存储新闻分类的List。
     */
    private static List<String> categoriesList = new ArrayList<>();
    static {
        categoriesList.addAll(Arrays.asList("家居","军事","娱乐","教育","体育","体育视频","财经","科技","公益","滚动","汽车","游戏","新闻","其他"));
    }

    public Map<String,String> getCategoriesMap() {
        return categoriesMap;
    }

    public List<String> getCategoriesList() { return categoriesList; }

    public static String identifyCategory(String url) {
        String splited = url.split("/")[2];
        return categoriesMap.getOrDefault(splited,"其他");
    }

}
