/**
 * 
 * @author 최진실
 *
 */
package com.rence.backoffice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rence.backoffice.dao.BackOfficeFAO;
import com.rence.backoffice.model.BackOfficeDTO;
import com.rence.backoffice.service.BackOfficeFileService;

@Service
public class BackOfficeFileServiceImpl implements BackOfficeFileService {
	
	@Autowired
	BackOfficeFAO fao;

	@Override
	public BackOfficeDTO backoffice_image_upload(BackOfficeDTO bvo, MultipartHttpServletRequest mtfRequest,
			MultipartFile multipartFile_room) {
		bvo = fao.backoffice_image_upload(bvo, mtfRequest, multipartFile_room);
		return bvo;
	}

}
