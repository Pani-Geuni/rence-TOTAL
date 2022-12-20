/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.dao;

import java.io.IOException;
import java.net.MalformedURLException;

public interface HostPaymentCancelDAO {

	public String getToken() throws MalformedURLException, IOException;

	public void payMentCancel(String token, String imp_uid, int amount, String string) throws IOException;

}
