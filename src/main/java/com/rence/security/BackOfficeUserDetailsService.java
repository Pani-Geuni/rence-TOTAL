/**
 * @author 강경석
 */
package com.rence.security;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rence.backoffice.model.BackOfficeEntity;
import com.rence.backoffice.model.BackOfficeDTO;
import com.rence.backoffice.repository.BackOfficeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BackOfficeUserDetailsService implements UserDetailsService {
	@Autowired private BackOfficeRepository repository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public UserDetails loadUserByUsername(String backoffice_id) throws UsernameNotFoundException {
		
		BackOfficeDTO backoffice =  new BackOfficeDTO();
		
		BackOfficeEntity be = repository.findByBackoffice_email(backoffice_id);
		
		log.info("backoffice:{}", be);
		
		if (be == null) {
			throw new UsernameNotFoundException("No user found with the given email");
		} else {
			backoffice = modelMapper.map(be, BackOfficeDTO.class);
		}
		
		return new BackOfficeUserDetails(backoffice);

//		return createUser(backoffice);
	}
	
	 private org.springframework.security.core.userdetails.User createUser(BackOfficeDTO backoffice) {
	     List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
	      return new org.springframework.security.core.userdetails.User(backoffice.getUsername(),
	    		  backoffice.getPassword(),
	              grantedAuthorities);
	   }

}
