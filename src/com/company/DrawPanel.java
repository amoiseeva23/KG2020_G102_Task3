package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {

    int scale=20;
    private ScreenConverter sc = new ScreenConverter(-scale,scale , 2*scale, 2*scale, 800, 800);

    public DrawPanel() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }


    private Grid grid = new Grid();
    JTextField field = new JTextField("20");


    @Override
    public void paint(Graphics g) {
        sc.setsW(getWidth());
        sc.setsH(getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D gr = bi.createGraphics();
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, getWidth(), getHeight());
        gr.dispose();

        gr = null;
        PixelDrawer pd = new BuffereImagePixelDrawer(bi);
        LineDrawer ld = new DDALineDrawer(pd);

        grid.setPd(pd);
        grid.drawGrid(sc,ld);
        grid.drawCoordinates(bi,sc);
        grid.clickCoordinat(click,bi,sc);
        ld.drawLine(sc.r2s(new RealPoint(-(grid.getLinesX().size()-1)/2*5,0)), sc.r2s(new RealPoint((grid.getLinesX().size()-1)/2*5,0)),Color.BLUE);
        ld.drawLine(sc.r2s(new RealPoint(0,-(grid.getLinesY().size()-1)/2*5)), sc.r2s(new RealPoint(0,(grid.getLinesY().size()-1)/2*5)),Color.RED);
        /**/
        /**/
        scale=Integer.parseInt(field.getText());
        sc=new ScreenConverter(-scale,scale , 2*scale, 2*scale, 800, 800);
        field.setPreferredSize(new Dimension(200, 70));
        field.setForeground(Color.BLACK);
        g.drawImage(bi, 0, 0, null);
        this.add(field);
    }

    private RealPoint oldPoint = null;
    private RealPoint click = new RealPoint(0,0);

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        click = sc.s2r(new ScreenPoint(mouseEvent.getX(), mouseEvent.getY()));
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        oldPoint = sc.s2r(new ScreenPoint(mouseEvent.getX(), mouseEvent.getY()));
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        oldPoint = null;
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        RealPoint newPoint = sc.s2r(new ScreenPoint(mouseEvent.getX(), mouseEvent.getY()));
        if (oldPoint != null){
            double dx = newPoint.getX() - oldPoint.getX();
            double dy = newPoint.getY() - oldPoint.getY();
            sc.setrX(sc.getrX() - dx);
            sc.setrY(sc.getrY() - dy);
            grid.addX(sc,dx);
            grid.addY(sc,dy);
            grid.changeGrid();
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}
