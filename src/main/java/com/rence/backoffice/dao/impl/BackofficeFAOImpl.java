/**
 * 
 * @author 최진실
 *
 */
package com.rence.backoffice.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.rence.backoffice.dao.BackOfficeFAO;
import com.rence.backoffice.model.BackOfficeDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class BackofficeFAOImpl implements BackOfficeFAO {
	
	@Autowired
	ServletContext context;

	private String S3Bucket = "rence/space"; // Bucket 이름

	@Autowired
	AmazonS3Client amazonS3Client;
	
	@Override
	public BackOfficeDTO backoffice_image_upload(BackOfficeDTO vo, MultipartHttpServletRequest mtfRequest,
			MultipartFile multipartFile_room) {

		if (multipartFile_room != null) {
			log.info("filename : {}", multipartFile_room.getOriginalFilename());
			List<MultipartFile> imgs = mtfRequest.getFiles("multipartFile_room");

			List<String> img_list = new ArrayList<String>();
			for (MultipartFile mf : imgs) {

				String originFileName = UUID.randomUUID()+"."+StringUtils.getFilenameExtension(mf.getOriginalFilename());
				long fileSize = mf.getSize();

				img_list.add(originFileName); // vo에 저장

				log.info("originFileName : {}", originFileName);
				log.info("fileSize : {}", fileSize);

				ObjectMetadata objectMetaData = new ObjectMetadata();
				objectMetaData.setContentType(mf.getContentType());
				objectMetaData.setContentLength(fileSize);
				try {

					amazonS3Client.putObject(
							new PutObjectRequest(S3Bucket, originFileName, mf.getInputStream(), objectMetaData)
									.withCannedAcl(CannedAccessControlList.PublicRead));

					String imagePath = amazonS3Client.getUrl(S3Bucket, originFileName).toString();
					log.info("이미지 링크 : {}", imagePath);

					String img_vo = img_list.stream().collect(Collectors.joining(", "));
					vo.setBackoffice_image(img_vo);

				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} else if (vo.getBackoffice_image() == null) {
			vo.setBackoffice_image("img_room_001.jpg");
		} else if (multipartFile_room == null) {
			vo.setBackoffice_image("img_room_001.jpg");
		}

		return vo;
	}

	@Override
	public BackOfficeDTO host_image_upload(BackOfficeDTO vo, MultipartFile multipartFile_host) {
		vo.setHost_image("img_host_001.jpg");
		return vo;
	}


}
