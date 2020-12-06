package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Grid implements IGrid{
    private PixelDrawer pd;
    private ArrayList<Line> linesX;
    private ArrayList<Line> linesY;

    public Grid(PixelDrawer pd) {
        linesX=createLinesX();
        linesY=createLinesY();
        this.pd = pd;
    }

    public Grid(){
        linesX=createLinesX();
        linesY=createLinesY();
    }

    private ArrayList<Line> createLinesX(){
        ArrayList<Line> lines = new ArrayList<Line>();
        for(int i = -20;i<=20;i=i+5){
            lines.add(new Line(i, -20, i, 20));
        }
        return lines;
    }

    private ArrayList<Line> createLinesY(){
        ArrayList<Line> lines = new ArrayList<Line>();
        for(int i = -20;i<=20;i=i+5){
            lines.add(new Line(-20, i, 20, i));
        }
        return lines;
    }

    public BufferedImage drawCoordinates(BufferedImage bi,ScreenConverter sc){

        Graphics g = bi.getGraphics();
        g.setColor(Color.BLACK);
        double x = linesX.get(0).getP1().getX();
        double y = linesY.get(0).getP1().getY();
        for(int i =0;i<linesX.size();i++) {
            ScreenPoint p1 = sc.r2s(new RealPoint(x+(5*i), -1));
            g.drawString(Integer.toString((int)(x+(5*i))), p1.getX(), p1.getY());
        }
        for(int i =0;i<linesY.size();i++) {
            if(y+(5*i)==0)continue;
            ScreenPoint p1 = sc.r2s(new RealPoint(0, y+(5*i)));
            g.drawString(Integer.toString((int)(y+(5*i))), p1.getX(), p1.getY());
        }
        return  bi;
    }

    public BufferedImage clickCoordinat(RealPoint rp,BufferedImage bi,ScreenConverter sc){
        Graphics g = bi.getGraphics();
        g.setColor(Color.BLACK);
        String str = "x="+rp.getX() +" y="+ rp.getY();
        g.drawString(str,10,10);
        return bi;
    }

    public void drawGrid(ScreenConverter sc,LineDrawer ld){
        for(int i=0;i<linesX.size();i++){
            ScreenPoint p1= sc.r2s(linesX.get(i).getP1());
            ScreenPoint p2= sc.r2s(linesX.get(i).getP2());
            ld.drawLine(p1,p2,Color.BLACK);
        }
        for(int i=0;i<linesY.size();i++){
            ScreenPoint p1= sc.r2s(linesY.get(i).getP1());
            ScreenPoint p2= sc.r2s(linesY.get(i).getP2());
            ld.drawLine(p1,p2,Color.BLACK);
        }

    }

    public void changeGrid(){
        int lengthX = linesX.size()*5;
        int lengthY = linesY.size()*5;
        for(int i = 0;i<linesX.size();i++){
            linesX.get(i).setP1(new RealPoint(linesX.get(i).getP1().getX(),-lengthX));
            linesX.get(i).setP2(new RealPoint(linesX.get(i).getP1().getX(),lengthX));
        }
        for(int i = 0;i<linesY.size();i++){
            linesY.get(i).setP1(new RealPoint(-lengthY,linesY.get(i).getP1().getY()));
            linesY.get(i).setP2(new RealPoint(lengthY,linesY.get(i).getP1().getY()));
        }
    }

    public void addX(ScreenConverter sc,double dx){
        int count = 4;
        while (Math.abs(dx)>0.1){

            int lengthX = linesX.size()*5;
            if(dx<0) {
                double x = linesX.get(linesX.size() - 1).getP1().getX() + 5;
                linesX.add(new Line(x, -lengthX, x, lengthX));

            }
            else{
                double x = linesX.get(0).getP1().getX() - 5;
                linesX.add(0,new Line(x, -lengthX, x, lengthX));

            }
            if(count==0)break;
            count--;
        }
    }

    public void addY(ScreenConverter sc,double dy){
        int count = 4;
        while (Math.abs(dy)>0.1){
            int lengthY = linesY.size()*5;
            if(dy<0) {
                double y = linesY.get(linesY.size() - 1).getP1().getY() + 5;
                linesY.add(new Line(-lengthY, y, lengthY,y ));

            }
            else{
                double y = linesY.get(0).getP1().getY() - 5;
                linesY.add(0,new Line(-lengthY, y, lengthY, y));

            }
            if(count==0)break;
            count--;
        }

    }



    public ArrayList<Line> getLinesX() {
        return linesX;
    }

    public ArrayList<Line> getLinesY() {
        return linesY;
    }

    public void setPd(PixelDrawer pd) {
        this.pd = pd;
    }
}
