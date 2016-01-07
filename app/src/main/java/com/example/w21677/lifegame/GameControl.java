package com.example.w21677.lifegame;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

/**
 * Created by w21677 on 12/18/2015.
 */
public class GameControl {
    private static final String TAG = "GameControl";
    private static final int TICK_MS = 500;

    private Board board;
    private boolean running;
    private TimerThread mTimerThread;
    private Handler mHandler;

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

    public GameControl(Board b){
        board = b;
        running = false;

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.d(TAG, "handleMessage");
                board.update();
            }
        };
    }

    public void reset(){
        if(running) return;
        board.reset();
    }

    public void start(){
        running = true;
        mTimerThread = new TimerThread(mHandler);
        mTimerThread.start();
        Log.d(TAG, "Game started.");
    }

    public void pause(){
        running = false;
        Log.d(TAG, "Game paused.");
    }
}
