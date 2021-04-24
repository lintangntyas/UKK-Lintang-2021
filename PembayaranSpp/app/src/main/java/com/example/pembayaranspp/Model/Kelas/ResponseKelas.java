package com.example.pembayaranspp.Model.Kelas;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseKelas{

	@SerializedName("result")
	private List<KelasItem> result;

	@SerializedName("code")
	private String code;

	@SerializedName("message")
	private String message;

	public void setResult(List<KelasItem> result){
		this.result = result;
	}

	public List<KelasItem> getResult(){
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