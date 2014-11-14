package com.andraft.conpas.Screens;

import static com.andraft.conpas.Screens.Constants.w;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.andraft.blacklist.Checking;
import com.andraft.blacklist.R;
import com.andraft.conpas.Screens.Constants.ico;
import com.andraft.models.SmsOptionsModel;

public class SetupSms extends Screen implements Screen.Back {
	private static final int HEIGHT = 60;
	protected List<SmallListPanel> rects;
	private String[] text = new String[6];
	private Checking checking;
	private SmsOptionsModel smsModel;

	public SetupSms() {
		super(R.string.setup_sms, Constants.FONwhitefill);
		init();
	}

	public SetupSms(int r) {
		super(r, Constants.FONwhitefill);
	}

	private void init() {
		this.setBackReadyCb(this);
		checking = Checking.getInstance(Constants.context);
		smsModel = new SmsOptionsModel(1, Constants.BLOCK_ALL_SMS ? 1 : 0,
				Constants.BLOCK_HIDDEN_SMS ? 1 : 0,
				Constants.BLOCK_NOTIFICATIONS_SMS ? 1 : 0);

		rects = new ArrayList<SmallListPanel>();

		text[0] = Constants.Res.getString(R.string.block_all_sms);
		text[1] = Constants.Res.getString(R.string.block_hidden_numbers);
		text[2] = Constants.Res.getString(R.string.notice);
		text[3] = Constants.Res.getString(R.string.spam_filter);
		text[4] = Constants.Res.getString(R.string.version);
		text[5] = Constants.Res.getString(R.string.coming);
		rects.add(new SmallListPanel(0, Constants.BLOCK_ALL_SMS, text[0], null,
				true, ico.roundOk, ico.roundCrest));
		rects.add(new SmallListPanel(1, Constants.BLOCK_HIDDEN_SMS, text[1],
				null, true, ico.roundOk, ico.roundCrest));
		if (android.os.Build.VERSION.SDK_INT < 12) {
			rects.add(new SmallListPanel(2, Constants.BLOCK_NOTIFICATIONS_SMS,
					text[2], text[4], false, null, null));
		} else
			rects.add(new SmallListPanel(2, Constants.BLOCK_NOTIFICATIONS_SMS,
					text[2], text[4], true, ico.roundOk, ico.roundCrest));
		rects.add(new SmallListPanel(3, false, text[3], text[5], false,
				ico.roundOk, ico.roundCrest));

	}

	@Override
	public void OnDraw(Canvas canvas) {
		super.OnDraw(canvas);

		for (SmallListPanel rect : rects) {
			canvas.drawLine(0, rect.top, w, rect.top, Constants.WhiteRamca);
			canvas.drawRect(rect, Constants.FONfill);
			canvas.drawLine(0, rect.bottom, w, rect.bottom,
					Constants.WhiteRamca);
			
			if (rect.getSecond() != null) {
				canvas.drawText(rect.getMain(), rect.centerX(), rect.centerY(),
						this.WhiteText);
				canvas.drawText(
						rect.getSecond(),
						rect.centerX(),
						rect.bottom

								- (Math.abs(WhiteTextSmall.ascent()
										+ Math.abs(WhiteTextSmall.descent()))),
						this.WhiteTextSmall);
			}else{
				canvas.drawText(rect.getMain(), rect.centerX(), rect.centerY()+(WhiteText.descent()-WhiteText.ascent())/4,//(Math.abs(WhiteText.descent())-Math.abs(WhiteText.ascent()))/2,
						this.WhiteText);
			}
			rect.draw(canvas);

		}

	}

	@Override
	boolean onTouch(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			return true;
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			for (SmallListPanel rect : rects) {
				if (rect.contains(event.getX(), event.getY())) {
					if (rect.isBlock()) {
						rect.setBlock(false);
						if (rect.getMain().equals(text[0])) {
							smsModel.setBlock_all_sms(0);
							Log.d("myLogs",text[0]+ " false");
						}
						if (rect.getMain().equals(text[1])) {
							smsModel.setBlock_hidden_numbers_sms(0);
							Log.d("myLogs",text[1] + " false");
						}
						if (rect.getMain().equals(text[2])
								&& android.os.Build.VERSION.SDK_INT > 12)
							smsModel.setBlock_notifications_sms(0);
					} else {
						rect.setBlock(true);
						if (rect.getMain().equals(text[0])) {
							smsModel.setBlock_all_sms(1);
							Log.d("myLogs",text[0]+ " true");
						}
						if (rect.getMain().equals(text[1])) {
							smsModel.setBlock_hidden_numbers_sms(1);
							Log.d("myLogs",text[1]+ " true");
						}
						if (rect.getMain().equals(text[2])
								&& android.os.Build.VERSION.SDK_INT > 12)
							smsModel.setBlock_notifications_sms(1);
					}
				}
			}

			return false;
		}

		return false;
	}

	@Override
	public void onBack() {
		checking.getDb().updateSmsOptions(smsModel);
		Constants.initSmsOptions();
		Log.d("myLogs", "back");
		Log.d("myLogs",
				"smsModel.blockallcalls" + smsModel.isBlock_all_sms()
						+ ",smsmodel.blockhiddennumbers:"
						+ smsModel.isBlock_hidden_numbers_sms());
	}

	class SmallListPanel extends RectF {
		private boolean block, drawCheck;
		private String main;
		private String second;
		private ico icon, icoff;

		public SmallListPanel() {
			super();
		}

		public SmallListPanel(int pos, boolean block, String main,
				String second, boolean drawCheck, ico icon, ico icoff) {
			super.set(0, BannerHeight() + pos * HEIGHT, w, BannerHeight()
					+ (pos + 1) * HEIGHT);
			this.block = block;
			this.main = main;
			this.second = second;
			this.drawCheck = drawCheck;
			this.icoff = icoff;
			this.icon = icon;
		}

		public void draw(Canvas canvas) {
			if (isBlock() && drawCheck)
				Constants.DrowIcon(canvas, icon, this.width() - 3
						* Constants.Res.getInteger(R.integer.smallIconWidth)
						/ 2, this.centerY(), true);
			else if (drawCheck)
				Constants.DrowIcon(canvas, icoff, this.width() - 3
						* Constants.Res.getInteger(R.integer.smallIconWidth)
						/ 2, this.centerY(), true);
		}

		public boolean isBlock() {
			return block;
		}

		public void setBlock(boolean block) {
			this.block = block;
		}

		public String getMain() {
			return main;
		}

		protected void setMain(String main) {
			this.main = main;
		}

		public String getSecond() {
			return second;
		}

		protected void setSecond(String second) {
			this.second = second;
		}

	}

}
