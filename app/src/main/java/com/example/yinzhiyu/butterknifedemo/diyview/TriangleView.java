package com.example.yinzhiyu.butterknifedemo.diyview;

/**
 * 以三角形为单元
 * 三角形的三个顶点坐标，即上文分析中提到的start、end1、end2坐标；
 * 三角形的背景色；
 * 三角形目前加载过程中current1和current2的坐标位置；
 * Created by yinzhiyu on 2018/1/3.
 */

public class TriangleView {

    //起始点坐标
    public int startX;
    public int startY;
    //终点坐标
    public int endX1;
    public int endY1;
    public int endX2;
    public int endY2;
    //当前延伸中的坐标位置
    public int currentX1;
    public int currentY1;

    public int currentX2;
    public int currentY2;
    //背景色
    public String color;
}
