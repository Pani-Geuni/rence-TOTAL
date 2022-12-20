package com.rence.office.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class OfficeQuestionDto {

	private String comment_no;
	
	private String mother_no;
	
	private String host_no;
	
	private String backoffice_no;
	
	private String comment_state;
	
	private String comment_content;
	
	private String comment_date;
	
	private String user_no;
	
	private String user_name;
	
	private String user_id;
	
	private String user_image;
	
	private String room_no;
	
	private String room_name;
	
	private String writer;
	
	private String is_secret;
	
	String answer_content;
	
	String answer_date;
	
}//end class
