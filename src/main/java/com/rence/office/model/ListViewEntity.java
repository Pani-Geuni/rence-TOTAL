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


@Entity
@Immutable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name="ListView")
public class ListViewEntity {
	
	@Id	// PK 설정
	@Column(name="backoffice_no")
	String backoffice_no;
	
	@Column(name="backoffice_type")
	String backoffice_type;
	
	@Column(name="company_name")
	String company_name;
	
	@Column(name="roadname_address")
	String roadname_address;
	
	@Column(name="backoffice_tag")
	String backoffice_tag;
	
	@Column(name="backoffice_image")
	String backoffice_image;
	
	@Column(name="min_room_price")
	String min_room_price;
	
	@Column(name="avg_rating")
	String avg_rating;
	
}
