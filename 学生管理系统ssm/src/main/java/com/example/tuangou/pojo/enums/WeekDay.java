package com.example.tuangou.pojo.enums;

/**
 * 枚举了一些常用API操作码
 * Created by macro on 2019/4/19.
 */
public enum WeekDay  {
    MONDAY("1",  "星期一"),
    TUESDAY("2", "星期二"),
    WEDNESDAY("3", "星期三"),
    THURSDAY("4", "星期四"),
    FRIDAY("5", "星期五"),
    SATURDAY("6","星期六"),
    SUNDAY("7", "星期日");

    private String code;
    private String message;

    private WeekDay(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
