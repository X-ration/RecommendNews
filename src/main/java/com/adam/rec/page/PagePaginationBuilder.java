package com.adam.rec.page;

import java.util.Arrays;
import java.util.List;

/**
 * @author adam
 * 创建于 2018-04-12 20:17.
 */
public class PagePaginationBuilder {

    private PagePagination pagePagination;
    private int currentPage;
    private int maxPage;

    public PagePaginationBuilder(int currentPage,int maxPage) {
        this.currentPage = currentPage;
        this.maxPage = maxPage;
    }

    public PagePagination build() {
        Boolean hasPrevious = currentPage > 1;
        Boolean hasNext = currentPage < maxPage;
        int previousPage = hasPrevious ? (currentPage - 1) : -1;
        int nextPage = hasNext ? (currentPage + 1) : -1;
        List<Integer> previousPages = null;
        List<Integer> nextPages = null;
        if(currentPage == 3) {
            previousPages = Arrays.asList(1,2);
            nextPages = Arrays.asList(4,5);
        } else if(currentPage == 2) {
            previousPages = Arrays.asList(1);
            nextPages = Arrays.asList(3,4,5);
        } else if(currentPage <= 1) {
            nextPages = Arrays.asList(2,3,4,5);
            currentPage = 1;
        } else if(currentPage == maxPage - 2) {
            previousPages = Arrays.asList(maxPage-4,maxPage-3);
            nextPages = Arrays.asList(maxPage-1,maxPage);
        } else if(currentPage == maxPage - 1) {
            previousPages = Arrays.asList(maxPage-4,maxPage -3,maxPage-2);
            nextPages = Arrays.asList(maxPage);
        } else if(currentPage >= maxPage) {
            previousPages = Arrays.asList(maxPage-4,maxPage-3,maxPage - 2,maxPage -1);
            currentPage = maxPage;
        } else {
            previousPages = Arrays.asList(currentPage - 2, currentPage - 1);
            nextPages = Arrays.asList(currentPage + 1, currentPage + 2);
        }

        return new PagePagination(hasPrevious,hasNext,previousPage,currentPage,nextPage,previousPages,nextPages);
    }

}
