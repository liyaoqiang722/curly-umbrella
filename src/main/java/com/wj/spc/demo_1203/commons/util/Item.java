package com.wj.spc.demo_1203.commons.util;

import org.jsoup.nodes.Node;

import java.util.List;

/**
 * Created by liyaoqiang on 17/8/14.
 */
public class Item {
    private List<String> tags;

    private List<String> attributes;

    //下面的子元素
    private List<Node> nodes;

    private String text;

    private POIStyle POIStyle;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public POIStyle getPOIStyle() {
        return POIStyle;
    }

    public void setPOIStyle(POIStyle POIStyle) {
        this.POIStyle = POIStyle;
    }
}
