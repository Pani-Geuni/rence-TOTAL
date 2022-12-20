package com.rence.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rence.user.dao.userFao;
import com.rence.user.model.UserDto;


@Service
public class UserFileuploadServiceImpl implements UserFileuploadService {

	@Autowired
	userFao fao;

	@Override
	public UserDto FileuploadOK(UserDto udto, MultipartHttpServletRequest mtfRequest,
			MultipartFile multipartFile_user) {
		return fao.FileuploadOK(udto, mtfRequest, multipartFile_user);
	}

}//end class
