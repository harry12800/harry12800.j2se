package cn.harry12800.j2se.tip;

import java.awt.Color;

import javax.swing.BorderFactory;

public class CurrentPlayItem implements Runnable {
	private ItemPanel itemPanel;
	int position = 0;
	int x = 0;
	int r = 0, g = 0, b = 0;
	int time = 0;
	int direct = 1;

	public CurrentPlayItem() {
		while (x < 250) {
			r = (int) (Math.random() * 255);
			g = (int) (Math.random() * 255);
			b = (int) (Math.random() * 255);
			x = r * r + g * g + b * b;
		}
	}

	public synchronized void setItemPanel(ItemPanel itemPanel) {
		position = 0;
		direct = 1;
		stopItemPanel();
		this.itemPanel = itemPanel;
	}

	private void stopItemPanel() {
		if (itemPanel != null)
			itemPanel.getTitleLabel().setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(2);
				time++;
				changeBackground();
				if (time == 50) {
					time = 0;
					changeLabelLocation();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void changeLabelLocation() {
		position += direct;
		position = position % 50;
		if (position == 49) {
			direct = -1;
		}
		if (position == -1) {
			position = 0;
			direct = 1;
		}
		itemPanel.getTitleLabel().setBorder(BorderFactory.createEmptyBorder(5, position + 5, 5, 0));
	}

	private void changeBackground() {
		double t = Math.random() * 2;
		double v = Math.random() * 3;
		if (v > 2) {
			if (t > 1 && v > 2) {
				r = (r + 1 + 256) % 256;
				if (r == 0)
					r = 254;
			} else {
				r = (r - 1 + 256) % 256;
				if (r == 255)
					r = 1;
			}
		} else if (v > 1) {
			if (t > 1) {
				g = (g + 1 + 256) % 256;
				;
				if (g == 0)
					g = 254;
			} else {
				g = (g - 1 + 256) % 256;
				if (g == 255)
					g = 1;
			}
		} else {
			if (t > 1) {
				b = (b + 257) % 256;
				if (b == 0)
					b = 254;
			} else {
				b = (b - 1 + 256) % 256;
				if (b == 255)
					b = 1;
			}
		}
		itemPanel.setBackground(new Color(r, g, b));
	}
}
