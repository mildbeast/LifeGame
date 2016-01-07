package com.example.w21677.lifegame;

import java.util.List;

/**
 * Created by w21677 on 12/18/2015.
 */
public class Cell {
    private boolean currentState;
    private boolean nextState;
    private List<Cell> neighbor;
    private Rule rule;
    private CellUpdater cellUpdater;
    private int row, col;

    public Cell(int i, int j, Rule r, CellUpdater cu){
        row = i;
        col = j;
        rule = r;
        cellUpdater = cu;
        currentState = false;
        nextState = false;
    }

    public void setNeighbor(List<Cell> n){
        neighbor = n;
    }

    public void setNextState() {
        nextState = rule.nextState(currentState, neighbor);
    }

    public void updateToNext(){
        currentState = nextState;
        cellUpdater.update(this);
    }

    public void toggleState() {
        currentState = !currentState;
        cellUpdater.update(this);
    }

    public void reset() {
        currentState = false;
        nextState = false;
        cellUpdater.update(this);
    }

    public boolean getCurrentState() {
        return currentState;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
