package com.melson.webserver.contract.resource;

import com.melson.webserver.contract.entity.Contract;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 正式合同controller
 *
 * @author wuhuan
 * @date 2021年04月15日
 */
@RestController
@RequestMapping(value = "/contract/formal")
public class FormalContractResource extends AbsContractResource {

    @Override
    protected String getContractType() {
        return Contract.TYPE_FORMAL;
    }
}
