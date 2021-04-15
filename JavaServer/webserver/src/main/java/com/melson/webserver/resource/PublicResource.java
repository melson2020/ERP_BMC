package com.melson.webserver.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.ResultType;
import com.melson.base.entity.Area;
import com.melson.base.entity.City;
import com.melson.base.entity.Province;
import com.melson.base.service.*;
import com.melson.base.vo.CommonSelectVo;
import com.melson.base.vo.SysDictVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2020/7/29
 */
@RestController
@RequestMapping(value = "/public")
public class PublicResource extends BaseResource {
    @Autowired
    private IProvince provinceService;
    @Autowired
    private ICity cityService;
    @Autowired
    private IArea areaService;
    @Autowired
    private ICompany companyService;
    @Autowired
    private ISysCache sysCacheService;

    @RequestMapping("/provinceList")
    public Result GetProvinceList() {
        List<Province> provinceList = provinceService.findAllFromCache();
        Result result = new Result();
        result.setData(provinceList);
        System.out.println("GET Rest Call: /public/provinceList ...");
        return result;
    }

    @RequestMapping("/cityList")
    public Result GetCityList(HttpServletRequest request) {
        String code = request.getParameter("provinceCode");
        List<City> cityList = cityService.findWithProvinceCode(code);
        Result result = new Result();
        result.setData(cityList);
        System.out.println("GET Rest Call: /public/cityList ...");
        return result;
    }

    @RequestMapping("/areaList")
    public Result GetAreaList(HttpServletRequest request) {
        String code = request.getParameter("cityCode");
        List<Area> areaList = areaService.findWithCityCode(code);
        Result result = new Result();
        result.setData(areaList);
        System.out.println("GET Rest Call: /public/areaList ...");
        return result;
    }

    @RequestMapping("/checkPhone")
    public Result CheckPhone(HttpServletRequest request) {
        String phoneNumber = request.getParameter("phoneNumber");
        if (StringUtils.isEmpty(phoneNumber)) {
            return this.GenerateResult(ResultType.ParameterNeeded);
        }
        boolean exist = companyService.CheckPhone(phoneNumber);
        Result result = new Result();
        result.setResultStatus(exist ? -1 : 1);
        return result;
    }

    /**
     * 根据系统字典id获取下拉框数据源
     *
     * @param typeId 数据字典id
     * @return
     */
    @GetMapping("/dictList")
    public Result dictList(Integer typeId) {
        if (typeId == null) {
            return GenerateResult(ResultType.ParameterNeeded);
        }
        List<CommonSelectVo> list = new ArrayList<>();
        List<SysDictVo> dictVoList = sysCacheService.getDictList(typeId);
        if (dictVoList != null && !dictVoList.isEmpty()) {
            dictVoList.forEach(dictVo -> list.add(new CommonSelectVo(dictVo.getCode(), dictVo.getValue(), dictVo.getComments())));
        }
        return new Result(list);
    }
}
