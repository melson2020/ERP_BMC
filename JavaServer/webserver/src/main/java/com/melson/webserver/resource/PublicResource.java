package com.melson.webserver.resource;

import com.melson.base.BaseResource;
import com.melson.base.Result;
import com.melson.base.ResultType;
import com.melson.base.entity.Area;
import com.melson.base.entity.City;
import com.melson.base.entity.Company;
import com.melson.base.entity.Province;
import com.melson.base.service.IArea;
import com.melson.base.service.ICity;
import com.melson.base.service.ICompany;
import com.melson.base.service.IProvince;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author Nelson
 * @Description
 * @Date 2020/7/29
 */
@RestController
@RequestMapping(value = "/public")
public class PublicResource extends BaseResource {
    private final IProvince provinceService;
    private final ICity cityService;
    private final IArea areaService;
    private final ICompany companyService;


    public PublicResource(IProvince provinceService, ICity cityService, IArea areaService, ICompany companyService) {
        this.provinceService = provinceService;
        this.cityService = cityService;
        this.areaService = areaService;
        this.companyService = companyService;
    }

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
        if (StringUtils.isEmpty(phoneNumber)) return this.GenerateResult(ResultType.ParameterNeeded);
        boolean exist = companyService.CheckPhone(phoneNumber);
        Result result = new Result();
        result.setResultStatus(exist ? -1 : 1);
        return result;
    }


}
