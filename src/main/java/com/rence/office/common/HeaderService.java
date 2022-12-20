package com.rence.office.common;

import java.util.Map;

public interface HeaderService {

	Map<String, Object> search_list(String type, String location, String searchWord, String condition, Integer page);

}//end class
