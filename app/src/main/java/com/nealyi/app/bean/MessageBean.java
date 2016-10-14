package com.nealyi.app.bean;

/**
 * Created by nealyi on 16/10/13.
 */
public class MessageBean {

    /**
     * success : true
     * msg :
     */

    private boolean success;
    private String msg;

    public MessageBean() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                '}';
    }
}
