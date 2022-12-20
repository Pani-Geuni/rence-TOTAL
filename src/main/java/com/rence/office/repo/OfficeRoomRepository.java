package com.rence.office.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rence.office.model.OfficeRoomEntity;

public interface OfficeRoomRepository extends JpaRepository<OfficeRoomEntity, Object> {
	
	
	@Query(nativeQuery = true, value = "select * from roominfo where room_state = 'T' and backoffice_no=?1")
	public List<OfficeRoomEntity> select_all_room_info(String backoffice_no);
	
	
}//end clas
