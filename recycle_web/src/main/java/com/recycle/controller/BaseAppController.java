package com.recycle.controller;

import com.recycle.model.User;
import org.springframework.stereotype.Controller;

@Controller
public class BaseAppController extends BaseController {

    @Override
    protected String getSessionName() {
        return "app";
    }

    /**
     * 设置当前登录用户名称
     *
     * @param user
     */
    public boolean setCurrentUser(User user) {
        try {
            redisUtil.set(mySiteSetting.getSessionName()+this.getSessionName()+"_"+user.getPhone(),user);
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
    public void exitCurrentUser(User user) {
        try {
            redisUtil.set(mySiteSetting.getSessionName()+this.getSessionName()+"_"+user.getPhone(),null);
        } catch (Exception ex) {

        }
    }
}
