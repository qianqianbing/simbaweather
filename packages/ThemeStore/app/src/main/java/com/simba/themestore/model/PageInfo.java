package com.simba.themestore.model;

import java.io.Serializable;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/28
 * @Desc :
 */
public class PageInfo implements Serializable {
    public static final int PAGE_SIZE = 10;
    public int pageNum = 0;

    public void nextPage() {
        pageNum++;
    }

    public void reset() {
        pageNum = 0;
    }

    public boolean isFirstPage() {
        return pageNum == 0;
    }

}
