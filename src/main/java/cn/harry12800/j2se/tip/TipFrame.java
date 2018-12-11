package cn.harry12800.j2se.tip;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.List;

import cn.harry12800.j2se.component.BaseWindow;
import cn.harry12800.j2se.utils.PlaySounds;
import cn.harry12800.j2se.utils.Speak;
import cn.harry12800.tools.Lists;
import cn.harry12800.tools.StringUtils;

public class TipFrame extends BaseWindow {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	static int h;
	static int w;
	int len = 0;

	public static class Builder {
		public boolean hasHead = false;
		public String headTitle;
		public String actionName;
		public List<String> dataList = Lists.newArrayList();
		public ActionHandler handler;
	}

	public static Builder createBuilder() {
		Builder builder = new Builder();
		return builder;
	}

	private ActionHandler handler;
	public Builder builder;

	public TipFrame(Builder builder) {
		this.builder = builder;
		this.handler = builder.handler;
		for (String string : builder.dataList) {
			int _len = getFontMetrics(getFont()).stringWidth(string);
//			int _len = (getFontSize(string) + 1) / 2;
			if (_len > len) {
				len = _len;
			}
		}
		w = len * 14;
		if (w < 250)
			w = 250;
		setType(Frame.Type.UTILITY);
		TextPanel textPanel = new TextPanel(this);
		setContentPane(textPanel);
		h = builder.dataList.size() * 17 + 70;
		setSize(w, h);
		setAlwaysOnTop(true);
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
		//取得底部边界高度，即任务栏高度  
		setLocation(size.width - w - screenInsets.right - 2, size.height - h - screenInsets.bottom);
	}

	/**
	 * 获取builder
	 *	@return the builder
	 */
	public Builder getBuilder() {
		return builder;
	}

	/**
	 * 设置builder
	 * @param builder the builder to set
	 */
	public void setBuilder(Builder builder) {
		this.builder = builder;
	}

	public void showFrame() {
		setVisible(true);
		int width2 = getWidth();
		int height2 = getHeight();
		Point location = getLocation();
		int x = location.x;
		int y = location.y + height2;
		double times = height2 * 1.0 / 100;
		for (int i = 0; i <= 100; i++) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			setLocation(x, (int) (y - (times * i)));
			setSize(width2, (int) (times * i));
		}
		try {
			Thread.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setLocation(x, y - height2);
		setSize(width2, height2);
	}

	public static void show(String title, String ok, String content) {
		show(false, false, title, ok, content);

	}

	public static void show(Boolean sound, Boolean reader, String title, String ok, String content) {
		String[] split2 = content.split("\n");
		List<String> list = Lists.newArrayList();
		for (String string : split2) {
			List<String> split = StringUtils.split(string, 22);
			list.addAll(split);
		}
		Builder builder2 = new Builder();
		builder2.headTitle = title;
		builder2.dataList = list;
		builder2.actionName = ok;
		new TipFrame(builder2).showFrame();
		if (!sound)
			return;
		PlaySounds.play("/image/warn.wav");
		if (reader)
			try {
				Speak.speak1(title);
				list = StringUtils.split(content);
				for (String string : list) {
					Speak.speak1(string);
				}
			} catch (Exception e) {
				//e.printStackTrace();
			}
	}

	public static void main(String[] args) {
		show(true, true, "asd", "asdf", "asdfaasdffffffffffffffffffffffffffffff阿萨德发阿萨德啊沙发啊sdfa\r\nasdfff");
	}

	public static void show(Builder builder) {
		new TipFrame(builder).showFrame();
	}

	/**
	 * 获取handler
	 *	@return the handler
	 */
	public ActionHandler getHandler() {
		return handler;
	}

	/**
	 * 设置handler
	 * @param handler the handler to set
	 */
	public void setHandler(ActionHandler handler) {
		this.handler = handler;
	}

}
