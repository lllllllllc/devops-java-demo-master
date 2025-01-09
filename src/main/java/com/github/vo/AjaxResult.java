package com.github.vo;

import com.alibaba.fastjson.JSONObject;

public class AjaxResult extends JSONObject {

    private AjaxResult(int code, String msg, Object data) {
        super.put("code", code);
        super.put("msg", msg);
        if (data != null) {
            super.put("data", data);
        }
    }

    public static AjaxResult success() {
        return new AjaxResult(0, "success", null);
    }

    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(0, msg, data);
    }

    public static AjaxResult error(int code, String msg, Object data) {
        return new AjaxResult(code, msg, data);
    }
}