package org.learn.bean;

import lombok.Data;

@Data
public class Results {

    private Object data;

    private Boolean status; // 是否成功

    private String message;

    private Integer code; // 状态码
}
