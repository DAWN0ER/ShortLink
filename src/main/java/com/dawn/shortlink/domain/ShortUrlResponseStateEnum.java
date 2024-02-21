package com.dawn.shortlink.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ShortUrlResponseStateEnum {
    ERROR(-1,"错误信息"),
    COLLISIONS(-2,"传入链接冲突"),
    SUCCESS(0,"成功保存"),
    NOTFOUND(-3,"未找到相应短链接"),
    GOAL(1,"查询到结果");

    private final int code;
    private final String massage;

    ShortUrlResponseStateEnum (int code, String massage) {
        this.code = code;
        this.massage = massage;
    }

    public int getCode() {
        return code;
    }

    public String getMassage() {
        return massage;
    }

}
