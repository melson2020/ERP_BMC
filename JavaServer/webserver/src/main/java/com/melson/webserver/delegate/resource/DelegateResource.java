package com.melson.webserver.delegate.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.ResultType;
import com.melson.webserver.delegate.vo.DelegateReleaseVo;
import com.melson.webserver.order.entity.DelegateTicket;
import com.melson.webserver.order.service.IDelegateDetailService;
import com.melson.webserver.order.service.IDelegateTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author Nelson
 * @Description
 * @Date 2021/5/27
 */
@RestController
@RequestMapping(value = "/delegate")
public class DelegateResource extends BaseResource {
    @Autowired
    private IDelegateTicketService delegateTicketService;

    @GetMapping(value = "/releaseList")
    public Result FindDelegateReleaseList() {
        return success(delegateTicketService.FindByState(DelegateTicket.STATE_CREATE));
    }

    @GetMapping(value = "/inBoundList")
    public Result FindInBoundList() {
        return success(delegateTicketService.FindByState(DelegateTicket.STATE_PICKING));
    }

    @GetMapping(value = "/processingList")
    public Result FindDelegateProcessingList() {
        return success(delegateTicketService.FindProcessingList());
    }

    @GetMapping(value = "/releaseInfo")
    public Result FindDelegateReleaseInfo(Integer ticketId) {
        return success(delegateTicketService.FindReleaseInfo(ticketId));
    }

    @PostMapping(value = "/confirm")
    public Result DelegateTicketConfirm(@RequestBody DelegateReleaseVo releaseVo) {
        if (releaseVo.getDelegateTicket() == null || releaseVo.getDelegateDetailList() == null || releaseVo.getDelegateDetailList().size() <= 0)
            return GenerateResult(ResultType.ParameterNeeded);
        DelegateTicket saved = delegateTicketService.DelegateTicketConfirm(releaseVo);
        if (saved != null) {
            return success(saved);
        } else {
            return failure(-1, "保存失败");
        }
    }

    @GetMapping(value = "/recordList")
    public Result FindDelegateRecordList(HttpServletRequest request) {
        Map<String,String[]> kvMap=request.getParameterMap();
        List<DelegateTicket> recordList=delegateTicketService.FindRecordList(kvMap);
        return success(recordList);
    }
}
