package com.andraft.conpas.Screens;

import static com.andraft.conpas.Screens.Constants.w;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.andraft.blacklist.Checking;
import com.andraft.blacklist.R;
import com.andraft.conpas.Screens.Constants.ico;
import com.andraft.models.NumberOptionsModel;

public class SetupCalls extends SetupSms implements Screen.Back {
	private static final int HEIGHT = 60;
	private String[] text = new String[6];
	private List<MenuList> rectshow;
	private String[] textShow = new String[2];
	private boolean show = false;
	private NumberOptionsModel numModel;
	private Checking checking;

	public SetupCalls() {
		super(R.string.setup_calls);
		init();
	}

	private void init() {
		this.setBackReadyCb(this);
		this.rects = new ArrayList<SmallListPanel>();
		this.rectshow = new ArrayList<MenuList>();
		this.numModel = new NumberOptionsModel(1, Constants.BLOCK_ALL_CALLS ? 1
				: 0, Constants.BLOCK_HIDDEN_CALLS ? 1 : 0,
				Constants.BLOCK_NOTIFICATION_CALLS ? 1 : 0,
				Constants.SILENT_MODE ? 1 : 0, Constants.BUSY_MODE ? 1 : 0);
		this.checking = Checking.getInstance(Constants.context);

		text[0] = Constants.Res.getString(R.string.block_all_calls);
		text[1] = Constants.Res.getString(R.string.block_hidden_numbers);
		text[2] = Constants.Res.getString(R.string.notice);
		text[3] = Constants.Res.getString(R.string.call_forwarding);
		text[4] = Constants.Res.getString(R.string.notice);
		text[5] = Constants.Res.getString(R.string.version);
		textShow[0] = Constants.Res.getString(R.string.silent);
		textShow[1] = Constants.Res.getString(R.string.busy);
		rects.add(new SmallListPanel(0, Constants.BLOCK_ALL_CALLS, text[0],
				null, true, ico.roundOk, ico.roundCrest));
		rects.add(new SmallListPanel(1, Constants.BLOCK_HIDDEN_CALLS, text[1],
				null, true, ico.roundOk, ico.roundCrest));
		if (android.os.Build.VERSION.SDK_INT < 12) {
			rects.add(new SmallListPanel(2, Constants.BLOCK_NOTIFICATION_CALLS,
					text[2], text[5], false, null, null));
		} else
			rects.add(new SmallListPanel(2, Constants.BLOCK_NOTIFICATION_CALLS,
					text[2], text[5], true, ico.roundOk, ico.roundCrest));

		rects.add(new SmallListPanel(3, false, text[3], null, true,
				ico.roundUp, ico.roundDown));
		rectshow.add(new MenuList(4, Constants.SILENT_MODE, textShow[0], null,
				true, ico.roundOk, ico.roundCrest));
		rectshow.add(new MenuList(5, Constants.BUSY_MODE, textShow[1], null,
				true, ico.roundOk, ico.roundCrest));
	}

	@Override
	public void OnDraw(Canvas canvas) {
		super.OnDraw(canvas);
		for (MenuList menu : rectshow) {
			if (show) {
				canvas.drawRect(menu, Constants.FONfill);
				menu.draw(canvas);
				canvas.drawText(menu.getMain(), menu.centerX(), menu.centerY()
						+ (WhiteText.descent() - WhiteText.ascent()) / 4,
						this.WhiteText);
				canvas.drawLine(0, menu.bottom, w, menu.bottom,
						Constants.WhiteRamca);
			}
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
						if (rect.getMain().equals(text[0]))
							numModel.setBlock_all_calls(0);
						if (rect.getMain().equals(text[1]))
							numModel.setBlock_hidden_numbers_calls(0);
						if (rect.getMain().equals(text[2])
								&& android.os.Build.VERSION.SDK_INT > 12)
							numModel.setBlock_notifications_calls(0);
					} else {
						rect.setBlock(true);
						if (rect.getMain().equals(text[0]))
							numModel.setBlock_all_calls(1);
						if (rect.getMain().equals(text[1]))
							numModel.setBlock_hidden_numbers_calls(1);
						if (rect.getMain().equals(text[2])
								&& android.os.Build.VERSION.SDK_INT > 12)
							numModel.setBlock_notifications_calls(1);

					}
					if (rect.getMain().equals(text[3])) {
						if (rect.isBlock()) {
							show = true;

						} else {
							show = false;

						}
					}
				}
			}
			if (show)
				for (int i = 0; i < rectshow.size(); i++) {
					if (rectshow.get(0).contains(event.getX(), event.getY())) {
						rectshow.get(0).setBlock(true);
						rectshow.get(1).setBlock(false);
						numModel.setSilent_mode(1);
						numModel.setBusy_mode(0);

					} else if (rectshow.get(1).contains(event.getX(),
							event.getY())) {
						rectshow.get(0).setBlock(false);
						rectshow.get(1).setBlock(true);
						numModel.setSilent_mode(0);
						numModel.setBusy_mode(1);

					}

				}
			return false;
		}
		return false;
	}

	@Override
	public void onBack() {
		checking.getDb().updateNumbersOptions(numModel);
		Constants.initNumberOptions();

	}

	class MenuList extends SmallListPanel {

		public MenuList(int pos, boolean block, String main, String second,
				boolean drawCheck, ico icon, ico icoff) {
			super(pos, block, main, second, drawCheck, icon, icoff);

		}

	}

}
