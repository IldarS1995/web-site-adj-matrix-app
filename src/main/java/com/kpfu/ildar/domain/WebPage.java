package com.kpfu.ildar.domain;

import lombok.Value;

/**
 * Created by Ildar on 4/8/2017.
 */
@Value
public class WebPage {

    String fullUrl;

    public static WebPage of(String page) {
        return new WebPage(page);
    }
}
