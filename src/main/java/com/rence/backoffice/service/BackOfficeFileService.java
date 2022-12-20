/**
 * 
 * @author 최진실
 *
 */
package com.rence.backoffice.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rence.backoffice.model.BackOfficeDTO;

public interface BackOfficeFileService {

	public BackOfficeDTO backoffice_image_upload(BackOfficeDTO bvo, MultipartHttpServletRequest mtfRequest,
			MultipartFile multipartFile_room);

}
