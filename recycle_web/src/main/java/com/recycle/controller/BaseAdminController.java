package com.recycle.controller;

import com.recycle.model.SysUser;
import org.springframework.stereotype.Controller;

@Controller
public class BaseAdminController extends BaseController {

    @Override
    protected String getSessionName() {
        return "admin";
    }

    /**
     * 设置当前登录用户名称
     *
     * @param user
     */
    public boolean setCurrentUser(SysUser user) {
        try {
            redisUtil.set(mySiteSetting.getSessionName()+this.getSessionName()+"_"+user.getLogin_name(),user);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 退出当前用户
     *
     * @param user
     */
    public void exitCurrentUser(SysUser user) {
        try {
            redisUtil.set(mySiteSetting.getSessionName()+this.getSessionName()+"_"+user.getLogin_name(),null);
        } catch (Exception ex) {

        }
    }
}
