package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LogGrid implements IGrid{

    private PixelDrawer pd;
    private ArrayList<Line> linesX;
    private ArrayList<Line> linesY;

    public LogGrid(PixelDrawer pd) {
        linesX=createLinesX();
        linesY=createLinesY();
        this.pd = pd;
    }

    public LogGrid(){
        linesX=createLinesX();
        linesY=createLinesY();
    }

    private ArrayList<Line> createLinesX(){
        ArrayList<Line> lines = new ArrayList<Line>();
        for(int i = 0;i<=40;i=i+10){
            lines.add(new Line(i*Math.log10(i/10), 0, i*Math.log10(i/10), 40));
        }
        return lines;
    }

    private ArrayList<Line> createLinesY(){
        ArrayList<Line> lines = new ArrayList<Line>();
        for(int i = 0;i<=40;i=i+10){
            lines.add(new Line(0, i*Math.log10(i/10), 40, i*Math.log10(i/10)));
        }
        return lines;
    }

    public BufferedImage drawCoordinates(BufferedImage bi, ScreenConverter sc){

        Graphics g = bi.getGraphics();
        g.setColor(Color.BLACK);
        double x = linesX.get(0).getP1().getX();
        double y = linesY.get(0).getP1().getY();
        for(int i =0;i<linesX.size();i++) {
            ScreenPoint p1 = sc.r2s(new RealPoint(x+(10*i*Math.log10(i)), 1));
            g.drawString(Integer.toString((int)(x+(10*i))), p1.getX(), p1.getY());
        }
        for(int i =0;i<linesY.size();i++) {
            if(y+(5*i)==  0)continue;
            ScreenPoint p1 = sc.r2s(new RealPoint(0, y+(10*i*Math.log10(i))));
            g.drawString(Integer.toString((int)(y+(10*i))), p1.getX(), p1.getY());
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
        int lengthX = linesX.size()*10;
        int lengthY = linesY.size()*10;
        for(int i = 0;i<linesX.size();i++){
            linesX.get(i).setP1(new RealPoint(linesX.get(i).getP1().getX(),0));
            linesX.get(i).setP2(new RealPoint(linesX.get(i).getP1().getX(),lengthX));
        }
        for(int i = 0;i<linesY.size();i++){
            linesY.get(i).setP1(new RealPoint(0,linesY.get(i).getP1().getY()));
            linesY.get(i).setP2(new RealPoint(lengthY,linesY.get(i).getP1().getY()));
        }
    }

    public void addX(ScreenConverter sc,double dx){
        int count = 4;
        while (Math.abs(dx)>0.1){

            int lengthX = linesX.size()*10;

            double x = 10*linesX.size()*Math.log10(linesX.size());
            linesX.add(new Line(x, 0, x, lengthX));

            if(count==0)break;
            count--;
        }
    }

    public void addY(ScreenConverter sc,double dy){
        int count = 4;
        while (Math.abs(dy)>0.1){
            int lengthY = linesY.size()*10;

            double y = 10*linesY.size()*Math.log10(linesY.size());
            linesY.add(new Line(0, y, lengthY,y ));



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
