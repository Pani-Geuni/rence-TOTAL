package com.rence.user.dao;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rence.user.model.UserDto;

public interface userFao {

	UserDto FileuploadOK(UserDto udto, MultipartHttpServletRequest mtfRequest, MultipartFile multipartFile_user);

}//end class
