package com.kpfu.ildar.algorithm;

import com.kpfu.ildar.domain.WebPageMatrix;

import java.util.Arrays;

/**
 * Created by Ildar on 4/8/2017.
 */
public class MatrixToStringConverter {

    private WebPageMatrix webPageMatrix;

    public MatrixToStringConverter(WebPageMatrix webPageMatrix) {
        this.webPageMatrix = webPageMatrix;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(webPageMatrix.getMatrix())
                .forEach(arr -> {
                    Arrays.stream(arr).forEach(i -> sb.append(i).append(" "));
                    sb.append("\n");
                });
        return sb.toString();
    }
}
