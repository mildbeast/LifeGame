package com.example.w21677.lifegame;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

/**
 * Created by w21677 on 12/18/2015.
 */
public class GameControl implements MainPresenter {
    private static final String TAG = "GameControl";
    private static final int TICK_MS = 500;
    private static final int ROW_NUM = 20;
    private static final int COLUMN_NUM = 20;

    private Board board;
    private boolean running;
    private TimerThread mTimerThread;
    private Handler mHandler;
    private MainView view;

    class TimerThread extends Thread{
        Handler mHandler;
        public TimerThread(Handler h){
            mHandler = h;
        }
        @Override
        public void run(){
            Log.d(TAG, "TimerThread: start");
            while(running){
                try {
                    mHandler.sendEmptyMessage(0);
                    Log.d(TAG, "TimerThread: sendEmptyMessage");
                    Thread.sleep(TICK_MS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.d(TAG, "TimerThread: end");
        }
    }

    public GameControl(MainView v){
        view = v;
        board = new Board(ROW_NUM, COLUMN_NUM, view);
        running = false;
        v.initGrid(board);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.d(TAG, "handleMessage");
                board.update();
            }
        };
    }

    public void resetGame(){
        if(running) return;
        board.reset();
    }

    @Override
    public void toggleGame() {
        running = !running;
        if(running){
            mTimerThread = new TimerThread(mHandler);
            mTimerThread.start();
            Log.d(TAG, "Game started.");
            view.started();
        }else{
            Log.d(TAG, "Game paused.");
            view.paused();
        }
    }
}
