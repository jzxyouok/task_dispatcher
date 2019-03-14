package com.hptpd.taskdispatcherserver.component;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-14 15:59
 * \*
 * \* Description:
 * \
 *
 * @author liucheng
 */
@SuppressWarnings("ALL")
public class Result {

    public static final Integer SUCCESS = 0;
    public static final Integer ERROR = 1;

    /**
     * 返回结果
     */
    private Integer errCode;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private String data;

    public Result() {

    }

    public Result(Integer errCode, String msg) {
        this.errCode = errCode;
        this.msg = msg;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static Result setResult(Integer errCode, String msg) {
        Result res = new Result();
        res.setMsg(msg);
        res.setErrCode(errCode);
        return res;
    }

    public static Result setResult(Integer errCode, String msg, String data) {
        Result res = new Result();
        res.setMsg(msg);
        res.setErrCode(errCode);
        res.setData(data);
        return res;
    }

    @Override
    public String toString() {
        return "Result [result=" + errCode + ", msg=" + msg + ", data=" + data
                + "]";
    }
}
