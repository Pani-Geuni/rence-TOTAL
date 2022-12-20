/**
 * @author 강경석
 * user_review_View
 */

package com.rence.user.model;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Immutable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReview_ViewDto {

	
	String review_no;
	
	String review_content;
	
	Integer review_point;
	
	String review_date;
	
	String room_name;

	String company_name;

	String user_no;
	
} //end class