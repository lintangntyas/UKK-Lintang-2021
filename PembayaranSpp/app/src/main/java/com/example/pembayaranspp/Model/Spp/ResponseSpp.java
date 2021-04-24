package com.example.pembayaranspp.Model.Spp;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseSpp{

	@SerializedName("result")
	private List<SppItem> result;

	@SerializedName("code")
	private String code;

	@SerializedName("message")
	private String message;

	public void setResult(List<SppItem> result){
		this.result = result;
	}

	public List<SppItem> getResult(){
		return result;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}