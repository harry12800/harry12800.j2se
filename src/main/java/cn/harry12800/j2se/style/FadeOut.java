package cn.harry12800.j2se.style;

import java.awt.Window;

public class FadeOut extends Thread {
	private Window wnd;
	private boolean isShow = false;
	private boolean dispose;
	public void display (){
		isShow =true;
		start();
	} 
	public void hide (){
		isShow =false;
		start();
	} 
	public FadeOut(Window wnd) {
		this.wnd = wnd;
	}
	 
	@SuppressWarnings("deprecation")
	public void run() {
		if(!isShow){
			try {
				for (int i = 100; i > 0; i--) {
					Thread.sleep(5);
					wnd.setOpacity(i / 100f);
				}
				wnd.hide();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}else{
			try {
				wnd.setOpacity(0 / 100f);
				wnd.show();
				for (int i = 0; i < 100; i++) {
					Thread.sleep(5);
					wnd.setOpacity(i / 100f);
				}
				if(dispose)wnd.dispose();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	public void closeAndDispose() {
		isShow =false;
		dispose =true;
		start();
	}
	
}