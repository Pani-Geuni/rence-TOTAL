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
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_commentpage_view")
public class UserQuestioEntity {
	
	@Id 
	@Column(name="comment_no", insertable = false, updatable = false)
	private String comment_no;
	
	@Column(name="user_no")
	private String user_no;
	
	@Column(name="state")
	private String state;
	
	@Column(name="room_name")
	private String room_name;
	
	@Column(name="backoffice_name")
	private String backoffice_name;
	
	@Column(name="comment_content")
	private String comment_content;
	
	@Column(name="comment_date")
	private String comment_date;
	
	@Column(name="mother_no")
	private String mother_no;
	
	@Transient
	String answer_content;
	
	@Transient
	String answer_date;

}//end class
