/**
 * 
 * @author 최진실
 *
 */
package com.rence.backoffice.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class BackOfficeListDTO implements Serializable{ 

	private String backoffice_no;
	
	private String owner_name;
	
	private String backoffice_id;
	
	private String backoffice_name;
	
	private String company_name;
	
	private String backoffice_tel;
	
	private String backoffice_email;
	
	private String apply_date;

}
