package com.dawn.shortlink.domain.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.FileInputStream;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ShortUrlResponseStateEnum {

    GOAL(1,"查询到结果"),
    SUCCESS(0,"成功保存"),

    ERROR(-1,"错误信息"),
    FAILURE(-1,"服务运行失败"),

    COLLISIONS(-2,"传入链接冲突"),
    ALREADY_SAVED(-2,"链接已经被保存"),

    NOTFOUND(-3,"未找到短链接相关信息");



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
