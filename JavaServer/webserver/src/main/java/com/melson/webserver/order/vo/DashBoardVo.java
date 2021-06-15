package com.melson.webserver.order.vo;

import java.util.List;

/**
 * Created by Messi on 2021/6/11
 */
public class DashBoardVo {
    List<DashBoardItemVo> topProductList;
    List<DashBoardItemVo> topSupplyList;

    public List<DashBoardItemVo> getTopProductList() {
        return topProductList;
    }

    public void setTopProductList(List<DashBoardItemVo> topProductList) {
        this.topProductList = topProductList;
    }

    public List<DashBoardItemVo> getTopSupplyList() {
        return topSupplyList;
    }

    public void setTopSupplyList(List<DashBoardItemVo> topSupplyList) {
        this.topSupplyList = topSupplyList;
    }
}
