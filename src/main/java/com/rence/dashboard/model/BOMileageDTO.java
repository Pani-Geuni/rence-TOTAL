/**
 * @author 최진실
 */

package com.rence.dashboard.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BOMileageDTO {

	private String mileage_no;
	
	private int mileage_total;
	
	private String mileage_state;
	
	private String user_no;
	
	private int mileage_change;
	
	private String payment_no;
	
	
}
