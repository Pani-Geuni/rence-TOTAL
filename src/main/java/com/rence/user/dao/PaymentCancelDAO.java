package com.rence.user.dao;

import java.io.IOException;

public interface PaymentCancelDAO {

	String getToken() throws IOException;

	void payMentCancel(String token, String imp_uid, int amount, String string) throws IOException;

}//end class
