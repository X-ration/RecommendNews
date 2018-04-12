package com.adam.rec.news.page;

import org.apache.catalina.LifecycleState;

import java.util.List;

/**
 * @author adam
 * 创建于 2018-04-12 20:18.
 */
public class PagePagination {

    private Boolean hasPrevious;
    private Boolean hasNext;
    private int previousPage;
    private int currentPage;
    private int nextPage;
    private List<Integer> previousPages;
    private List<Integer> nextPages;

    public PagePagination(Boolean hasPrevious, Boolean hasNext, int previousPage, int currentPage, int nextPage, List<Integer> previousPages, List<Integer> nextPages) {
        this.hasPrevious = hasPrevious;
        this.hasNext = hasNext;
        this.previousPage = previousPage;
        this.currentPage = currentPage;
        this.nextPage = nextPage;
        this.previousPages = previousPages;
        this.nextPages = nextPages;
    }

    public Boolean getHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(Boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public List<Integer> getPreviousPages() {
        return previousPages;
    }

    public void setPreviousPages(List<Integer> previousPages) {
        this.previousPages = previousPages;
    }

    public List<Integer> getNextPages() {
        return nextPages;
    }

    public void setNextPages(List<Integer> nextPages) {
        this.nextPages = nextPages;
    }
}
