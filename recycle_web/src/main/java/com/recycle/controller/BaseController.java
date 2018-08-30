package com.recycle.controller;

import com.recycle.config.MySiteSetting;
import com.recycle.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
public abstract class BaseController {

    @Autowired
    public RedisUtil redisUtil;
    @Autowired
    public MySiteSetting mySiteSetting;

    /**
     * 定义请求参数的名字key
     */
    public static final String REQUEST_DATA = "data";

    /**
     * 返回的结果Key
     */
    public final static String KEY_RESULT = "result";
    /**
     * 返回结果码Key
     */
    public final static String KEY_RESULT_CODE = "resultCode";
    /**
     * 返回结果数据Key
     */
    public final static String KEY_RESULT_DATA = "resultData";
    /**
     * 返回结果提示Key
     */
    public final static String KEY_RESULT_MSG = "resultMsg";
    /**
     * 返回结果路径Key
     */
    public final static String KEY_RESULT_PATH = "resultPath";

    /**
     * 成功
     */
    public static final String SUCCESS = "success";
    /**
     * 登录
     */
    public static final String LOGIN = "login";
    /**
     * 错误
     */
    public static final String ERROR = "error";
    /**
     * 参数异常
     */
    public static final String INPUT = "input";

    /**
     * 成功
     */
    public final static int RESULT_SUCCESS = 0;
    /**
     * 请求错误
     */
    public final static int RESULT_ERROR = -1;
    /**
     * 请求参数错误
     */
    public final static int RESULT_INPUT = -2;
    /**
     * 请先登录
     */
    public final static int RESULT_LOGIN = -3;
    /**
     * 下线通知：已在其他浏览器登录
     */
    public final static int RESULT_OFFLINE_LOGIN = -4;
    /**
     * 网络不稳定，请重试
     */
    public final static int RESULT_BE_LAZY = -5;
    /**
     * 您的帐号异常，请联系客服
     */
    public final static int RESULT_DISABLE = -6;

    /**
     * 该登录账号已存在，请使用其他账号
     */
    public final static int ERRORS_ONE = 1;
    /**
     * 不存在该账号
     */
    public final static int ERRORS_TWO = 2;
    /**
     * 密码错误
     */
    public final static int ERRORS_THREE = 3;
    /**
     * 该手机号已被绑定，请使用其他手机号码
     */
    public final static int ERRORS_FOUR = 4;
    /**
     * 发生短信失败，请稍后重试
     */
    public final static int ERRORS_FIVE = 5;
    /**
     * 获取验证码失败，请重新尝试
     */
    public final static int ERRORS_SIX = 6;
    /**
     * 修改密码失败，请重新尝试
     */
    public final static int ERRORS_SEVEN = 7;
    /**
     * 短信验证码错误
     */
    public final static int ERRORS_EIGHT = 8;
    /**
     * 验证码超时
     */
    public final static int ERRORS_NINE = 9;
    /**
     * 验证码已被使用
     */
    public final static int ERRORS_TEN = 10;

    /**
     * 结果码映射
     */
    public final static Map<Integer, String> RESULT_MAP;
    static {
        RESULT_MAP = new HashMap<Integer, String>();
        RESULT_MAP.put(RESULT_LOGIN, "请先登录");
        RESULT_MAP.put(RESULT_DISABLE, "您的帐号异常，请联系客服");
        RESULT_MAP.put(RESULT_OFFLINE_LOGIN, "下线通知：已在其他浏览器登录");
        RESULT_MAP.put(RESULT_SUCCESS, "成功");
        RESULT_MAP.put(RESULT_INPUT, "请求参数错误");

        RESULT_MAP.put(ERRORS_ONE, "该登录账号已存在，请使用其他账号");
        RESULT_MAP.put(ERRORS_TWO, "不存在该账号");
        RESULT_MAP.put(ERRORS_THREE, "密码错误");
        RESULT_MAP.put(ERRORS_FOUR, "该手机号已被绑定，请使用其他手机号码");
        RESULT_MAP.put(ERRORS_FIVE, "发生短信失败，请稍后重试");
        RESULT_MAP.put(ERRORS_SIX, "获取验证码失败，请重新尝试");
        RESULT_MAP.put(ERRORS_SEVEN, "修改密码失败，请重新尝试");
        RESULT_MAP.put(ERRORS_EIGHT, "短信验证码错误");
        RESULT_MAP.put(ERRORS_NINE, "验证码超时");
        RESULT_MAP.put(ERRORS_TEN, "验证码已被使用");
    }

    /**
     * Session名称
     *
     * @return
     */
    protected abstract String getSessionName();

}
