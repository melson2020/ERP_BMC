package com.melson.webserver.delegate.vo;

import com.melson.webserver.order.entity.DelegateDetail;
import com.melson.webserver.order.entity.DelegateTicket;
import com.melson.webserver.order.entity.PickingTicket;
import com.melson.webserver.order.entity.PickingTicketDetail;

import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/28
 */
public class DelegateReleaseVo {
    private DelegateTicket delegateTicket;
    private List<DelegateDetail> delegateDetailList;
    private PickingTicket pickingTicket;
    private List<PickingTicketDetail> pickingTicketDetailList;

    public DelegateTicket getDelegateTicket() {
        return delegateTicket;
    }

    public void setDelegateTicket(DelegateTicket delegateTicket) {
        this.delegateTicket = delegateTicket;
    }

    public List<DelegateDetail> getDelegateDetailList() {
        return delegateDetailList;
    }

    public void setDelegateDetailList(List<DelegateDetail> delegateDetailList) {
        this.delegateDetailList = delegateDetailList;
    }

    public PickingTicket getPickingTicket() {
        return pickingTicket;
    }

    public void setPickingTicket(PickingTicket pickingTicket) {
        this.pickingTicket = pickingTicket;
    }

    public List<PickingTicketDetail> getPickingTicketDetailList() {
        return pickingTicketDetailList;
    }

    public void setPickingTicketDetailList(List<PickingTicketDetail> pickingTicketDetailList) {
        this.pickingTicketDetailList = pickingTicketDetailList;
    }
}
