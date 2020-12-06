package com.company;

import java.util.ArrayList;

public interface IGrid {

    public void drawGrid(ScreenConverter sc,LineDrawer ld);

    public void changeGrid();

    public void addX(ScreenConverter sc,double dx);

    public void addY(ScreenConverter sc,double dy);


}
