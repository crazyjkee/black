package com.andraft.conpas.Screens;

import java.util.Iterator;
import java.util.LinkedList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.andraft.blacklist.Checking;
import com.andraft.blacklist.R;
import com.andraft.conpas.Screens.Constants.ico;
import com.andraft.models.NumberModel;

public class ListOfContacts extends ListOfNumbers {
	private static String Messages[];
	private Checking checking;

	private RectF whiteButton = new RectF();

	public ListOfContacts() {
		super(R.string.list_of_contacts, true);
		checking = Checking.getInstance(Constants.context);
		checking.init();
		allMessagesOrCalls = new LinkedList<SmallListPanel>();
		int i = 0;
		Messages = new String[checking.getCalls(2).size()];
		for (NumberModel number : checking.getCalls(2)) {
			// Log.d("myLogs","num:"+number.getNum()+" ,text:"+number.getText());
			allMessagesOrCalls.add(new SmallListPanel(i, number.getNum(),number.getName()));
			i++;
		}
		CheckISEmtyMessages();
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
					if (num.getNum().equals(this.getCenter_nomer())&&!allMessagesOrCalls.contains(NoData)) {
						active = false;
						menu.setShow(active);
						round = ico.roundPlus;
						checking.getDb().updateNumber(num, 0);
						for (Iterator<SmallListPanel> iter = allMessagesOrCalls
								.iterator(); iter.hasNext();) {
							SmallListPanel data = iter.next();
							if (data.getNomer().equals(this.getCenter_nomer())) {
								this.removeSmallListPanel(data);
								iter.remove();
							}
						}
						break;

					}

				}
				
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
								this.removeSmallListPanel(data);
								iter.remove();
							}
						}
						break;

					}

				}
				
			}
			

			if (Centr.contains(event.getX(), event.getY())) {
                if(messageRect.contains(event.getX(),event.getY())&&!active){
                	
					AlertDialog.Builder builder = new AlertDialog.Builder(Constants.context);
					builder
							.setMessage(this.getFull_message())
							.setCancelable(false)
							.setNegativeButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int id) {
											dialog.cancel();
										}
									});
					AlertDialog alert = builder.create();
					alert.show();

                }

				if (plus.contains(event.getX(), event.getY()) && !active) {
					active = true;
					round = ico.roundMinus;

					menu.setShow(active);
				

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
