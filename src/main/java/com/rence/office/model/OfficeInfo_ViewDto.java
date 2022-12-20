/**
 * @author 전판근
 */

package com.rence.office.model;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Immutable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeInfo_ViewDto {
	
	private String backoffice_no;
	
	private String owner_name;
	
	private String backoffice_id;
	
	private String backoffice_name;
	
	private String company_name;
	
	private String backoffice_image;
	
	private String backoffice_type;
	
	private String backoffice_tag;
	
	private String backoffice_option;
	
	private String backoffice_around;
	
	private String backoffice_tel;
	
	private String backoffice_email;
	
	private String backoffice_info;
	
	private String zipcode;
	
	private String roadname_address;
	
	private String number_address;
	
	private String detail_address;
	
	private Double avg_rating;	
}
