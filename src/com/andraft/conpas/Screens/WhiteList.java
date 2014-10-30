package com.andraft.conpas.Screens;

import java.util.Iterator;
import java.util.LinkedList;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.andraft.blacklist.Checking;
import com.andraft.blacklist.R;
import com.andraft.models.NumberModel;

public class WhiteList extends BlackList{
private String[] calls;
private Checking checking;
	public WhiteList( ) {
		super(R.string.white_list, false);
		checking = Checking.getInstance(Constants.context);
		allMessagesOrCalls = new LinkedList<SmallListPanel>();
		calls = new String[checking.getCalls(0).size()];
		int i = 0;
		for(NumberModel num:checking.getCalls(0)){
			calls[i] = num.getNum()+" "+num.getName();
			allMessagesOrCalls.add(new SmallListPanel(i, calls[i]));
			i++;
		}
		 
	}

	@Override
	boolean onTouch(MotionEvent event) {
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			return true;
		}
		if(event.getAction()==MotionEvent.ACTION_UP){
			if(Centr.contains(event.getX(),event.getY())){
				v=0;
				if(plus.contains(event.getX(), event.getY())){
					for (Iterator<SmallListPanel> iter = allMessagesOrCalls
							.iterator(); iter.hasNext();) {
						SmallListPanel data =  iter.next();
						if (data.getNomer().equals(this.getCenter_nomer())) {

							for (NumberModel num : checking.getCalls(0))
								if (num.getNum().equals(getCenter_nomer()))
									checking.getDb().updateNumber(num, 2);
						
							iter.remove();
							break;
						}
					}}
				return false;
			}
		}
		return super.onTouch(event);
	}

	@Override
	public void OnDraw(Canvas canvas) {
		super.OnDraw(canvas);
		
	}

}
