

package com.rence.user.model;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Immutable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto{
	private String user_no;
	private String user_name;
	private String user_tel;
	private String user_email;
	
}//end class


///**
// * @author 김예은
// * 유저관련 VO
//*/
//
//package com.rence.user.model;
//
//public interface UserInfoDto{
//String getUser_no();
//String getUser_name();
//String getUser_tel();
//String getUser_email();
//
//}//end class