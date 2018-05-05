package cn.harry12800.j2se.tip;

public class Letter {

	/**
	 * 头信息
	 */
	private String title;
	/**
	 * 中间内容信息
	 */
	private String content;
	/**
	 * 右下角信息
	 */
	private String date;
	private int indent=0;
	
	public int getIndent() {
		return indent;
	}
	public void setIndent(int indent) {
		this.indent = indent;
	}
	public Letter(String title, String content, String date) {
		super();
		this.title = title;
		this.content = content;
		this.date = date;
	}
	/**
	 * 获取title
	 *	@return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置title
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取content
	 *	@return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置content
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取date
	 *	@return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * 设置date
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Letter [title=" + title + ", content=" + content + ", date=" + date + "]";
	}
	 
	 
}
