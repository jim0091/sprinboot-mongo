package com.recycle;

import com.alibaba.fastjson.JSONObject;
import com.recycle.api.sms.req.ReportReq;
import com.recycle.api.sms.req.SearchNumberReq;
import com.recycle.api.sms.req.SmsMOReq;
import com.recycle.api.sms.req.SmsMTReq;
import com.recycle.api.sms.resp.ReportResp;
import com.recycle.api.sms.resp.SearchNumberResq;
import com.recycle.api.sms.resp.SmsMOResq;
import com.recycle.api.sms.resp.SmsMTResp;
import com.recycle.api.sms.utils.SmsUtil;
import org.junit.Test;

public class SmsTest {

    private static final String spId = "107065";
    private static final String loginName = "gzagrz";
    private static final String password = "94FCDD5F006BF9FEA2DA0F2E35D01903";
    /**
     * 查询剩余条数短信条数
     */
    @Test
    public void testSearchNumberResq(){
        SearchNumberReq searchNumberReq = new SearchNumberReq();
        searchNumberReq.setSpId(spId);
        searchNumberReq.setLoginName(loginName);
        searchNumberReq.setPassword(password);
        SearchNumberResq searchNumberResq = SmsUtil.searchNumberResq(searchNumberReq);
        System.out.println(JSONObject.toJSONString(searchNumberResq));
    }

    /**
     * 发送短信
     */
    @Test
    public void sendSms(){
        SmsMTReq smsMTReq = new SmsMTReq();
        smsMTReq.setSpId(spId);
        smsMTReq.setLoginName(loginName);
        smsMTReq.setPassword(password);
        smsMTReq.setMobiles("18320310321");
        smsMTReq.setContent("【信用牛牛】你的短信验证码是：66666 ,请注意保密.");
        SmsMTResp smsMTResp = SmsUtil.smsSend(smsMTReq);
        System.out.println(JSONObject.toJSONString(smsMTResp));

        //{"description":"发送成功,需要人工审核","result":1000,"smsId":"180712145204630140"}
    }

    /**
     * 接收短信
     */
    @Test
    public void testReply(){
        SmsMOReq smsMOReq = new SmsMOReq();
        smsMOReq.setSpId(spId);
        smsMOReq.setLoginName(loginName);
        smsMOReq.setPassword(password);
        SmsMOResq smsMOResq = SmsUtil.receiveSMS(smsMOReq);
        System.out.println(JSONObject.toJSONString(smsMOResq));
    }

    /**
     * 导出报告
     */
    @Test
    public void testReport(){
        ReportReq reportReq = new ReportReq();
        reportReq.setSpId(spId);
        reportReq.setLoginName(loginName);
        reportReq.setPassword(password);
        ReportResp reportResp = SmsUtil.reportRep(reportReq);
        System.out.println(JSONObject.toJSONString(reportResp));
    }

}
