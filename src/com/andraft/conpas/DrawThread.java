package com.andraft.conpas;

import com.andraft.blacklist.MainActivity;

import android.content.Context;
import android.graphics.Canvas; 
import android.view.SurfaceHolder;
 

public class DrawThread extends Thread{
	private      boolean runFlag = true;
	public static final long FPS=25; 
    private static SurfaceHolder surfaceHolder; 
    public static boolean isDraw(){
		return draw;
	}
	public static void setDraw(boolean draw){
		DrawThread.draw=draw;
	}
	public static boolean draw=true;
     public static DrawThread Risovalca=null; 
public static void Init(SurfaceHolder surfaceHolder, Context context){
	DrawThread.surfaceHolder=surfaceHolder;  
	com.andraft.conpas.Screens.Constants.init( context.getResources(),surfaceHolder.getSurfaceFrame());
	  Risovalca=new DrawThread( ) ;  
	  Risovalca.start();
}  
    @Override
    public void run() { 
        Canvas canvas=null; 
       long time = System.currentTimeMillis();
        while (runFlag) { 
        	 time=System.currentTimeMillis()-time;
        	 if(time<FPS)
        		try {
        			Thread.sleep(FPS-time); 
        	} catch (InterruptedException e) {}
            canvas = null;
            if(draw)
            try { 
                canvas = surfaceHolder.lockCanvas(null); 
                synchronized (surfaceHolder) { 
                	 try {
                		 MainActivity.getActiveScreen().OnDraw(canvas); 
					} catch (Exception e) { 
					} 
                }
            } 
            finally {
                if (canvas != null)  
                    surfaceHolder.unlockCanvasAndPost(canvas);
                };
        } 
    }
	public static void Stop (){
		if(Risovalca!=null){
		boolean retry = true; 
		Risovalca.runFlag=false;
        while (retry)  
            try {
            	Risovalca.join();
                retry = false;
            } catch (InterruptedException e) { 
        }Risovalca=null;
        } 
	}
	 
}