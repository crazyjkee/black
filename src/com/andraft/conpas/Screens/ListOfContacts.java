package com.andraft.conpas.Screens;

import static com.andraft.conpas.Screens.Constants.w; 
import java.util.LinkedList;
import com.andraft.blacklist.R; 
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;
import static com.andraft.conpas.Screens.Constants.*;
public class ListOfContacts extends Screen {
	static LinkedList<SmallListPanel> allMessagesOrCalls = new LinkedList<SmallListPanel>();
	  String []Messages={"333333 sdsd","22222222 sdSDsggnsd","11111111 dndfn","55555555 dfgndfndfn","7777777 adfgtetwt",
			  "3333333 sdsdf","88888888 sdefsdf","888888888 sdsd","777777777 sdSDsggnsd",
			  "8888888 dndfn","77777777 "
			  		+ "55555555 dfgndfndfn","5555555 adfgtetwt","5555555 sdsdf","4444444 sdefsdf"};
 	private static final int HEIGHT=60;
	 private  float v=0;
	 private  SmallListPanel NoData=null;
	private RectF Centr=new RectF(0,h/2+BannerHeight()/2-HEIGHT,w,h/2+BannerHeight()/2+HEIGHT);
	public ListOfContacts() {
		super(R.string.list_of_contacts); 
		for (int i = 0; i < Messages.length; i++) { 
			allMessagesOrCalls.add(new SmallListPanel(i,Messages[i]));  
		
	}
		toched=allMessagesOrCalls.get(  allMessagesOrCalls.size()/2) ;
		v=(Centr.centerY()-toched.centerY())/40;
	}
	public ListOfContacts(int r,String []Messages) {
		super(r); 
		this.Messages=Messages;
		for (int i = 0; i < Messages.length; i++) { 
			allMessagesOrCalls.add(new SmallListPanel(i,Messages[i]));  
		
	}   CheckISEmtyMessages();
		toched=allMessagesOrCalls.get(  allMessagesOrCalls.size()/2) ;
		v=(Centr.centerY()-toched.centerY())/40;
	}
private void CheckISEmtyMessages(){ 
	//проверка, удаление или добавление R.string.no_data
	if (allMessagesOrCalls.isEmpty()){NoData=new SmallListPanel(0,Res.getString(R.string.no_data));} 
 	else if(allMessagesOrCalls.size()>1&&allMessagesOrCalls.contains(NoData))allMessagesOrCalls.remove(NoData);
};	
	
private RectF toched=null;
 
	@Override
	 boolean  onTouch(MotionEvent event) { 
		if(event.getAction()==MotionEvent.ACTION_DOWN){ return true;}
		if(event.getAction()==MotionEvent.ACTION_UP){ 
		if(Centr.contains(event.getX(), event.getY())){
			v =0;return false;}
		for(RectF r:allMessagesOrCalls) 
			if(r.contains(event.getX(), event.getY())) {
				toched=r;
				v=(Centr.centerY()-r.centerY())/40;
				return false;
				} 
		}
		return false;
	}
	void stop(){v = 0;
		if(toched!=null){
			toched.set(Centr);
		
		for (RectF r:allMessagesOrCalls) {
			   int dy =   allMessagesOrCalls.indexOf(toched)-allMessagesOrCalls.indexOf(r)  ;
			if(allMessagesOrCalls.indexOf(r)>allMessagesOrCalls.indexOf(toched))r.set(0,toched.top+ dy*HEIGHT, w,toched.top+ dy*HEIGHT+HEIGHT);
			else if (allMessagesOrCalls.indexOf(r)<allMessagesOrCalls.indexOf(toched))r.set(0,toched.bottom-HEIGHT+dy*HEIGHT, w,toched.bottom+ dy*HEIGHT);
		} 
			 }
	}
	@Override
		public   void OnDraw(Canvas canvas) {
		 {super.OnDraw(canvas);
		 if(toched==null||Centr.contains(toched))stop();
		for(SmallListPanel tr:allMessagesOrCalls){ 
	        tr.offset(0, v); 
		if(Centr.contains(tr)) {
			canvas.drawText(tr.nomer ,tr.centerX(), tr.centerY() ,WhiteText );
			 
		} 
		 	else  {
			canvas.drawText(tr.nomer ,tr.centerX(), tr.centerY(),WhiteTextSmall);
			if(!Centr.contains(10, tr.bottom))canvas.drawLine(0,tr.bottom, w, tr.bottom,WhiteRamca);
			if(!Centr.contains(10, tr.top))canvas.drawLine(0,tr.top, w,tr.top,WhiteRamca);
			 
		 }}
		 
		 canvas.drawRect(Centr ,WhiteRamca); 
	}}
  class SmallListPanel extends RectF{ 
	private String  nomer ; 
	private String mess;
	SmallListPanel(int pos,String text){
		super.set(0,BannerHeight()+pos*HEIGHT,w,BannerHeight()+(pos+1)*HEIGHT);
		this.nomer=text.split(" ")[0] ; 
		this.mess=text.split(" ")[1] ;
		} 
	 
  }}
