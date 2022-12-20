/**
 * @author 강경석, 김예은
 */

package com.rence.office.common;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "공통 컨트롤러")
@RequestMapping("/common")
public class HeaderController {

	@Autowired
	HeaderService service;

	// 자동 개행 및 줄 바꿈 (new Gson은 일자로 나옴)
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	/**
	 * 서치바 검색
	 */
	@RequestMapping(value = "/search_list", method = RequestMethod.GET)
	public String search_list(String type, String location, String searchWord, String condition, Integer page) {
		log.info("search_list()...");

		Map<String, Object> map = service.search_list(type, location, searchWord, condition, page);

		String jsonObject = gson.toJson(map);
		return jsonObject;
	}

}// end class
