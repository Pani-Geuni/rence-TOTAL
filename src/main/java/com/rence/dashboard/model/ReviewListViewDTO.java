/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewListViewDTO implements Serializable{

	private String review_no;
	
	private String review_content;
	
	private float review_point;
	
	private String review_date;
	
	private String user_no;
	
	private String user_image;
	
	private String user_name;
}
