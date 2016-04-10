package com.reva.data;

public class CityMasterData {
	private int cityId;
	private int activeFlag;
	private String city;
	private long timesatmp;

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(int activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public long getTimesatmp() {
		return timesatmp;
	}

	public void setTimesatmp(long timesatmp) {
		this.timesatmp = timesatmp;
	}
}
