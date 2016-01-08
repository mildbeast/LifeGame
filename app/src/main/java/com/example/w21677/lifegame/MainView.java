package com.example.w21677.lifegame;

/**
 * Created by w21677 on 1/8/2016.
 */
public interface MainView {
    void paused();
    void started();

    void initGrid(Board board);
    void updateGridItem(int row, int col, boolean state);
}
