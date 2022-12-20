package com.rence.security;

/**
 * @author 강경석
 */

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.rence.user.model.UserEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserUserDetails implements UserDetails {

	private UserEntity user;

	public UserUserDetails(UserEntity user) {
		this.user = user;
	}

	//현재 회원이 가지고 있는 권한들을 Lsit<GrantedAuthority> 형태로 리턴
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return null;

	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getUser_pw();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		log.info("id::::::::::::::::::{}", user.getUser_id());
		return user.getUser_id();

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

}// end class
