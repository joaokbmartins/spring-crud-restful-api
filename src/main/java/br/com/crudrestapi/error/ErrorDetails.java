package br.com.crudrestapi.error;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ErrorDetails {

	private String title;
	private int status;
	private String detail;
	private String timestamp;
	private String developerMessage;

	public ErrorDetails() {
		this.timestamp = new Timestamp(new Date().getTime()).toString();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDetail() {
		return detail;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}

}
