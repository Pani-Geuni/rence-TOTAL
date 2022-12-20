
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
public class OfficeMileageDto {

	private String mileage_no;
	
	private int mileage_total;
	
	private String mileage_state;

	private String user_no;
	
	private int mileage_change;
	
	private String payment_no;
	
	
}//end class
