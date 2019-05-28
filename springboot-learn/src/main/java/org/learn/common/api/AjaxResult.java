package org.learn.common.api;

/**
 * 通用返回对象
 */
public class AjaxResult {
    private boolean status;
    private String message;
    private Object data;

    public AjaxResult() {
    }

    public AjaxResult(boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static AjaxResult create(boolean status, String message, Object result) {
        return new AjaxResult(status, message, result);
    }

    //定义一个默认的成功的处理，只传数据的方法，默认认为是请求正确的
    public static AjaxResult success(String message, Object result) {
        return AjaxResult.create(true, message, result);
    }

    public static AjaxResult failure() {
        return AjaxResult.create(false, ResultCode.UNKNOWN_ERROR.getErrMessage(), null);
    }

    public static AjaxResult failure(String message) {
        return AjaxResult.create(false, message, null);
    }

    public static AjaxResult failure(String message, Object data) {
        return AjaxResult.create(false, message, data);
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
