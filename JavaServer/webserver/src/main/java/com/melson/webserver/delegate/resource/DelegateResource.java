package com.melson.webserver.delegate.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.webserver.order.service.IDelegateTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return success(delegateTicketService.FindReleaseList());
    }
}
