/**
 * @author 강경석
 * user_commentpage_view
 */

package com.rence.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Immutable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuestionDto {
	
	private String comment_no;
	
	private String user_no;
	
	private String state;
	
	private String room_name;
	
	private String backoffice_name;
	
	private String comment_content;
	
	private String comment_date;
	
	private String mother_no;
	
	private String answer_content;
	
	private String answer_date;
}//end class
