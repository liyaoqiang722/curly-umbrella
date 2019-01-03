package com.wj.spc.demo_1203.commons.util;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by liyaoqiang on 17/8/18.
 */
public class POIParagraph {

    private List<POIItem> items;
    private POIStyle style;
    int type=1;//1 默认文字 2空页 3图片
    ByteArrayInputStream imageInputStream;

    public List<POIItem> getItems() {
        return items;
    }

    public void setItems(List<POIItem> items) {
        this.items = items;
    }

    public POIStyle getStyle() {
        return style;
    }

    public void setStyle(POIStyle style) {
        this.style = style;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ByteArrayInputStream getImageInputStream() {
        return imageInputStream;
    }

    public void setImageInputStream(ByteArrayInputStream imageInputStream) {
        this.imageInputStream = imageInputStream;
    }
}
