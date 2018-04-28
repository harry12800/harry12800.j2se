package cn.harry12800.j2se.calendar;

import java.util.Calendar;

public class Solar {  
    public int year;
    public int month;
    public int day;  
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Solar [solarYear=" + year + ", solarMonth=" + month
				+ ", solarDay=" + day + "]";
	}
	/**
	 * 获取solarDay
	 *	@return the solarDay
	 */
	public int getSolarDay() {
		return day;
	}
	/**
	 * 设置solarDay
	 * @param solarDay the solarDay to set
	 */
	public void setSolarDay(int solarDay) {
		this.day = solarDay;
	}
	/**
	 * 获取solarMonth
	 *	@return the solarMonth
	 */
	public int getSolarMonth() {
		return month;
	}
	/**
	 * 设置solarMonth
	 * @param solarMonth the solarMonth to set
	 */
	public void setSolarMonth(int solarMonth) {
		this.month = solarMonth;
	}
	/**
	 * 获取solarYear
	 *	@return the solarYear
	 */
	public int getSolarYear() {
		return year;
	}
	/**
	 * 设置solarYear
	 * @param solarYear the solarYear to set
	 */
	public void setSolarYear(int solarYear) {
		this.year = solarYear;
	}
	public static int getCurrentYear() {
		int i = Calendar.getInstance().get(Calendar.YEAR);
		return i;
	}
    
}  
  