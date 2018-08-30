package com.recycle.controller;

import com.alibaba.fastjson.JSONObject;
import com.recycle.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 重写BasicErrorController,主要负责系统的异常页面的处理以及错误信息的显示
 *
 * @version 2016/5/31 11:22
 * @see org.springframework.boot.autoconfigure.web.BasicErrorController
 * @see org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration
 * @since JDK 7.0+
 */
@Controller
@RequestMapping(value = "/error")
@EnableConfigurationProperties({ServerProperties.class})
public class CustomErrorController implements ErrorController {

    private static Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    private ErrorAttributes errorAttributes;
    @Autowired
    private ServerProperties serverProperties;

    /**
     * 初始化ExceptionController
     *
     * @param errorAttributes
     */
    @Autowired
    public CustomErrorController(ErrorAttributes errorAttributes) {
        Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(value = {""})
    @ResponseBody
    public ModelAndView errorHtml(HttpServletRequest request,
                                  HttpServletResponse response) {
        response.setStatus(getStatus(request).value());
        Map<String, String> error = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.TEXT_HTML));

        Integer status = Integer.parseInt(error.get("status").toString());
        if (status == null
                || !(status == 403 || status == 404 || status == 500 || status == 503)) {
            if (status != null) {
                logger.info("发现未知HTTP状态：" + status);
            }
            // 默认未知错误
            status = 500;
        }

        error.put("params", JSONObject.toJSONString(request.getParameterMap()));
        error.put("method", request.getMethod());
        error.put("clientIP", HttpUtil.getClientIP(request));
        error.put("userAgent", request.getHeader("User-Agent"));

        // 记录在日志或者数据库
        if (status == 500) {
            logger.error(error + "");
        } else {
            logger.info(error + "");
        }

        // 不输出在页面上
        error.remove("trace");

        // model 包含：timestamp，status，error，exception，message，path
        return new ModelAndView("error/" + status.toString(), "error", error);
    }

    /**
     * Determine if the stacktrace attribute should be included.
     *
     * @param request  the source request
     * @param produces the media type produced (or {@code MediaType.ALL})
     * @return if the stacktrace attribute should be included
     */
    protected boolean isIncludeStackTrace(HttpServletRequest request,
                                          MediaType produces) {
        ErrorProperties.IncludeStacktrace include = this.serverProperties
                .getError().getIncludeStacktrace();
        if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
            return true;
        }
        if (include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
            return getTraceParameter(request);
        }
        return false;
    }

    /**
     * 获取错误的信息
     *
     * @param request
     * @param includeStackTrace
     * @return
     */
    private Map<String, String> getErrorAttributes(HttpServletRequest request,
                                                   boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(
                request);
        Map<String, Object> errorAttrs = this.errorAttributes
                .getErrorAttributes(requestAttributes, includeStackTrace);

        Map<String, String> error = new HashMap<String, String>();
        for (String key : errorAttrs.keySet()) {
            if (errorAttrs.get(key) instanceof Date) {
                error.put(key, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format((Date) errorAttrs.get(key)));
            } else if (errorAttrs.get(key) instanceof Exception) {
                StringBuilder stackTrace = new StringBuilder();
                for (StackTraceElement e : ((Exception) errorAttrs.get(key))
                        .getStackTrace()) {
                    stackTrace.append(e.toString() + "</br>");
                }
                error.put(key, stackTrace.toString());
            } else {
                error.put(key, errorAttrs.get(key).toString());
            }
        }

        return error;
    }

    /**
     * 是否包含trace
     *
     * @param request
     * @return
     */
    private boolean getTraceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        if (parameter == null) {
            return false;
        }
        return !"false".equals(parameter.toLowerCase());
    }

    /**
     * 获取错误编码
     *
     * @param request
     * @return
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    /**
     * 获取错误的路径
     *
     * @return
     */
    public String getErrorPath() {
        return null;
    }
}