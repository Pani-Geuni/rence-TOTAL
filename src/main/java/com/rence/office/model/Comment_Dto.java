/**
 * @author 김예은
*/
package com.rence.office.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment_Dto {
	
	String comment_no;
	
	String mother_no;
	
	String comment_content;
	
	String comment_date;
	
	String room_no;
	
	String backoffice_no;
	
	String user_no;
	
	String host_no;
	
	String comment_state;
	
	String writer;
	
	String is_secret;
	
}//end class
