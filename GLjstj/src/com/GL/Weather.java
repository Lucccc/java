package com.GL;

import java.util.List;

public class Weather {
	private int zhanhao;
	private int year;
	private int month;
	private int day;
	private int hour;
	private String R;
	private List<String> minutes;
	
	
	
	public int getZhanhao() {
		return zhanhao;
	}
	public void setZhanhao(int zhanhao) {
		this.zhanhao = zhanhao;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public String getR() {
		return R;
	}
	public void setR(String r) {
		R = r;
	}
	public List<String> getMinutes() {
		return minutes;
	}
	public void setMinutes(List<String> minutes) {
		this.minutes = minutes;
	}
	@Override
	public String toString() {
		return "Weather [zhanhao=" + zhanhao + ", year=" + year + ", month="
				+ month + ", day=" + day + ", hour=" + hour + ", R=" + R
				+ ", minutes=" + minutes + "]";
	}

}
