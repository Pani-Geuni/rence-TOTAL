/**
 * @author 강경석
 */

package com.rence.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.rence.backoffice.model.BackOfficeDTO;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BackOfficeUserDetails implements UserDetails {
	
	private BackOfficeDTO backoffice;
	

	
	public BackOfficeUserDetails(BackOfficeDTO backoffice) {
		this.backoffice = backoffice;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
	      return null;

	}
	@Override
	public String getPassword() {
		return backoffice.getBackoffice_pw();
	}
	@Override
	public String getUsername() {
	      log.info("id::::::::::::::::::{}",backoffice.getBackoffice_id());
	      return backoffice.getBackoffice_id();

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