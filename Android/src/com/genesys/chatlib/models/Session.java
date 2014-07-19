package com.genesys.chatlib.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Session implements Parcelable {
	private String path;
	private int code;
	private String id;
	
	public Session(){}
	
	public void initSession(JSONObject object)
	{
		try {
			this.setPath(object.getString("path"));
			this.setCode(object.getInt("statusCode"));
			this.setId(object.getString("id"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(path); 
		dest.writeInt(code); 
		dest.writeString(id); 
	}
	
	public static final Parcelable.Creator<Session> CREATOR = new Creator<Session>() {

		public Session createFromParcel(Parcel source) {
			Session chatSession = new Session();
			chatSession.path = source.readString();
			chatSession.code = source.readInt();
			chatSession.id = source.readString();
			return chatSession;
		}

		public Session[] newArray(int size) {
			return new Session[size];

		}

	};

}
