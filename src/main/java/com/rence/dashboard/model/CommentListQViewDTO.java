/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentListQViewDTO {

	private String comment_no;
	
	private String user_no;
	
	private String comment_content;
	
	private String comment_date;
	
	private String room_name;
	
	private String comment_state;
	
	private String room_no;
	
	private String backoffice_no;
	
	private String answer_no;
	
	private String answer_content;
	
	private String answer_date;
	
}
