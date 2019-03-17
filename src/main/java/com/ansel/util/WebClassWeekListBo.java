package com.ansel.util;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 排课业务实体类
 *
 * @author zhaohongye
 * @date 2019/3/12
 */
public class WebClassWeekListBo implements Serializable {
    /**
     * 上课日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;
    /**
     * 周几上课
     */
    private int week;
    /**
     * 上课开始时间-结束时间
     */
    private String courseDate;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(String courseDate) {
        this.courseDate = courseDate;
    }

    @Override
    public String toString() {
        return "WebClassWeekListBo{" +
                "date=" + date +
                ", week=" + week +
                ", courseDate='" + courseDate + '\'' +
                '}';
    }
}

