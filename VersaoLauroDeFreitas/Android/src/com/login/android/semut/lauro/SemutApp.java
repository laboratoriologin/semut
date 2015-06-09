package com.login.android.semut.lauro;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.login.android.semut.lauro.sqlite.dao.DataManager;

public class SemutApp extends Application implements LocationListener {

	private Double latitude = -12.875836;
	private Double longitude = -38.308309;
	
	private Boolean isChanged = false;
	private LocationManager locationManager;
	private DataManager dataManager;

	@Override
	public void onCreate() {
		super.onCreate();
		setDataManager(new DataManager(this));

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

		if (locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null) {
			latitude = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
			longitude = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
			isChanged = true;
		}
		
	}

	public DataManager getDataManager() {
		return dataManager;
	}

	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Override
	public void onLocationChanged(Location location) {
		this.latitude = location.getLatitude();
		this.longitude = location.getLongitude();
		isChanged = true;
	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	/**
	 * @return the isChanged
	 */
	public Boolean getIsChanged() {
		return isChanged;
	}

	/**
	 * @param isChanged the isChanged to set
	 */
	public void setIsChanged(Boolean isChanged) {
		this.isChanged = isChanged;
	}
}
