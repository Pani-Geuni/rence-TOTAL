package com.rence.office.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
public class ListViewDto {
	
	String backoffice_no;
	
	String backoffice_type;
	
	String company_name;
	
	String roadname_address;
	
	String backoffice_tag;
	
	String backoffice_image;
	
	String min_room_price;
	
	String avg_rating;
	
}//end class
