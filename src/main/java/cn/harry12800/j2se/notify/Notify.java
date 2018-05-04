package cn.harry12800.j2se.notify;

public interface Notify {
	public String getTitle();

	public int getTimes();

	public String getContent();

	public String getIcon();

	public void show();

	public void close();
}
