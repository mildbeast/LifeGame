package com.example.w21677.lifegame;

import java.util.List;

/**
 * Created by w21677 on 12/18/2015.
 */
public class Rule {
    public boolean nextState(boolean state, List<Cell> neighbor) {
        int count = 0;
        for(Cell c : neighbor){
            if(c.getCurrentState()) count++;
        }

        if(state){
            if(count == 2 || count == 3) return true;
        }else{
            if(count == 3) return true;
        }
        return false;
    }
}
