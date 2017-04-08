package com.kpfu.ildar;

import com.kpfu.ildar.algorithm.AdjacentMatrixBuilder;
import com.kpfu.ildar.algorithm.MatrixToStringConverter;
import com.kpfu.ildar.domain.WebPage;
import lombok.val;

import java.util.function.Function;

public class App {

    public static void main( String[] args ) {
        String url = "https://en.wikipedia.org";
        String fullPageUrl = url + "/wiki/Oracle_Corporation";
        int matrixSize = 100;
        Function<String, String> wikiPageConverter = wikiPage -> wikiPage.replaceAll("/wiki/", url + "/wiki/");
        val matrixBuilder = new AdjacentMatrixBuilder(WebPage.of(fullPageUrl), matrixSize, wikiPageConverter);
        val resultingList = matrixBuilder.buildAdjacentMatrix();

        System.out.println("The initial web site is " + fullPageUrl);
        System.out.println("Indexes of web-pages in the matrix:");
        int i = 0;
        for (val webPage : resultingList) {
            System.out.println(i++ + " -> " + webPage.getFullUrl());
        }

        System.out.println("The resulting matrix:");
        System.out.println(new MatrixToStringConverter(resultingList));
    }
}
