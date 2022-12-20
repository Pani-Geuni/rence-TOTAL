/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

public interface HostPaymentCancelService {

	public Map<String, Object> backoffice_reservation_cancel(String backoffice_no, String reserve_no, String user_no,
			String user_email, String reserve_stime, String reserve_etime) throws MalformedURLException, IOException;

}
