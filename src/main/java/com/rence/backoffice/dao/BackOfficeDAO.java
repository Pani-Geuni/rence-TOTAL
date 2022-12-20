/**
 * 
 * @author 최진실
 *
 */
package com.rence.backoffice.dao;

import com.rence.backoffice.model.AuthDTO;
import com.rence.backoffice.model.BackOfficeOperatingTimeDTO;
import com.rence.backoffice.model.BackOfficeDTO;

public interface BackOfficeDAO {

	public BackOfficeDTO backoffice_insertOK(BackOfficeDTO vo);

	public int backoffice_operating_insertOK(BackOfficeOperatingTimeDTO ovo);

	public BackOfficeDTO backoffice_email_check(BackOfficeDTO bvo);

	public AuthDTO backoffice_auth_overlap(BackOfficeDTO bvo);

	public AuthDTO backoffice_auth_insert(AuthDTO avo);

	public AuthDTO backoffice_authOK_select(String backoffice_email, String auth_code);

	public void backoffice_auth_delete(AuthDTO avo2);

	public BackOfficeDTO backoffice_login_info(String username);

	public BackOfficeDTO backoffice_id_email_select(BackOfficeDTO bvo);

	public int backoffice_resetOK_pw(BackOfficeDTO bvo2);

	public int backoffice_settingOK_pw(BackOfficeDTO bvo);

	public void auth_auto_delete(String user_email);

}