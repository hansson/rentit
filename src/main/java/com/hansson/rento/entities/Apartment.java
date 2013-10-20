package com.hansson.rento.entities;

public class Apartment {
	
	public static final String CREATE_STATEMENT = "crate table t_apartment ()"
	public static final String INSERT_STATEMENT = "insert into t_apartment (mArea, mCity, mAddress, mUrl, mRooms, mSize, mRent, mIdentifier, mLandlord) values (':mArea', ':mCity', ':mAddress', ':mUrl', ':mRooms', ':mSize', ':mRent', ':mIdentifier', ':mLandlord')";

	private int mId;
	private String mArea;
	private String mCity;
	private String mAddress;
	private String mUrl;
	private double mRooms;
	private int mSize;
	private int mRent;
	// This identifier should be unique to be able to decide if the apartment is a new one or if it was already stored in the database.
	private String mIdentifier;
	private String mLandlord;

	public Apartment() {
	}
	
	public int getId() {
		return mId;
	}

	public void setId(int id) {
		mId = id;
	}

	public Apartment(String landlord) {
		mLandlord = landlord;
	}

	public String getArea() {
		return mArea;
	}

	public void setArea(String area) {
		mArea = area;
	}

	public String getCity() {
		return mCity;
	}

	public void setCity(String city) {
		mCity = city;
	}

	public String getAddress() {
		return mAddress;
	}

	public void setAddress(String address) {
		mAddress = address;
	}

	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String url) {
		mUrl = url;
	}

	public double getRooms() {
		return mRooms;
	}

	public void setRooms(double rooms) {
		mRooms = rooms;
	}

	public int getSize() {
		return mSize;
	}

	public void setSize(int size) {
		mSize = size;
	}

	public int getRent() {
		return mRent;
	}

	public void setRent(int rent) {
		mRent = rent;
	}
	public String getIdentifier() {
		return mIdentifier;
	}

	public void setIdentifier(String identifier) {
		mIdentifier = identifier;
	}

	public String getLandlord() {
		return mLandlord;
	}

	public void setLandlord(String landlord) {
		mLandlord = landlord;
	}


}
