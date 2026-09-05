package apiTests.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse{

	@JsonProperty("code")
	private int code;

	@JsonProperty("type")
	private String type;

	@JsonProperty("message")
	private String message;

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}