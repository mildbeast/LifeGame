package com.example.w21677.lifegame;

import java.util.List;

/**
 * Created by w21677 on 12/18/2015.
 */
public class Cell {
    private boolean currentState;
    private boolean nextState;
    private List<Cell> neighbor;
    private MainView view;
    private int row, col;

    public Cell(int i, int j, MainView v){
        row = i;
        col = j;
        view = v;
        currentState = false;
        nextState = false;
    }

    public void setNeighbor(List<Cell> n){
        neighbor = n;
    }

    public void setNextState() {
        nextState = false;
        int count = 0;
        for(Cell c : neighbor){
            if(c.getCurrentState()) count++;
        }

        if(currentState){
            if(count == 2 || count == 3) nextState = true;
        }else{
            if(count == 3) nextState = true;
        }
    }

    public void updateToNext(){
        updateCell(nextState);
    }

    public void toggleState() {
        updateCell(!currentState);
    }

    public void reset() {
        nextState = false;
        updateCell(false);
    }

    public boolean getCurrentState() {
        return currentState;
    }

    private void updateCell(boolean state) {
        currentState = state;
        view.updateGridItem(row, col, currentState);
    }
}
