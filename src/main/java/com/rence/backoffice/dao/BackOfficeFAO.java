/**
 * 
 * @author 최진실
 *
 */
package com.rence.backoffice.dao;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rence.backoffice.model.BackOfficeDTO;

public interface BackOfficeFAO {

	public BackOfficeDTO backoffice_image_upload(BackOfficeDTO vo, MultipartHttpServletRequest mtfRequest,
			MultipartFile multipartFile_room);

	public BackOfficeDTO host_image_upload(BackOfficeDTO vo, MultipartFile multipartFile_host);


}
