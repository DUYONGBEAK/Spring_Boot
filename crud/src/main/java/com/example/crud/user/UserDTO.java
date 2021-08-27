package com.example.crud.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Document(collection="user") @Data @AllArgsConstructor
public class UserDTO {
	@Id
	private String _id; //식별자
	private String _class;
	private String name;
	private int age;
	private String pwd;
	private String sex;
	
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String get_class() {
		return _class;
	}

	public void set_class(String _class) {
		this._class = _class;
	}

	
	UserDTO(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	UserDTO(String id, String name, int age) {
		this._id = id;
		this.name = name;
		this.age = age;
	}
	
	UserDTO(String id, String pwd, String name, int age, String sex) {
		this._id = id;
		this.name = name;
		this.age = age;
		this.pwd = pwd;
		this.sex = sex;
	}
	
	UserDTO() {
		
	}
	
	public String getId() { return _id; }
}
