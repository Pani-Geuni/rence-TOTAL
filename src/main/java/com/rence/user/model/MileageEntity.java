package com.rence.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Immutable
@AllArgsConstructor
@NoArgsConstructor
@Table(name="MILEAGE")
public class MileageEntity {
	
	@Id	// PK 설정
	@Column(name="mileage_no")
	private String mileage_no;
	
	@Column(name="mileage_total")
	private Integer  mileage_total;
	
	@Column(name="mileage_state")
	private String mileage_state;
	
	@Column(name="user_no")
	private String user_no;
	
	@Column(name="mileage_change")
	private Integer mileage_change;
	
	@Column(name="payment_no")
	private String payment_no;
	
	

}//end class