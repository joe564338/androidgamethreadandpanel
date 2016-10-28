package com.packages.joe.gamethreadlib;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Joe on 10/27/2016.
 */
public class GameThread extends Thread implements Runnable{
    private int FPS = 30;
    private double averageFps;
    private SurfaceHolder surfaceHolder;
    /*GamePanel that will be updated as thread runs*/
    private GamePanel gamePanel;
    /*Determines if the game is running*/
    private boolean running;
    /*Canvas to be locked while being used by thread*/
    public static Canvas canvas;

    public GameThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }
    /*Sets the running state to on or off*/
    public void setRunning(boolean b){
        running = b;
    }
    /*Runs game at 30fps*/
    @Override
    public void run(){
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000/FPS;

        while(running){
            startTime = System.nanoTime();
            canvas = null;

            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch (Exception e){}
            finally {
                if(canvas != null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime - timeMillis;

            try{
                this.sleep(waitTime);
            } catch (Exception e){}
            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if(frameCount == FPS){
                averageFps = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFps);
            }
        }
    }
}
