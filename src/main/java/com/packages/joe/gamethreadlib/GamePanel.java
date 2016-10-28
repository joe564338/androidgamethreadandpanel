package com.packages.joe.gamethreadlib;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Joe on 10/27/2016.
 * To use, implement the update and draw methods however you wish to then to construct just do
 * something like GamePanel gamePanel = new GamePanel(this) inside an activity
 */
public abstract class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    /*Context for the GamePanel, basically whatever activity it will be running in*/
    protected Context context;
    /*Thread that will update and draw the GamePanel*/
    protected GameThread thread;
    /*When using inside of an activity simply pass the context of the activity in with the "this"
     keyword*/
    public GamePanel(Context context){
        super(context);
        getHolder().addCallback(this);
        this.context = context;
        thread = new GameThread(getHolder(),this);
        setFocusable(true);
    }
    /*This is called when screen orientation is changed. Try to avoid this because it probably will
     mess with the positioning of objects in your game*/
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }
    /*When you leave the GamePanel screen this method is called*/
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;

        while(retry){
            try{

                thread.setRunning(false);
                thread.join();
                retry = false;
            }catch(InterruptedException e){
                e.printStackTrace();
            }

        }
    }
    /*Upon initializing the GamePanel this method is called*/
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread.setRunning(true);
        thread.start();

    }
    /*Update whatever variables you need to*/
    public abstract void update();
    /*Draw your components*/
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
    }
}
