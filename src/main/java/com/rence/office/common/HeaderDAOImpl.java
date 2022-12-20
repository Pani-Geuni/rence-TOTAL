package com.rence.office.common;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.text.html.ListView;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rence.office.model.ListViewDto;
import com.rence.office.model.ListViewEntity;
import com.rence.office.model.RoomScheduleDto;
import com.rence.office.repo.OfficeListRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class HeaderDAOImpl implements HeaderDAO {

	@Autowired
	OfficeListRepository list_repository;
	
	@Autowired
	ModelMapper modelmapper;

	@Override
	public int search_list_totalCnt(String type, String location, String searchWord) {
		int cnt = list_repository.search_list_totalCnt("%" + type + "%", "%" + location + "%", "%" + searchWord + "%");

		return cnt;
	}

	@Override
	public List<ListViewDto> search_list(String type, String location, String searchWord, String condition, int min,
			int max) {
		List<ListViewEntity> entity_list = null;
	
		if (condition.equals("date")) {
			entity_list = list_repository.searchAll_orderBy_date("%" + type + "%", "%" + location + "%",
					"%" + searchWord + "%", min, max);
		} else if (condition.equals("rating")) {
			entity_list = list_repository.searchAll_orderBy_rating("%" + type + "%", "%" + location + "%",
					"%" + searchWord + "%", min, max);
		} else if (condition.equals("cheap")) {
			entity_list = list_repository.searchAll_orderBy_cheap("%" + type + "%", "%" + location + "%",
					"%" + searchWord + "%", min, max);
		} else if (condition.equals("expensive")) {
			entity_list = list_repository.searchAll_orderBy_expensive("%" + type + "%", "%" + location + "%",
					"%" + searchWord + "%", min, max);
		}
		
		List<ListViewDto> dtos = entity_list.stream().map(source -> modelmapper.map(source, ListViewDto.class))
				.collect(Collectors.toList());
		
		

		return dtos;
	}

}// end class
