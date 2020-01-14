package com.negen.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * 用户/账号
 * @author Negen
 *
 */
@Entity
@Table(name = "user")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	long id;
	String userName;
	String password;
	@ApiModelProperty(hidden = true)
	String salt;
	@ApiModelProperty(hidden = true)
	@OneToMany(cascade = {CascadeType.ALL} , fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	List<Role> roles;
	
	String introduction;    //自我简介
	String avatar;          //头像 
	String token;           
	
}
