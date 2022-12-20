/**
* @author 강경석
*/

package com.rence.user.model;

import java.sql.Date;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Immutable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
	
	String review_no;
	
	String review_content;
	
	Float review_point;
	
	Date review_date;
	
	String room_no;
	
	String backoffice_no;
	
	String user_no;
	
}//end class
