package com.kpfu.ildar.util;

import com.kpfu.ildar.domain.WebPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkSpan;
import org.nibor.autolink.LinkType;

import java.io.IOException;
import java.net.URI;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Ildar on 4/8/2017.
 */
@Slf4j
public final class LinkRetriever {

    private LinkRetriever() {}

    public static List<WebPage> retrieveLinks(WebPage webPage, Function<String, String> webPageConverter) {
        log.info("Retrieving links for the web page {}.", webPage.getFullUrl());

        String input = webPageConverter.apply(downloadPage(webPage));
        LinkExtractor linkExtractor = LinkExtractor.builder()
                .linkTypes(EnumSet.of(LinkType.URL, LinkType.WWW))
                .build();

        return StreamSupport.stream(linkExtractor.extractLinks(input).spliterator(), false)
                .map(link -> WebPage.of(urlFromPage(link, input)))
                .filter(page -> hostsEqual(page, webPage))
                .collect(Collectors.toList());
    }

    private static boolean hostsEqual(WebPage page, WebPage webPage) {
        try {
            String host1 = URI.create(page.getFullUrl()).getHost();
            String host2 = URI.create(webPage.getFullUrl()).getHost();
            return !(host1 == null || host2 == null) && host1.equals(host2);
        }
        catch (IllegalArgumentException exc) {
            log.warn(exc.getMessage());
            return false;
        }
    }

    private static String urlFromPage(LinkSpan linkSpan, String input) {
        return input.substring(linkSpan.getBeginIndex(), linkSpan.getEndIndex());
    }

    private static String downloadPage(WebPage webPage) {
        try {
            return IOUtils.toString(URI.create(webPage.getFullUrl()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
