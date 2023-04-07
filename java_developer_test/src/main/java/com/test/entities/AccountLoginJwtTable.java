package com.test.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "account_login_jwt")
public class AccountLoginJwtTable {

	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "jwt_value")
	private String jwt_value;
	
	@Column(name = "created_date")
	private String created_date;
	
	@Column(name = "created_by")
	private String created_by;
}
