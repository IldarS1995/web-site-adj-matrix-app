package com.kpfu.ildar.domain;

import java.util.*;

/**
 * Created by Ildar on 4/8/2017.
 */
public class WebPageMatrix implements Iterable<WebPage> {

    private List<WebPage> list;
    private int[][] matrix;

    private int capacity;

    public WebPageMatrix(int capacity) {
        list = new ArrayList<>(capacity);
        this.matrix = new int[capacity][capacity];
        this.capacity = capacity;
    }

    public boolean addPage(WebPage page) {
        if (capacity == list.size()) {
            return false;
        }

        list.add(page);
        return true;
    }

    public void addPageIfNotExists(WebPage retrievedPage) {
        if (!findByPageUrl(retrievedPage).isPresent()) {
            addPage(retrievedPage);
        }
    }

    public Optional<WebPage> findByPageUrl(WebPage webPage) {
        return list.stream()
                .limit(getCurrentFill())
                .filter(page -> page.getFullUrl().equals(webPage.getFullUrl()))
                .findAny();
    }

    public int[][] getMatrix() {
        int[][] cln = matrix.clone();
        for (int i = 0; i < cln.length; i++) {
            cln[i] = cln[i].clone();
        }

        return cln;
    }

    public void setConnection(int i, int j) {
        matrix[i][j] = 1;
        matrix[j][i] = 1;
    }

    public int getCurrentFill() {
        return list.size();
    }

    public WebPage getPage(int i) {
        return list.get(i);
    }

    @Override
    public Iterator<WebPage> iterator() {
        return list.iterator();
    }

    public boolean isFull() {
        return getCurrentFill() == capacity;
    }

    public int getIndexOfPage(WebPage page) {
        int i = 0;
        for (WebPage webPage : list) {
            if (webPage.equals(page)) {
                return i;
            }
            i++;
        }

        return -1;
    }
}
