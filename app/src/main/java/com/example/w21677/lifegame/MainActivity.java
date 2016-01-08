package com.example.w21677.lifegame;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridLayout;

public class MainActivity extends AppCompatActivity implements MainView {
    private static final String TAG = "MainActivity";

    MainPresenter mGC;
    static final int ROW_NUM = 20;
    static final int COLUMN_NUM = 20;
    GridItem[][] gridItems;
    FloatingActionButton fab;

    private DisplayMetrics getDisplayMetrics() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Log.d(TAG, "density = " + metrics.density);
        Log.d(TAG, "densityDpi = " + metrics.densityDpi);
        Log.d(TAG, "widthPixels = " + metrics.widthPixels);
        Log.d(TAG, "heightPixels = " + metrics.heightPixels);
        Log.d(TAG, "scaledDensity = " + metrics.scaledDensity);
        Log.d(TAG, "xdpi = " + metrics.xdpi);
        Log.d(TAG, "ydpi = " + metrics.ydpi);
        return metrics;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGC = new GameControl(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.start_pause);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGC.toggleGame();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset) {
            mGC.resetGame();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void paused() {
        fab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_media_play, getTheme()));
    }

    @Override
    public void started() {
        fab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_media_pause, getTheme()));
    }

    @Override
    public void updateGridItem(int row, int col, boolean state) {
        gridItems[row][col].setColor(state);
    }

    @Override
    public void initGrid(Board board) {
        GridLayout gl = (GridLayout) findViewById(R.id.grid);
        gl.setRowCount(board.row);
        gl.setColumnCount(board.col);

        gl.setOrientation(GridLayout.HORIZONTAL);
        gl.setBackgroundColor(Color.GRAY);

        DisplayMetrics metrics = getDisplayMetrics();

        gridItems = new GridItem[board.row][board.col];
        for(int i=0; i < gridItems.length; i++){
            for(int j=0; j < gridItems[0].length; j++){
                gridItems[i][j] = new GridItem(this, board.getCell(i,j));
                gridItems[i][j].setBackgroundColor(Color.WHITE);
                gridItems[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GridItem gridItem = (GridItem) v;
                        gridItem.cell.toggleState();
                    }
                });

                GridLayout.LayoutParams param = new GridLayout.LayoutParams(
                        GridLayout.spec(i, GridLayout.CENTER),
                        GridLayout.spec(j, GridLayout.CENTER)
                );
                param.setGravity(Gravity.FILL);
                param.width = (int)(18 * metrics.density);
                param.height = (int)(18 * metrics.density);
                param.setMargins(2,2,1,1);
                gl.addView(gridItems[i][j],param);
            }
        }
    }

    private class GridItem extends Button {
        Cell cell;

        public GridItem(Context context, Cell c){
            super(context);
            cell = c;
        }

        public void setColor(boolean state) {
            setBackgroundColor(state ? Color.BLACK : Color.WHITE);
        }
    }
}
