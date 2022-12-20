package com.rence.office.model;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Immutable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OfficeRoomDto {

	private String room_no;
	
	private String backoffice_no;
	
	private String room_type;
	
	private String room_name;
	
	private String room_price;
	
	private String room_state;
	
}//end class
