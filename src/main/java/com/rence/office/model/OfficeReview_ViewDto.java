package com.rence.office.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeReview_ViewDto {

	private String review_no;
	
	private String room_no;
	
	private String backoffice_no;
	
	private String user_no;
	
	private String review_content;
	
	private int review_point;
	
	private String review_date;

	private String user_image;
	
	private String user_name;
	
	
}//end class
