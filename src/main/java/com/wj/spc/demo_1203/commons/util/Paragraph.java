package com.wj.spc.demo_1203.commons.util;


import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created by liyaoqiang on 17/8/14.
 */
public class Paragraph {

    private String tag;

    private List<String> attributes;
    //所有叶子节点的element
    private Elements elements;
    //所有叶子节点的element
    private List<Item> items;
    private POIStyle POIStyle;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public Elements getElements() {
        return elements;
    }

    public void setElements(Elements elements) {
        this.elements = elements;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public POIStyle getPOIStyle() {
        return POIStyle;
    }

    public void setPOIStyle(POIStyle POIStyle) {
        this.POIStyle = POIStyle;
    }
}
