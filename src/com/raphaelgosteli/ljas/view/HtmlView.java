package com.raphaelgosteli.ljas.view;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class HtmlView extends View {

    private Document document;


    public HtmlView(String path) {
        try {
            this.document = Jsoup.parse(new File(path), "UTF-8", "");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets document.
     *
     * @return Value of document.
     */
    public Document getDocument() {
        return document;
    }

    /**
     * Sets new document.
     *
     * @param document New value of document.
     */
    public void setDocument(Document document) {
        this.document = document;

    }

    @Override
    public String getContent() {
        return this.document.toString();
    }


}
