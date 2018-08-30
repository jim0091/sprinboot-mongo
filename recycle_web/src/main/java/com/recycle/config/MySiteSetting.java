package com.recycle.config;

import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;

@Component
@ConfigurationProperties
public class MySiteSetting {

    @Value("${com.recycle.site.host}")
    private String host;
    @Value("${com.recycle.site.staticHost}")
    private String staticHost;
    @Value("${com.recycle.site.whiteDomainList}")
    private String whiteDomainList;
    @Value("${com.recycle.site.siteDirPath}")
    private String siteDirPath;
    @Value("${com.recycle.site.uploadDir}")
    private String uploadDir;
    @Value("${com.recycle.site.session}")
    private String sessionName;
    @Value("${server.context-path}")
    private String contextPath;
    @Value("com.api.cm.partner_code")
    private String cm_partner_code;
    @Value("com.api.cm.partner_key")
    private String cm_partner_key;
    @Value("com.api.bg.partner_code")
    private String bg_partner_code;
    @Value("com.api.bg.partner_key")
    private String bg_partner_key;
    @Value("com.api.bg.app_name")
    private String bg_app_name;
    @Value("com.api.im.partner_code")
    private String im_partner_code;
    @Value("com.api.im.partner_key")
    private String im_partner_key;
    @Value("com.api.sms.account")
    private String sms_account;
    @Value("com.api.sms.pass")
    private String sms_pass;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUploadDir() {

        return uploadDir;
    }

    public String getStaticHost() {
        return staticHost;
    }

    public void setStaticHost(String staticHost) {
        this.staticHost = staticHost;
    }

    public String getWhiteDomainList() {
        return whiteDomainList;
    }

    public void setWhiteDomainList(String whiteDomainList) {
        this.whiteDomainList = whiteDomainList;
    }

    public String getSiteDirPath() throws URISyntaxException {

        if (StringUtils.isNullOrEmpty(siteDirPath)) {
            siteDirPath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
        }

        return siteDirPath;
    }

    public void setSiteDirPath(String siteDirPath) {
        this.siteDirPath = siteDirPath;
    }

    /**
     * 返回上传文件夹路径
     *
     * @param absolute 是否绝对路径
     * @return
     * @throws URISyntaxException
     */
    public String getUploadDir(boolean absolute) throws URISyntaxException {
        if (StringUtils.isNullOrEmpty(uploadDir)) {
            return uploadDir;
        }

        if (absolute) {
            return this.getSiteDirPath() + uploadDir;
        } else {
            return uploadDir;
        }
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getCm_partner_code() {
        return cm_partner_code;
    }

    public void setCm_partner_code(String cm_partner_code) {
        this.cm_partner_code = cm_partner_code;
    }

    public String getCm_partner_key() {
        return cm_partner_key;
    }

    public void setCm_partner_key(String cm_partner_key) {
        this.cm_partner_key = cm_partner_key;
    }

    public String getBg_partner_code() {
        return bg_partner_code;
    }

    public void setBg_partner_code(String bg_partner_code) {
        this.bg_partner_code = bg_partner_code;
    }

    public String getBg_partner_key() {
        return bg_partner_key;
    }

    public void setBg_partner_key(String bg_partner_key) {
        this.bg_partner_key = bg_partner_key;
    }

    public String getBg_app_name() {
        return bg_app_name;
    }

    public void setBg_app_name(String bg_app_name) {
        this.bg_app_name = bg_app_name;
    }

    public String getIm_partner_code() {
        return im_partner_code;
    }

    public void setIm_partner_code(String im_partner_code) {
        this.im_partner_code = im_partner_code;
    }

    public String getIm_partner_key() {
        return im_partner_key;
    }

    public void setIm_partner_key(String im_partner_key) {
        this.im_partner_key = im_partner_key;
    }

    public String getSms_account() {
        return sms_account;
    }

    public void setSms_account(String sms_account) {
        this.sms_account = sms_account;
    }

    public String getSms_pass() {
        return sms_pass;
    }

    public void setSms_pass(String sms_pass) {
        this.sms_pass = sms_pass;
    }
}
