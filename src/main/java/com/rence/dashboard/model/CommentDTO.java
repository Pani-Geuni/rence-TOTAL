/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO implements Serializable{

	private String comment_no;
	
	private String mother_no;
	
	private String comment_state;
	
	private String room_name;
	
	private String comment_content;
	
	private String comment_date;
	
	private String room_no;
	
	private String backoffice_no;
	
	private String user_no;
	
	private String host_no;
	
	private String writer;
}
