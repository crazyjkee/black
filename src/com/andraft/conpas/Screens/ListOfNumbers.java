package com.andraft.conpas.Screens;

import static com.andraft.conpas.Screens.Constants.Res;
import static com.andraft.conpas.Screens.Constants.WhiteRamca;
import static com.andraft.conpas.Screens.Constants.h;
import static com.andraft.conpas.Screens.Constants.w;

import java.util.Iterator;
import java.util.LinkedList;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.andraft.blacklist.Checking;
import com.andraft.blacklist.R;
import com.andraft.conpas.Screens.Constants.ico;
import com.andraft.models.SmsModel;

public class ListOfNumbers extends Screen {
	static LinkedList<SmallListPanel> allMessagesOrCalls;
	String[] Messages;/*
					 * { "3333333333333333 sdsd", "22222222 sdSDsggnsd",
					 * "11111111 dndfn", "55555555 dfgndfndfn",
					 * "7777777 adfgtetwt", "3333333 sdsdf", "88888888 sdefsdf",
					 * "888888888 sdsd", "777777777 sdSDsggnsd",
					 * "8888888 dndfn", "77777777 " + "55555555 dfgndfndfn",
					 * "5555555 adfgtetwt", "5555555 sdsdf", "4444444 sdefsdf"
					 * };
					 */

	private boolean twoIconsTrueOneIconFalse = false;
	private static final int HEIGHT = 60;
	protected float v = 0;
	private SmallListPanel NoData = null;
	protected MenuRect menu = null;
	protected RectF Centr = new RectF(0, h / 2 + BannerHeight() / 2 - HEIGHT,
			w, h / 2 + BannerHeight() / 2 + HEIGHT);
	private Checking checking;
	protected RectF blackButton = new RectF();
	protected RectF plus = new RectF(w
			- Constants.Res.getInteger(R.integer.smallIconWidth) * 2,
			Centr.centerY()
					- Constants.Res.getInteger(R.integer.smallIconWidth) / 2, w
					- Constants.Res.getInteger(R.integer.smallIconWidth) / 2,
			Centr.centerY()
					+ Constants.Res.getInteger(R.integer.smallIconWidth) / 2);

	protected boolean active = false;

	protected ico round = ico.roundPlus;
	protected boolean bmenu = true;

	public ListOfNumbers() {
		super(R.string.list_of_numbers);
		Log.d("myLogs", "ListOfNumbers Constructor");
		this.twoIconsTrueOneIconFalse = false;
		checking = Checking.getInstance(Constants.context);
		fillList();

		// checking.getDb().closeDB();

		menu = new MenuRect(this.twoIconsTrueOneIconFalse, Centr);

	}

	private void fillList() {
		allMessagesOrCalls = new LinkedList<SmallListPanel>();
		Messages = new String[checking.getSms(2).size()];
		int i = 0;
		for (SmsModel sms : checking.getSms(2)) {
			Log.d("myLogs", "num:" + sms.getNum() + " ,text:" + sms.getText());
			//Messages[i] = sms.getNum() + "/" + sms.getText();
			allMessagesOrCalls.add(new SmallListPanel(i, sms.getNum(),sms.getText()));
			i++;

		}
		toched = allMessagesOrCalls.get(allMessagesOrCalls.size() / 2);
		v = (Centr.centerY() - toched.centerY()) / 40;
	}

	public ListOfNumbers(int r, boolean bmenu) {
		super(r);
		this.bmenu = bmenu;
		if (bmenu) {
			this.twoIconsTrueOneIconFalse = true;
			menu = new MenuRect(this.twoIconsTrueOneIconFalse, Centr);
		} else
			round = ico.roundCrest;

	}

	protected void CheckISEmtyMessages() {
		// проверка, удаление или добавление R.string.no_data
		if (allMessagesOrCalls.isEmpty()) {
			NoData = new SmallListPanel(0, Res.getString(R.string.no_data),Res.getString(R.string.no_data));
		} else if (allMessagesOrCalls.size() > 1
				&& allMessagesOrCalls.contains(NoData))
			allMessagesOrCalls.remove(NoData);
	};

	protected RectF toched = null;
	private String center_nomer;

	@Override
	boolean onTouch(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			return true;
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (blackButton.contains(event.getX(), event.getY())) {
				for (SmsModel sms : checking.getSms(2)) {
					if (sms.getNum().equals(center_nomer)) {
						Log.d("myLogs",""+sms.getNum().equals(center_nomer));
						active = false;
						menu.setShow(active);
						round=ico.roundPlus;
						checking.getDb().updateSms(sms, 1);
						for (Iterator<SmallListPanel> iter = allMessagesOrCalls
								.iterator(); iter.hasNext();) {
							SmallListPanel data = iter.next();
							if (data.getNomer().equals(center_nomer)) {
								iter.remove();
							}
						}
						break;

					}

				}
				Log.d("myLogs", "blackButton:" + this.getCenter_nomer());
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

			if (!active) {
				Log.d("myLogs", "bmenuSAsas");
				for (RectF r : allMessagesOrCalls)
					if (r.contains(event.getX(), event.getY())) {
						toched = r;
						v = (Centr.centerY() - r.centerY()) / 40;
						return false;
					}
			}
		}
		return false;
	}

	void stop() {
		v = 0;
		if (toched != null) {
			toched.set(Centr);

			for (RectF r : allMessagesOrCalls) {
				int dy = allMessagesOrCalls.indexOf(toched)
						- allMessagesOrCalls.indexOf(r);
				if (allMessagesOrCalls.indexOf(r) > allMessagesOrCalls
						.indexOf(toched))
					r.set(0, toched.top + dy * HEIGHT, w, toched.top + dy
							* HEIGHT + HEIGHT);
				else if (allMessagesOrCalls.indexOf(r) < allMessagesOrCalls
						.indexOf(toched))
					r.set(0, toched.bottom - HEIGHT + dy * HEIGHT, w,
							toched.bottom + dy * HEIGHT);
			}
		}
	}

	@Override
	public void OnDraw(Canvas canvas) {
		{
			super.OnDraw(canvas);
			// canvas.drawRect(plus, WhiteText);
			if (toched == null || Centr.contains(toched))
				stop();
			for (SmallListPanel tr : allMessagesOrCalls) {
				tr.offset(0, v);
				if (Centr.contains(tr)) {
					center_nomer = tr.nomer;
					canvas.drawText(
							tr.nomer,
							tr.centerX(),
							tr.top
									+ tr.height()
									/ 4
									+ (Math.abs(WhiteText.ascent()
											+ Math.abs(WhiteText.descent()))),
							WhiteText);

					Constants.DrowIcon(
							canvas,
							round,
							canvas.getWidth()
									- Res.getInteger(R.integer.smallIconWidth),
							Centr.centerY(), true);

					canvas.drawText(
							tr.mess,
							tr.centerX(),
							tr.bottom
									- tr.height()
									/ 4
									- (Math.abs(WhiteText.ascent()
											+ Math.abs(WhiteText.descent()))),
							WhiteText);
					if (bmenu) {
						menu.move();
						blackButton
								.set(menu.left
										+ Constants.Res
												.getInteger(R.integer.largeIconWidth)
										/ 3,
										menu.centerY()
												- Constants.Res
														.getInteger(R.integer.largeIconWidth)
												/ 2,
										menu.left
												+ 4
												* Constants.Res
														.getInteger(R.integer.largeIconWidth)
												/ 3,
										menu.centerY()
												+ Constants.Res
														.getInteger(R.integer.largeIconWidth)
												/ 2);
						canvas.drawRect(menu, Constants.FONfill);
						Constants.DrowIcon(canvas, ico.blackList,
								blackButton.centerX(), blackButton.centerY(),
								false);
					}
					// canvas.drawRect(blackButton, WhiteText);

				} else {
					canvas.drawText(tr.nomer, tr.centerX(), tr.centerY(),
							WhiteTextSmall);
					if (!Centr.contains(10, tr.bottom))
						canvas.drawLine(0, tr.bottom, w, tr.bottom, WhiteRamca);
					if (!Centr.contains(10, tr.top))
						canvas.drawLine(0, tr.top, w, tr.top, WhiteRamca);

				}
			}

			canvas.drawRect(Centr, WhiteRamca);
		}
	}

	protected String getCenter_nomer() {
		return center_nomer;
	}

	class SmallListPanel extends RectF {
		private String nomer;
		private String mess;


		SmallListPanel(int pos, String nomer, String mess) {
			super.set(0, BannerHeight() + pos * HEIGHT, w, BannerHeight()
					+ (pos + 1) * HEIGHT);
			

			this.nomer = nomer;
			this.mess = mess.split(" ")[0];
		}

		public String getNomer() {
			return nomer;
		}

		protected void setNomer(String nomer) {
			this.nomer = nomer;
		}

		public String getMess() {
			return mess;
		}

		protected void setMess(String mess) {
			this.mess = mess;
		}

	}

	class MenuRect extends RectF {
		private int show = 0;
		private int maxRight;

		public MenuRect(boolean twoIconsTrueOneIconFalse, RectF centr) {
			super();
			if (twoIconsTrueOneIconFalse)
				maxRight = Constants.Res.getInteger(R.integer.largeIconWidth) * 3;
			else
				maxRight = 3 * Constants.Res
						.getInteger(R.integer.largeIconWidth) / 2;
			super.set(-maxRight, centr.top, 0, centr.bottom);

		}

		public int getShow() {
			return show;
		}

		public void setShow(boolean show) {
			if (show)
				this.show = 2;
			else
				this.show = -2;
		}

		boolean move() {
			// Log.d("myLogs","show:"+show+",maxRight:"+maxRight);

			if (this.right < 0 && show != 2)
				return false;
			if (this.right > maxRight && show != -2)
				return false;
			this.offset(show, 0);
			// Log.d("myLogs","this.right:"+this.right);
			return true;
		}

	}
}
