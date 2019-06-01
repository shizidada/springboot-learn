package org.learn.common.api;

/**
 * 通用返回对象
 */
public class AjaxResult {
    private boolean status;
    private String message;
    private Object data;
    private long code;

    private AjaxResult() {
    }

    private AjaxResult(long code, boolean status, String message, Object data) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    private static AjaxResult create(long code, boolean status, String message, Object result) {
        return new AjaxResult(code, status, message, result);
    }

    //定义一个默认的成功的处理，只传数据的方法，默认认为是请求正确的
    public static AjaxResult success(String message, Object result) {
        return AjaxResult.create(ResultCode.SUCCESS.getCode(), true, message, result);
    }

    public static AjaxResult failure() {
        return AjaxResult.create(ResultCode.UNKNOWN_ERROR.getCode(), false, ResultCode.UNKNOWN_ERROR.getMessage(), null);
    }

    public static AjaxResult failure(Long code, String message) {
        return AjaxResult.create(code, false, message, null);
    }

    public static AjaxResult unauthorized(String message) {
        return AjaxResult.create(ResultCode.UNAUTHORIZED.getCode(), false, message, null);
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AjaxResult{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
