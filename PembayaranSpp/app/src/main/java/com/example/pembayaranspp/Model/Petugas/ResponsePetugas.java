package com.example.pembayaranspp.Model.Petugas;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponsePetugas{

	@SerializedName("result")
	private List<PetugasItem> result;

	@SerializedName("code")
	private String code;

	@SerializedName("message")
	private String message;

	public void setResult(List<PetugasItem> result){
		this.result = result;
	}

	public List<PetugasItem> getResult(){
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