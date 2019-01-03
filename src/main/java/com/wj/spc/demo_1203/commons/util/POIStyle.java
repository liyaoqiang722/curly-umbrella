package com.wj.spc.demo_1203.commons.util;

/**
 * Created by liyaoqiang on 17/8/18.
 */
public class POIStyle {
    private String fontSize="16px";

    private boolean bold;

    private boolean italic;

    private String textAlign="left";

    private String color="000000";

    private boolean underLine;
    //每段段首缩进
    private int indentationFirstLine;

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public String getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(String textAlign) {
        this.textAlign = textAlign;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isUnderLine() {
        return underLine;
    }

    public void setUnderLine(boolean underLine) {
        this.underLine = underLine;
    }

    public int getIndentationFirstLine() {
        return indentationFirstLine;
    }

    public void setIndentationFirstLine(int indentationFirstLine) {
        this.indentationFirstLine = indentationFirstLine;
    }
}
