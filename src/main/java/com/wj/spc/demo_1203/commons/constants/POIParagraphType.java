package com.wj.spc.demo_1203.commons.constants;

/**
 * Created by liyaoqiang on 17/8/30.
 */
public enum POIParagraphType {
    //最高法公报案例
    WORD(1),
    //最高检公报案例
    BLANK_PAGE(2),
    //最高法指导性案例
    IMAGE(3),
    //最高检指导性案例
    WORD_UNDER_IMAGE(4);

    private int type;

    POIParagraphType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
