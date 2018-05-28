package model;

import javax.persistence.*;
@Entity
@Table(name="usuario")
public class Usuario {

	@Id
	private String email;
	private String name;
	private String lastName;
	private int age;
	private Gender gender;
	private String password;
	private boolean premium;
	
	public Usuario() {
		super();
	}
	
	public Usuario(String email, String name, String lastName, int age, Gender gender, String password) {
		this.email = email;
		this.name = name;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.password = password;
		this.premium = false;
	}
	
	public Usuario login(String email, String password) {
		return null;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}
	
}
