/**
 * @author 최진실
 */
package com.rence.master.dao;

import java.util.List;

import com.rence.backoffice.model.BackOfficeListDTO;
import com.rence.backoffice.model.BackOfficeDTO;

public interface MasterDAO {

	public List<BackOfficeListDTO> backoffice_applyList_selectAll(Integer page);

	public int backoffice_grant(BackOfficeDTO bvo);

	public int backoffice_refuse(BackOfficeDTO bvo);

	public List<BackOfficeListDTO> backoffice_endList_selectAll(Integer page);

	public int backoffice_revoke(BackOfficeDTO bvo);

	public BackOfficeDTO master_backoffice_detail_selectOne(BackOfficeDTO bvo);

}
