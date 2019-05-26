package org.learn.common.api;

/**
 * 枚举了一些常用API操作码
 */
public enum ResultCode implements ICommonError {

    // 通用错误码 10001
    UNKNOWN_ERROR(10001, "未知错误"),
    VALIDATE_FAILED(10002, "参数检验失败"),

    // 用户错误
    USER_EXIST(20001, "用户名已存在"),
    NICKNAME_EXIST(20002, "昵称已存在"),
    PHONE_EXIST(20003, "手机号码已存在"),

    SUCCESS(200, "操作成功"),

    UNAUTHORIZED(401, "暂未登录或 token 已经过期"),
    FORBIDDEN(403, "没有相关权限"),

    FAILED(500, "操作失败");

    private long errCode;
    private String errMessage;

    private ResultCode(long errCode, String errMessage) {
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public long getErrCode() {
        return this.errCode;
    }

    public String getErrMessage() {
        return this.errMessage;
    }

    public ICommonError setErrMessage(String errMessage) {
        this.errMessage = errMessage;
        return this;
    }
}
