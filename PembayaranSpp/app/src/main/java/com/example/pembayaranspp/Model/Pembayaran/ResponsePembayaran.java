package com.example.pembayaranspp.Model.Pembayaran;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponsePembayaran{

	@SerializedName("result")
	private List<PembayaranItem> result;

	@SerializedName("code")
	private String code;

	@SerializedName("message")
	private String message;

	public void setResult(List<PembayaranItem> result){
		this.result = result;
	}

	public List<PembayaranItem> getResult(){
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