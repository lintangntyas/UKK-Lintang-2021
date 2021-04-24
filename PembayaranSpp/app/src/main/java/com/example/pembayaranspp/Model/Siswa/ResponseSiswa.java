package com.example.pembayaranspp.Model.Siswa;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseSiswa{

	@SerializedName("result")
	private List<SiswaItem> result;

	@SerializedName("code")
	private String code;

	@SerializedName("message")
	private String message;

	public void setResult(List<SiswaItem> result){
		this.result = result;
	}

	public List<SiswaItem> getResult(){
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