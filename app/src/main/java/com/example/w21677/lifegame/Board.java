package com.example.w21677.lifegame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by w21677 on 12/18/2015.
 */
public class Board {
    public int col;
    public int row;
    private Cell[][] cells;
    private MainView view;

    public Board(int r, int c, MainView v){
        view = v;
        initCells(r, c);
    }

    private void initCells(int r, int c) {
        row = r;
        col = c;
        cells = new Cell[row][col];

        for(int i=0; i < cells.length; i++){
            for(int j=0; j < cells[0].length; j++){
                cells[i][j] = new Cell(i, j, view);
            }
        }

        for(int i=0; i < cells.length; i++){
            for(int j=0; j < cells[0].length; j++){
                int[] dx = {-1, -1, -1,  0, 0,  1, 1, 1};
                int[] dy = {-1,  0,  1, -1, 1, -1, 0, 1};
                List<Cell> neighbor = new ArrayList<Cell>();
                for(int k=0; k < dx.length; k++){
                    int nx = i + dx[k], ny = j + dy[k];
                    if(nx >= 0 && nx < r && ny >= 0 && ny < c){
                        neighbor.add(cells[nx][ny]);
                    }
                }
                cells[i][j].setNeighbor(neighbor);
            }
        }
    }

    public void update(){
        for(int i=0; i < cells.length; i++){
            for(int j=0; j < cells[0].length; j++){
                cells[i][j].setNextState();
            }
        }

        for(int i=0; i < cells.length; i++){
            for(int j=0; j < cells[0].length; j++){
                cells[i][j].updateToNext();
            }
        }
    }

    public void reset() {
        for(int i=0; i < cells.length; i++){
            for(int j=0; j < cells[0].length; j++){
                cells[i][j].reset();
            }
        }
    }

    public Cell getCell(int x, int y){
        return cells[x][y];
    }
}
