package com.adam.rec.news;

import com.adam.rec.user_news.Evaluation;

import java.util.List;
import java.util.Map;

public abstract class NewsService {

    private static Map<String,String> newsCategories;
    int windowInterval;

    public NewsService(NewsCategories newsCategories, int windowInterval) {
        this.newsCategories = newsCategories.getCategoriesMap();
        this.windowInterval = windowInterval;
    }

    int startIndex = 1;

    abstract List<News> getNewsListByIdRange(int startIndex, int endIndex);
    abstract List<News> getNewsListWindow();
    abstract List<News> getNewsListPage(int page);
    abstract Boolean writeNewsList(List<News> newsList) throws Exception;
    abstract List<News> getNewsListByCategoriesAndAmount(List<String> categories,int amountEachCategory);
    abstract News getNewsById(int newsId);
    abstract List<News> getNewsListByIndexRange(int startIndex,int endIndex);
    public abstract Boolean receiveEvaluation(Evaluation evaluation,double prevPersonalScore,int prevPersonalEvaluation,Boolean hasPrevPersonal);

    /**
     * 根据URL地址，识别新闻分类。
     * @param url 新闻原始URL
     * @return 新闻类别
     */
    static String identifyCategory(String url) {
        String s = url.split("/")[2];
        for(String key : newsCategories.keySet()){
            if(url.contains(key)) return newsCategories.get(key);
        }
        return "其他";
    }

}
