/**
 * @author 강경석
 * 
 */

package com.rence.user.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Immutable;
import org.springframework.lang.NonNullFields;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Immutable
@Slf4j
@Data
public class UserDto implements Serializable,UserDetails  {
	
	
	private String user_no; //사용자 고유번호
	
	private String user_image; //프로필 이미지
	
	private String user_id; //아이디
	
	private String user_pw; //패스워드
	
	private String user_name; //실명
	
	private String user_email; //이메일
	
	private String user_tel; //전화번호
	
	private String user_birth; //생년월일
	
	private String user_state; //회원상태
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		  Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();

	      return roles;

	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.getUser_pw();
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
	      log.info("id::::::::::::::::::{}",this.getUser_id());
	      return this.getUser_id();

	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}//end class
