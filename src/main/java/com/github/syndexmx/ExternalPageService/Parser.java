package com.github.syndexmx.ExternalPageService;

import org.jsoup.nodes.Document;
import java.io.IOException;

import static com.github.syndexmx.ExternalPageService.Fetcher.getPage;

public class Parser {


    public static String listFromUrl(String url) throws IOException {
        Document page = getPage(url);

        StringBuilder list = new StringBuilder();

        String pageTitle = page.title();
        list.append("<h3>" + pageTitle + "</h3> \n");

        String pageText = page.toString();
        if (pageText.contains("SearchNoResult")){
            return "";
        }
        String[] strings = pageText.split("manifestations:");
        for (String string : strings){
            if (string.contains("ManifestationAudio") &&
                    string.contains(",isExpired")){

                String mp3 = extractMp3Url(string);
                String title = extractProgramTitle(string);
                String image = extractImage(string);

                if (mp3.contains(".mp3")) {
                    list.append("<h4> " + title + " </h4> \n" +
                            "<img src=" + image+ "/>\n" +
                            "<p><a href=" + mp3 + "> " + mp3 + " </a></p> \n ");
                }

            }
        }
        System.out.println("Page parsed: " + pageTitle);
        return list.toString();
    }


    private static String extractProgramTitle(String string) {
        String title = string.substring(string.indexOf("created:"));
        title = title.substring(title.indexOf("title:")+6);
        title = title.substring(0, title.indexOf(",isExpired"));
        if (title.contains(",path:")){
            title = title.substring(0, title.indexOf(",path"));
        }
        if (title.contains(",seoTitle:")){
            title = title.substring(0, title.indexOf(",seoTitle:"));
        }
        if (title.contains(",airtime:")){
            title = title.substring(0, title.indexOf(",airtime:"));
        }
        title = title.replaceAll("\"","*");
        return title;
    }

    private static String extractImage(String string) {
        String image = string.substring(string.indexOf("created:"));
        image = image.substring(image.indexOf(",src:")+5);
        image = image.substring(0, image.indexOf(",webpSrc:"));
        return image;
    }

    private static String extractMp3Url(String string) {
        String mp3 = string.substring(string.indexOf("url:")+4);
        mp3 = mp3.substring(0, mp3.indexOf(",created:"));
        return mp3;
    }


}
