package com.recycle.config;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/druid/*", initParams = {
		@WebInitParam(name = "allow", value = ""),// IP白名单
													// (没有配置或者为空，则允许所有访问)
		@WebInitParam(name = "deny", value = ""),// IP黑名单
													// (存在共同时，deny优先于allow)
		@WebInitParam(name = "loginUsername", value = "root"),// 用户名
		@WebInitParam(name = "loginPassword", value = "root"),// 密码
		@WebInitParam(name = "resetEnable", value = "true") // 禁用HTML页面上的“ResetAll”功能
})
public class DruidStatViewServlet extends StatViewServlet {

}
