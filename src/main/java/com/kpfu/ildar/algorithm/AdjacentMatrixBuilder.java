package com.kpfu.ildar.algorithm;

import com.kpfu.ildar.domain.WebPage;
import com.kpfu.ildar.domain.WebPageMatrix;
import com.kpfu.ildar.util.LinkRetriever;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Ildar on 4/8/2017.
 */
@Getter @Setter
@Slf4j
public class AdjacentMatrixBuilder {

    private WebPageMatrix webPageMatrix;
    private Function<String, String> webPageConverter;

    public AdjacentMatrixBuilder(WebPage initialPage,
                                 int matrixSize,
                                 Function<String, String> webPageConverter) {
        this.webPageMatrix = new WebPageMatrix(matrixSize);
        this.webPageMatrix.addPage(initialPage);
        this.webPageConverter = webPageConverter;
    }

    public WebPageMatrix buildAdjacentMatrix() {
        log.info("Building adjacent matrix...");

        int i = 0;
        do {
            WebPage nextPage = webPageMatrix.getPage(i);
            List<WebPage> retrievedPages = LinkRetriever.retrieveLinks(nextPage, webPageConverter);
            log.info("Stage #{}, page {}, retrieved {} pages from the website.",
                    i, nextPage.getFullUrl(), retrievedPages.size());
            List<Integer> pagesIndices = putToTheWebPageList(retrievedPages);
            for (Integer retrievedPageIndex : pagesIndices) {
                if (retrievedPageIndex != -1) {
                    //we might not have added a page because the list is fully loaded
                    webPageMatrix.setConnection(i, retrievedPageIndex);
                }
            }
            log.info("Finished stage #{}, current web-page list fill is {}.", i, webPageMatrix.getCurrentFill());
            i++;
        }
        while(i < webPageMatrix.getCurrentFill());

        return webPageMatrix;
    }

    private List<Integer> putToTheWebPageList(List<WebPage> retrievedPages) {
        for (WebPage retrievedPage : retrievedPages) {
            webPageMatrix.addPageIfNotExists(retrievedPage);
        }

        return retrievedPages.stream()
                .map(page -> webPageMatrix.getIndexOfPage(page))
                .collect(Collectors.toList());
    }
}
