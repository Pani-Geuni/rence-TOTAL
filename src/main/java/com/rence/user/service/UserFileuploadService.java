package com.rence.user.service;


import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rence.user.model.UserDto;

public interface UserFileuploadService {
	
	public UserDto FileuploadOK(UserDto udto, MultipartHttpServletRequest mtfRequest, MultipartFile multipartFile_user);

	
} //end class
