package com.rence.office.common;

import java.util.List;

import com.rence.office.model.ListViewDto;

public interface HeaderDAO {

	int search_list_totalCnt(String type, String location, String searchWord);

	List<ListViewDto> search_list(String type, String location, String searchWord, String condition, int i, int j);

}//end class
