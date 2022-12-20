package com.rence.office.common;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rence.office.model.ListViewDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HeaderServiceImpl implements HeaderService {
	
	@Autowired
	HeaderDAO dao;

	@Override
	public Map<String, Object> search_list(String type, String location, String searchWord, String condition,
			Integer page) {

		log.info("search_list()..");
		log.info("type: {} location: {} searchWord: {} condition: {} page: {} ",type, location, searchWord, condition, page );

		Map<String, Object> map = new HashMap<String, Object>();

		int total_cnt = dao.search_list_totalCnt(type, location, searchWord);
		log.info("total_cnt : {}", total_cnt);

		List<ListViewDto> list = null;
		list = dao.search_list(type, location, searchWord, condition, 12 * (page - 1) + 1, 12 * (page));

		if (list == null)
			map.put("cnt", 0);
		else
			map.put("cnt", list.size());

		if (list != null) {
			for (ListViewDto dto : list) {
				DecimalFormat dc = new DecimalFormat("###,###,###,###");
				String ch = dc.format(Integer.parseInt(dto.getMin_room_price()));
				dto.setMin_room_price(ch);
				dto.setAvg_rating(Double.toString((Math.round(Double.parseDouble(dto.getAvg_rating()) * 100) / 100.0)));

				dto.setBackoffice_image(
						"https://rence.s3.ap-northeast-2.amazonaws.com/space/" + dto.getBackoffice_image());

				if (dto.getBackoffice_tag() != null) {
					String[] tags = dto.getBackoffice_tag().split(",");

					int i = 0;
					for (String tag : tags) {
						tag = "#" + tag;
						tags[i] = tag;
						i++;
					}

					dto.setBackoffice_tag(String.join(" ", tags));
				}

				if (dto.getRoadname_address().contains(" ")) {
					String road_name = dto.getRoadname_address().split(" ")[0] + " "
							+ dto.getRoadname_address().split(" ")[1];
					dto.setRoadname_address(road_name);
				}
			}
		}

		map.put("condition", condition);
		map.put("page", "list_page");
		map.put("list", list);
		map.put("nowCnt", 1);

		if (total_cnt > 0)
			map.put("maxCnt", total_cnt);
		else
			map.put("maxCnt", 0);

		
		return map;
	}

}// end class
