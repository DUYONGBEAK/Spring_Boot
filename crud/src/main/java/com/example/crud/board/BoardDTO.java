package com.example.crud.board;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "board")
@Data
@AllArgsConstructor
public class BoardDTO {
	@Id
	private String _id; // 식별자
	private String _class;
	private String userId;
	private String title;
	private String content;
	private String date;

	BoardDTO() {
		
	}
	
	BoardDTO(String title, String content, String date) {
		this.title = title;
		this.content = content;
		this.date = date;
	}
	
	BoardDTO(String title) {
		this.title = title;
	}

	BoardDTO(String title, String content,String userId, String date) {
		this.title = title;
		this.content = content;
		this.userId = userId;
		this.date = date;
	}
	
	BoardDTO(String id, String title, String content,String userId, String date) {
		this._id = id;
		this.title = title;
		this.content = content;
		this.userId = userId;
		this.date = date;
	}

	public String get_class() {
		return _class;
	}

	public void set_class(String _class) {
		this._class = _class;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getId() {
		return _id;
	}
}
