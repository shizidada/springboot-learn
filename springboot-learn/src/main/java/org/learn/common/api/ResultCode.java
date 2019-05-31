package org.learn.common.api;

/**
 * 枚举了一些常用API操作码
 */
public enum ResultCode implements ICommonError {

    SUCCESS(200, "操作成功"),

    // 通用错误码 10000
    UNKNOWN_ERROR(10000, "未知错误"),
    VALIDATE_FAILED(10001, "参数检验失败"),

    // 用户错误 20000
    MEMBER_PASSWORD_NOT_EXIST(20000, "用户名或密码错误"),
    MEMBER_EXIST(20001, "用户名已存在"), // TODO ??
    NICKNAME_EXIST(20002, "昵称已存在"),
    PHONE_EXIST(20003, "手机号码已存在"),
    REGISTER_FAILED(20004, "注册失败"),
    MEMBER_LOGOUT_SUCCESS(20005, "用户成功退出"),
    MEMBER_LOGIN_SUCCESS(200006, "登录成功"),

    UNAUTHORIZED(401, "暂未登录或 token 已经过期"),
    FORBIDDEN(403, "FORBIDDEN 权限受限"),
    ACCESS_DENIED(403, "ACCESS DENIED 权限被拒绝"),

    FAILED(500, "操作失败");

    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public ICommonError setMessage(String message) {
        this.message = message;
        return this;
    }
}
