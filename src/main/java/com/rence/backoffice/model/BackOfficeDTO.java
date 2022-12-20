/**
 * 
 * @author 최진실
 *
 */
package com.rence.backoffice.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackOfficeDTO implements Serializable, UserDetails{  
   
   private String backoffice_no;
   
   private String owner_name;
   
   private String backoffice_id;
   
   private String backoffice_name;
   
   private String company_name;
   
   private String backoffice_pw;
   
   private String backoffice_tel;
   
   private String backoffice_email;
   
   private String zipcode;
   
   private String roadname_address;
   
   private String number_address;
   
   private String detail_address;
   
   private String backoffice_tag;
   
   private String backoffice_info;
   
   private String backoffice_option;
   
   private String backoffice_around;
   
   private String backoffice_image;
   
   private String host_image;
   
   private String backoffice_state;
   
   private Date apply_date;
   
   private String backoffice_type;
   
   
   // security
   @Override
   public String getPassword() {
	   log.info("this.getBackoffice_pw()::{}",this.getBackoffice_pw());
      return this.getBackoffice_pw();
   }

   @Override
   public String getUsername() {
	   log.info("this.getBackoffice_id()::{}",this.getBackoffice_id());
      return this.getBackoffice_id();
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
      return roles;
   }

}