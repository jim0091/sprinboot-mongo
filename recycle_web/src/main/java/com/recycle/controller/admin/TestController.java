package com.recycle.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.recycle.model.Test;
import com.recycle.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @ResponseBody
    @RequestMapping(value = {"/test"},method = RequestMethod.GET,produces = {"application/json;charset=utf-8"})
    public String getTestData(){
        PageInfo<Test> testList = testService.getAllInfo();
        return JSONObject.toJSONString(testList);
    }
}
