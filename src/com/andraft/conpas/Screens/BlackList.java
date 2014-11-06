package com.andraft.conpas.Screens;

import java.util.Iterator;
import java.util.LinkedList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.andraft.blacklist.Checking;
import com.andraft.blacklist.R;
import com.andraft.conpas.Screens.Constants.ico;
import com.andraft.models.NumberModel;
import com.andraft.models.SmsModel;

public class BlackList extends ListOfNumbers {
	private String[] smski, calls;
	private Checking checking;
	private boolean stat = true;

	public BlackList() {
		super(R.string.black_list, false);
		stat = true;
		checking = Checking.getInstance(Constants.context);
		allMessagesOrCalls = new LinkedList<SmallListPanel>();
		smski = new String[checking.getSms(1).size()];
		calls = new String[checking.getCalls(1).size()];
		int i = 0;
		for (NumberModel num : checking.getCalls(1)) {
			calls[i] = num.getNum() + " " + num.getName();
			allMessagesOrCalls.add(new SmallList(i, num.getNum(),num.getName(), false, num
					.getCount_black()));
			i++;
		}
		i = 0;
		for (SmsModel smska : checking.getSms(1)) {
			smski[i] = smska.getNum() + " " + smska.getText();
			allMessagesOrCalls.add(new SmallList(i, smska.getNum(),smska.getText(), true, smska
					.getCount_black()));
			i++;
		}
	}
	

	public BlackList(int r,boolean stat) {
		super(r,false);
		this.stat = stat;
	}


	@Override
	boolean onTouch(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			return true;
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if(stat)
			if (Centr.contains(event.getX(), event.getY())) {
				v = 0;

				if (plus.contains(event.getX(), event.getY())) {
					for (Iterator<SmallListPanel> iter = allMessagesOrCalls
							.iterator(); iter.hasNext();) {
						SmallList data = (SmallList) iter.next();
						if (data.getNomer().equals(this.getCenter_nomer())) {

							if (data.isSms()) {
								for (SmsModel smska : checking.getSms(1)) {
									if (smska.getNum()
											.equals(getCenter_nomer()))
										checking.getDb().updateSms(smska, 2);
								}

							} else {
								for (NumberModel num : checking.getCalls(1))
									if (num.getNum().equals(getCenter_nomer()))
										checking.getDb().updateNumber(num, 2);
							}
							iter.remove();
							break;
						}

					}
					Log.d("myLogs", "delete:" + this.getCenter_nomer());

				}

				return false;

			}
			for (RectF r : allMessagesOrCalls)
				if (r.contains(event.getX(), event.getY())) {
					toched = r;
					v = (Centr.centerY() - r.centerY()) / 40;
					return false;
				}

			return false;

		}

		return false;
	}

	@Override
	public void OnDraw(Canvas canvas) {
		super.OnDraw(canvas);
		if(stat)
		for (SmallListPanel tr : allMessagesOrCalls) {
			if (Centr.contains(tr)) {
				if(((SmallList) tr).isSms()){
					Log.d("myLogs","isSms");
					Constants.DrowIcon(canvas, ico.konvert, Centr.left+5+Constants.Res.getInteger(R.integer.smallIconWidth)/2, Centr.bottom-5-Constants.Res.getInteger(R.integer.smallIconWidth)/2, true);
					canvas.drawText(((SmallList)tr).getCount_black()+"", Centr.left+5+3*Constants.Res.getInteger(R.integer.smallIconWidth)/2, Centr.bottom-5-Constants.Res.getInteger(R.integer.smallIconWidth)/2, WhiteTextSmall);
				}else{
					Log.d("myLogs","isn't SMS");
					Constants.DrowIcon(canvas, ico.truba, Centr.left+5+Constants.Res.getInteger(R.integer.smallIconWidth)/2, Centr.bottom-5-Constants.Res.getInteger(R.integer.smallIconWidth)/2, true);
					canvas.drawText(((SmallList)tr).getCount_black()+"", Centr.left+5+3*Constants.Res.getInteger(R.integer.smallIconWidth)/2, Centr.bottom-5-Constants.Res.getInteger(R.integer.smallIconWidth)/2, WhiteTextSmall);
				}
			}
		}
		
	}

	public class SmallList extends ListOfNumbers.SmallListPanel {
		private boolean sms = false;
		private int count_black;

		SmallList(int pos,String name,String text, boolean sms, int count_black) {
			super(pos, name, text);
			this.sms = sms;
			this.count_black = count_black;
		}

		public boolean isSms() {
			return sms;
		}

		protected void setSms(boolean sms) {
			this.sms = sms;
		}

		public int getCount_black() {
			return count_black;
		}

		protected void setCount_black(int count_black) {
			this.count_black = count_black;
		}

	}

}
