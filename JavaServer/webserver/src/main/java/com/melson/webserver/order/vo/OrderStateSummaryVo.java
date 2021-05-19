package com.melson.webserver.order.vo;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/17
 */
public class OrderStateSummaryVo {
    private String title;
    private Integer count;
    private String path;

    public OrderStateSummaryVo(String title, Integer count, String path) {
        this.title = title;
        this.count = count;
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
