package com.recycle;

import com.alibaba.fastjson.JSONObject;
import com.recycle.api.mohe.req.CreateTaskReq;
import com.recycle.api.mohe.req.VerifyLoginFirstReq;
import com.recycle.api.mohe.req.VerifyLoginSecondReq;
import com.recycle.api.mohe.resp.CreateTaskResq;
import com.recycle.api.mohe.resp.VerifyLoginResp;
import com.recycle.api.mohe.utils.CmUtils;
import org.junit.Test;

/**
 *   测试通过（OK）
 *  1.创建任务
 *  2.登陆-首次
 *  3.登陆-二次验证
 *  4.重试验证码
 *  5.查询记录
 *
 */
public class CmTest {

    private static final String partner_code = "xynn_mohe";
    private static final String partner_key = "1ca74b1bb18747fd87ae61f1ced5a0a0";

    /**
     * {"code":0,
     * "data":{"channel_code":"100000","channel_type":"YYS","created_time":"2018-07-10 09:56:05","identity_code":"440982199211272812","real_name":"张华成","user_mobile":"13726040244"},
     * "message":"任务创建成功",
     * "task_id":"TASKYYS100000201807100956050661532431"}
     * 创建任务
     */
    @Test
    public void testCreateTask() {
        CreateTaskReq createTaskReq;
        createTaskReq = new CreateTaskReq();
        createTaskReq.setUser_mobile("15018419560");
        createTaskReq.setIdentity_code("440921199108124210");
        createTaskReq.setReal_name("雷勇");

        CreateTaskResq createTaskResq = CmUtils.createTask(partner_code, partner_key, createTaskReq);
        System.out.println("----------------创建任务------------------------");
        System.out.println(JSONObject.toJSONString(createTaskResq));
        System.out.println("----------------创建任务------------------------");
    }

    /**
     * {"code":105,
     * "data":{"next_stage":"QUERY","fields":[{"name":"sms_code","label":"手机验证码","type":"text"}]},
     * "message":"请输入手机验证码",
     * "task_id":"TASKYYS100000201807100956050661532431"}
     *
     *登陆验证，第一阶段
     */
    @Test
    public void testLogin() {
        VerifyLoginFirstReq verifyLoginFirstReq = new VerifyLoginFirstReq();
        verifyLoginFirstReq.setTask_id("TASKYYS100000201807111655240720983572");
        verifyLoginFirstReq.setUser_name("15018419560");
        verifyLoginFirstReq.setUser_pass("100861");
        VerifyLoginResp verifyLoginResp = CmUtils.verifyLogin(partner_code, partner_key, verifyLoginFirstReq);
        System.out.println(JSONObject.toJSONString(verifyLoginResp));
    }

    /**
     * {"code":137,
     * "message":"任务已成功提交",
     * "task_id":"TASKYYS100000201807100956050661532431"}
     * 登陆验证-第二阶段
     */
    @Test
    public void testSecondLogin() {
        String resp = "{\"code\":105,\"data\":{\"next_stage\":\"QUERY\",\"fields\":[{\"name\":\"sms_code\",\"label\":\"手机验证码\",\"type\":\"text\"}]},\"message\":\"请输入手机验证码\",\"task_id\":\"TASKYYS100000201807111655240720983572\"}";
        VerifyLoginResp verifyLoginResp = JSONObject.parseObject(resp, VerifyLoginResp.class);

        VerifyLoginSecondReq verifyLoginSecondReq = new VerifyLoginSecondReq();
        verifyLoginSecondReq.setTask_id(verifyLoginResp.getTask_id());
        verifyLoginSecondReq.setSms_code("115165");//手机接 收到的验证码
        String next_stage = (String) verifyLoginResp.getData().get("next_stage");
        verifyLoginSecondReq.setTask_stage(next_stage);
        verifyLoginResp = CmUtils.verifyLoginNextStep(partner_code, partner_key, verifyLoginResp, verifyLoginSecondReq);
        System.out.println(JSONObject.toJSONString(verifyLoginResp));
    }

    /**
     * 重试发送验证码
     */
    @Test
    public void retrySendCode(){
        String taskId = "TASKYYS100000201807091457100711033951";
        VerifyLoginResp verifyLoginResp = CmUtils.retrySendSmCode(partner_code,partner_key,taskId);
        System.out.println(JSONObject.toJSONString(verifyLoginResp));
    }


    /**
     * {"code":0,
     * "data":{"identity_code":"440982199211272812","created_time":"2018-07-10 09:56:05","channel_src":"中国移动","user_mobile":"13726040244",
     * "task_data":{
     * "bill_info":[
     * {"bill_fee":"0",
     * "bill_record":[
     * {"fee_name":"固定费用","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"语音通信费","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"上网费","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"短彩信","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"增值业务费","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"代收费","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"其它费用","fee_amount":"0","user_number":"13726040244"}],
     * "bill_cycle":"2018-07","bill_total":"0"},
     * {"bill_fee":"0",
     * "bill_record":[
     * {"fee_name":"固定费用","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"语音通信费","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"上网费","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"短彩信","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"增值业务费","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"代收费","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"其它费用","fee_amount":"0","user_number":"13726040244"}],
     * "bill_cycle":"2018-06",
     * "bill_total":"0"},
     * {"bill_fee":"5250",
     * "bill_record":[
     * {"fee_name":"38元4G飞享套餐青春版","fee_amount":"3800","fee_category":"固定费用","user_number":"13726040244"},
     * {"fee_name":"基本通话费","fee_amount":"1450","fee_category":"语音通信费","user_number":"13726040244"},
     * {"fee_name":"上网费","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"短彩信","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"增值业务费","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"代收费","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"其它费用","fee_amount":"0","user_number":"13726040244"}],
     * "bill_cycle":"2018-05","bill_total":"5250"},
     * {"bill_fee":"4475",
     * "bill_record":[
     * {"fee_name":"38元4G飞享套餐青春版","fee_amount":"3800","fee_category":"固定费用","user_number":"13726040244"},
     * {"fee_name":"基本通话费","fee_amount":"675","fee_category":"语音通信费","user_number":"13726040244"},
     * {"fee_name":"上网费","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"短彩信","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"增值业务费","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"代收费","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"其它费用","fee_amount":"0","user_number":"13726040244"}],
     * "bill_cycle":"2018-04",
     * "bill_total":"4475"},
     * {"bill_fee":"6944",
     * "bill_record":[
     * {"fee_name":"38元4G飞享套餐青春版","fee_amount":"3800","fee_category":"固定费用","user_number":"13726040244"},
     * {"fee_name":"万能副号成员产品","fee_amount":"1000","fee_category":"固定费用","user_number":"13726040244"},
     * {"fee_name":"集团短信上行通信费","fee_amount":"30","fee_category":"语音通信费","user_number":"13726040244"},
     * {"fee_name":"基本通话费","fee_amount":"2114","fee_category":"语音通信费","user_number":"13726040244"},
     * {"fee_name":"上网费","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"短彩信","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"增值业务费","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"代收费","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"其它费用","fee_amount":"0","user_number":"13726040244"}],
     * "bill_cycle":"2018-03",
     * "bill_total":"6944"},
     * {"bill_fee":"6920",
     * "bill_record":[
     * {"fee_name":"58元4G飞享套餐 ","fee_amount":"5800","fee_category":"固定费用","user_number":"13726040244"},
     * {"fee_name":"万能副号成员产品","fee_amount":"1000","fee_category":"固定费用","user_number":"13726040244"},
     * {"fee_name":"语音通信费","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"上网费","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"点对点短信息费","fee_amount":"40","fee_category":"短彩信","user_number":"13726040244"},
     * {"fee_name":"外网短信通讯费","fee_amount":"80","fee_category":"短彩信","user_number":"13726040244"},
     * {"fee_name":"增值业务费","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"代收费","fee_amount":"0","user_number":"13726040244"},
     * {"fee_name":"其它费用","fee_amount":"0","user_number":"13726040244"}],
     * "bill_cycle":"2018-02",
     * "bill_total":"6920"}],
     * "family_info":[],
     * "data_info":[],
     * "sms_info":[
     * {"total_msg_count":"35",
     * "sms_record":[{
     * "msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-01 09:41:14","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-01 09:41:15","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-02 07:56:41","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-02 07:56:41","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-03 06:54:47","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-03 06:54:49","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-05 07:06:27","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-05 07:06:29","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-05 13:09:27","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-05 13:09:34","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-06 08:05:10","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-06 08:05:12","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-06 10:29:43","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-06 10:29:44","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-06 14:36:07","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-06 14:36:08","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-07 07:22:29","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-07 07:22:31","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-08 08:56:37","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-08 08:56:38","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-09 07:47:06","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-09 07:47:08","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-09 11:13:37","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-09 11:13:38","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-09 11:13:42","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-09 11:13:43","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"99901861","msg_start_time":"2018-07-09 14:15:58","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"99901861","msg_start_time":"2018-07-09 14:20:08","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"99901861","msg_start_time":"2018-07-09 14:26:58","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"99901861","msg_start_time":"2018-07-09 14:33:50","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"99901861","msg_start_time":"2018-07-09 14:47:56","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"99901861","msg_start_time":"2018-07-09 14:57:41","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"99901861","msg_start_time":"2018-07-09 15:00:33","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-10 07:34:45","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-07-10 07:34:46","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"}],
     * "msg_cycle":"2018-07"},
     * {"total_msg_count":"90",
     * "sms_record":[
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-01 00:56:46","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-01 00:56:46","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-01 20:07:36","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-01 20:07:37","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-01 20:41:43","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-01 20:41:44","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-01 20:54:32","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-01 20:54:34","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-02 09:09:41","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-02 09:09:41","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-03 10:01:15","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-03 10:01:16","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-04 06:34:42","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-04 06:34:43","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-05 07:43:08","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-05 07:43:33","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-07 07:00:39","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-07 07:01:18","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-08 06:52:05","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-08 06:52:06","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-09 11:42:04","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-09 11:42:06","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"99901861","msg_start_time":"2018-06-09 11:46:42","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"99901861","msg_start_time":"2018-06-09 11:46:58","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-10 23:39:50","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-10 23:39:50","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"99901861","msg_start_time":"2018-06-11 00:16:45","msg_type":"发送","msg_address":"未知","msg_other_num":"10658999"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"99901861","msg_start_time":"2018-06-11 00:16:50","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"99901861","msg_start_time":"2018-06-11 00:16:57","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-11 05:43:52","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-11 05:43:53","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"99901346","msg_start_time":"2018-06-11 10:43:45","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-12 07:28:33","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-12 07:28:33","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"10","msg_channel":"短信","msg_start_time":"2018-06-12 17:07:08","msg_type":"发送","msg_address":"未知","msg_other_num":"18218188804","contact_area":"广东省.广州市"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-13 07:54:16","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-13 07:54:18","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-13 13:31:39","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-13 13:31:42","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-13 20:13:28","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-13 20:13:30","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"99901346","msg_start_time":"2018-06-14 11:07:25","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-14 14:01:28","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-14 14:01:30","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"10","msg_channel":"短信","msg_start_time":"2018-06-14 14:39:32","msg_type":"发送","msg_address":"未知","msg_other_num":"18218188804","contact_area":"广东省.广州市"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-15 07:54:27","msg_type":"发送","msg_address":"未知","msg_other_num":"10658112210002"},
     * {"msg_cost":"0","msg_channel":"短信","msg_biz_name":"88000067","msg_start_time":"2018-06-15 07:54:28","msg_type":"接收","msg_address":"未知","msg_other_num":"未知"},
     * {"msg_cost":"10","msg_channel":"短信","msg_start_time":"2018-06-15 09:59:30","msg_type":"发送","msg_address":"未知","msg_other_num":"18218188804","contact_area":"广东省.广州市"},
     * {"msg_cost":"10","msg_channel":"短信","msg_start_time":"2018-06-15 10:01:30","msg_type":"发送","msg_address":"未知","msg_other_num":"18218188804","contact_area":"广东省.广州市"},
     * ........................................................................
     * 查询任务结果
     */
    @Test
    public void testQueryResult(){
        String taskId = "TASKYYS100000201807111655240720983572";
        VerifyLoginResp verifyLoginResp = CmUtils.queryTaskResult(partner_code,partner_key,taskId);
        System.out.println(JSONObject.toJSONString(verifyLoginResp));
    }

}
