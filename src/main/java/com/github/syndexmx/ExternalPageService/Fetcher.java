package com.github.syndexmx.ExternalPageService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

import static com.github.syndexmx.ExternalPageService.Parser.listFromUrl;

public class Fetcher {

    protected static Document getPage(String url) throws IOException {
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    static int SCAN_PAGE_COUNT_LIMIT = 100;

    public static String scanUrl(String url) throws IOException {
        StringBuilder outerList = new StringBuilder();
        StringBuilder innerList = new StringBuilder(String.valueOf(listFromUrl(url)));
        outerList.append(innerList);
        int pageCount = 2;
        do {
            String pageUrl = url + "?p=" + pageCount;
            innerList = new StringBuilder(String.valueOf(listFromUrl(pageUrl)));
            outerList.append(innerList);
            pageCount++;
        } while (innerList!=null && innerList.length()>1 && pageCount<SCAN_PAGE_COUNT_LIMIT);
        return outerList.toString();
    }

}
