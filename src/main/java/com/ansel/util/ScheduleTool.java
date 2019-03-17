package com.ansel.util;

import java.util.*;

/**
 * @Author chenshuai
 * @Description TODO
 * @Date 2019/3/13 20:08
 * @Version 1.0
 **/

public class ScheduleTool {
    public static void main(String[] args) {

        //开始上课日期
        Date date = new Date();
        System.err.println("getTime:" + date.getTime());
        //初始化日期工具类
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        //获取当前日期是属于周一
//        int week_index = rightNow.get(Calendar.DAY_OF_WEEK) - 1;
//        System.out.println("week_index:" + week_index);

        //总课时数
        int countNUM = 6;

        //每周上课的日期 和时间
        List<WebClassWeekListBo> classWeekListBos = new ArrayList<>();
        WebClassWeekListBo bo = new WebClassWeekListBo();
        bo.setWeek(3);
        bo.setCourseDate("08:00-10:00");
        classWeekListBos.add(bo);
        WebClassWeekListBo bo1 = new WebClassWeekListBo();
        bo1.setWeek(3);
        bo1.setCourseDate("10:00-12:00");
        classWeekListBos.add(bo1);
        WebClassWeekListBo bo2 = new WebClassWeekListBo();
        bo2.setWeek(4);
        bo2.setCourseDate("12:00-14:00");
        classWeekListBos.add(bo2);
        WebClassWeekListBo bo3 = new WebClassWeekListBo();
        bo3.setWeek(5);
        bo3.setCourseDate("14:00-16:00");
        classWeekListBos.add(bo3);


        //总的课表
        List<WebClassWeekListBo> allIntegers = new ArrayList<>();

        //这是第几节课
        int count = 0;

        //将当前日期一直往后加一天
        for (int i = 0; i < 10000; i++) {
            //获取当前天是周几
            int week = rightNow.get(Calendar.DAY_OF_WEEK) - 1;
            //将这天是周几和 有规律的排课表对比
            //临时课表
            List<WebClassWeekListBo> integersTemp = new ArrayList<>();

            for (int j = 0; j < classWeekListBos.size(); j++) {
                if ((classWeekListBos.get(j).getWeek()==week) && count < countNUM) {
//                    if (j > 0) {
//                        int fristI = 0;
//                        //如果后一个和前一个相等的情况，那就直接跳出当前循环
////                        if (classWeekListBos.get(j).getWeek()==classWeekListBos.get(j - 1).getWeek() &&
////                                fristI==i
////                        ) {
//////                            j++;
//////                            i++;
////                            continue;
////                        }
//                    }
                    WebClassWeekListBo webClassWeekListBo = new WebClassWeekListBo();
                    //获取当前时间
                    webClassWeekListBo.setDate(rightNow.getTime());
                    webClassWeekListBo.setWeek(week);
                    webClassWeekListBo.setCourseDate(classWeekListBos.get(j).getCourseDate());
                    allIntegers.add(webClassWeekListBo);
                    if (allIntegers.get(count).getWeek()==week){

                    }
                    classWeekListBos.remove(j);
                    count++;
                    if (classWeekListBos.size()==0) {
                        WebClassWeekListBo bo4 = new WebClassWeekListBo();
                        bo4.setWeek(3);
                        bo4.setCourseDate("08:00-10:00");
                        classWeekListBos.add(bo4);
                        WebClassWeekListBo bo5 = new WebClassWeekListBo();
                        bo5.setWeek(3);
                        bo5.setCourseDate("10:00-12:00");
                        classWeekListBos.add(bo5);
                        WebClassWeekListBo bo6 = new WebClassWeekListBo();
                        bo6.setWeek(4);
                        bo6.setCourseDate("12:00-14:00");
                        classWeekListBos.add(bo6);
                        WebClassWeekListBo bo7 = new WebClassWeekListBo();
                        bo7.setWeek(5);
                        bo7.setCourseDate("14:00-16:00");
                        classWeekListBos.add(bo7);
                    }
                }
            }
            //每次都添加一天
            rightNow.add(Calendar.DAY_OF_YEAR, 1);
        }

        classWeekListSort(allIntegers);
        for (int i = 0; i < allIntegers.size(); i++) {
            System.err.println(allIntegers.get(i));
        }
    }

    private static void classWeekListSort(List<WebClassWeekListBo> classWeekList) {
        Collections.sort(classWeekList, new Comparator<WebClassWeekListBo>() {
            @Override
            public int compare(WebClassWeekListBo o1, WebClassWeekListBo o2) {
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dt1 = o1.getDate();
                    Date dt2 = o2.getDate();
                    if (dt1.getTime() > dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    System.err.println("排列时间报错" + e);
                }
                return 0;

            }
        });
    }
}