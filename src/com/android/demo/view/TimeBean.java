package com.android.demo.view;

public class TimeBean {
	private int year;  
	private int month; 
	private int dayMin;    // the min day
	private int dayMax; 
	private int timeMin; 
	private int timeMax; 
    private double[] day;       //array of day		
    private double[] time;    

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
	public int getDayMin() {
		return dayMin;
	}
	public void setDayMin(int dayMin) {
		this.dayMin = dayMin;
	}
	public int getDayMax() {
		return dayMax;
	}
	public void setDayMax(int dayMax) {
		this.dayMax = dayMax;
	}
	public int getTimeMin() {
		return timeMin;
	}
	public void setTimeMin(int timeMin) {
		this.timeMin = timeMin;
	}
	public int getTimeMax() {
		return timeMax;
	}
	public void setTimeMax(int timeMax) {
		this.timeMax = timeMax;
	}
	public double[] getDay() {
		return day;
	}
	public void setDay(double[] day) {
		this.day = day;
	}
	public double[] getTime() {
		return time;
	}
	public void setTime(double[] time) {
		this.time = time;
	}
}
