package com.example.pembayaranspp.Model.Register;

import com.google.gson.annotations.SerializedName;

public class ResponseRegister{

	@SerializedName("data")
	private RegisterData data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setData(RegisterData data){
		this.data = data;
	}

	public RegisterData getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}