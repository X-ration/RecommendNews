package com.adam.rec.recommend;

import com.adam.rec.news.News;
import org.apache.catalina.LifecycleState;

import java.util.List;

/**
 * @author adam
 * 创建于 2018-04-21 13:55.
 */
public abstract class RecommendService {

    public abstract List<News> getNewsListFiltered(List<String> categories);
    public abstract List<News> getNewsListFilteredIndexRange(List<String> categories,int startIndex,int endIndex);
    public abstract List<News> getNewsListFilteredPage(List<String> categories,int page);

}