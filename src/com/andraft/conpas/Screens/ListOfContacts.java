package com.andraft.conpas.Screens;

import java.util.Iterator;
import java.util.LinkedList;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.andraft.blacklist.Checking;
import com.andraft.blacklist.R;
import com.andraft.conpas.Screens.Constants.ico;
import com.andraft.conpas.Screens.ListOfNumbers.SmallListPanel;
import com.andraft.models.NumberModel;
import com.andraft.models.SmsModel;

public class ListOfContacts extends ListOfNumbers {
	private static String Messages[];
	private Checking checking;

	private RectF whiteButton = new RectF();

	public ListOfContacts() {
		super(R.string.list_of_contacts, true);
		checking = Checking.getInstance(Constants.context);
		allMessagesOrCalls = new LinkedList<SmallListPanel>();
		int i = 0;
		Messages = new String[checking.getCalls(2).size()];
		for (NumberModel number : checking.getCalls(2)) {
			// Log.d("myLogs","num:"+number.getNum()+" ,text:"+number.getText());
			allMessagesOrCalls.add(new SmallListPanel(i, number.getNum(),number.getName()));
			i++;
		}
		checking.getDb().closeDB();
		toched = allMessagesOrCalls.get(allMessagesOrCalls.size() / 2);
		v = (Centr.centerY() - toched.centerY()) / 40;

	}

	@Override
	public void OnDraw(Canvas canvas) {
		super.OnDraw(canvas);
		whiteButton.set(
				menu.right - 4
						* Constants.Res.getInteger(R.integer.largeIconWidth)
						/ 3,
				menu.centerY()
						- Constants.Res.getInteger(R.integer.largeIconWidth)
						/ 2,
				menu.right - Constants.Res.getInteger(R.integer.largeIconWidth)
						/ 3,
				menu.centerY()
						+ Constants.Res.getInteger(R.integer.largeIconWidth)
						/ 2);
		Constants.DrowIcon(canvas, ico.whiteList, whiteButton.centerX(),
				whiteButton.centerY(), false);

	}

	@Override
	boolean onTouch(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			return true;
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (whiteButton.contains(event.getX(), event.getY())) {
				for (NumberModel num : checking.getCalls(2)) {
					if (num.getNum().equals(this.getCenter_nomer())) {
						active = false;
						menu.setShow(active);
						round = ico.roundPlus;
						checking.getDb().updateNumber(num, 0);
						for (Iterator<SmallListPanel> iter = allMessagesOrCalls
								.iterator(); iter.hasNext();) {
							SmallListPanel data = iter.next();
							if (data.getNomer().equals(this.getCenter_nomer())) {
								iter.remove();
							}
						}
						break;

					}

				}
				Log.d("myLogs", "whiteButton");
			}
			if (blackButton.contains(event.getX(), event.getY())) {
				for (NumberModel num : checking.getCalls(2)) {
					if (num.getNum().equals(this.getCenter_nomer())) {
						active = false;
						menu.setShow(active);
						round = ico.roundPlus;
						checking.getDb().updateNumber(num, 1);
						for (Iterator<SmallListPanel> iter = allMessagesOrCalls
								.iterator(); iter.hasNext();) {
							SmallListPanel data = iter.next();
							if (data.getNomer().equals(this.getCenter_nomer())) {
								iter.remove();
							}
						}
						break;

					}

				}
				Log.d("myLogs", "blackButton");
			}
			

			if (Centr.contains(event.getX(), event.getY())) {
				v = 0;

				if (plus.contains(event.getX(), event.getY()) && !active) {
					active = true;
					round = ico.roundMinus;

					menu.setShow(active);
					Log.d("myLogs", "plus contains");

				} else if (plus.contains(event.getX(), event.getY()) && active) {
					active = false;
					menu.setShow(active);
					round = ico.roundPlus;
				}

				return false;

			}
			if (!active)
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
}
