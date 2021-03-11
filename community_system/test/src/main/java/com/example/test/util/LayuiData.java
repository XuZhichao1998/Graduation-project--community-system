package com.example.test.util;

import java.util.List;

/**
 * 创建人：thj
 * 创建时间： 2018/6/26 17:39
 * 用途：
 */
public class LayuiData {

    private Integer count;
    private Integer code;
    private String msg;
    private List data;

    public LayuiData() {
    }

    public LayuiData(Integer count, Integer code, String msg, List data) {
        this.count = count;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
