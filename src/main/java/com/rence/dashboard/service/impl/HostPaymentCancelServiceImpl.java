/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rence.backoffice.model.BackOfficeDTO;
import com.rence.dashboard.common.DashboardSendEmail;
import com.rence.dashboard.dao.DashboardDAO;
import com.rence.dashboard.dao.HostPaymentCancelDAO;
import com.rence.dashboard.model.BOPaymentDTO;
import com.rence.dashboard.service.HostPaymentCancelService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HostPaymentCancelServiceImpl implements HostPaymentCancelService{
	
	@Autowired
	HostPaymentCancelDAO cancelDao;
	
	@Autowired
	DashboardDAO dao;
	
	@Autowired
	DashboardSendEmail dashboardSendEmail;
	

	/**
	 * 일정 관리 - 예약취소
	 * @throws IOException 
	 * 
	 */
	@Override
	public Map<String, Object> backoffice_reservation_cancel(String backoffice_no, String reserve_no, String user_no,
			String user_email, String reserve_stime, String reserve_etime) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();

		// 에약 상태 cancel로 변경, 예약자에게 취소 메일 보내기, 결제 환불 상태 C , payment_date=current_date, 결제 테이블에서 사용한 마일리지와 돈 환불.
		BOPaymentDTO pvo = dao.backoffice_reservation_cancel(backoffice_no, reserve_no, user_no);

		if (pvo != null) {
			
			String token = cancelDao.getToken();
			int amount = Integer.parseInt(pvo.getActual_payment());
			cancelDao.payMentCancel(token, pvo.getImp_uid(), amount, "관리자 취소");
	        
			BackOfficeDTO bvo = dao.backoffice_select_companyname(backoffice_no);
			String company_name = bvo.getCompany_name();
			int flag = dashboardSendEmail.reserve_cancel_mail(user_no, user_email, reserve_stime, reserve_etime,
					company_name);
			if (flag == 1) {
				log.info("successed...");
				map.put("pvo", pvo);
				map.put("result", "1");
			} else {
				log.info("mail falied...");
				map.put("result", "0");
			}
		} else {
			log.info("cancel falied...");
			map.put("result", "0");
		}
		return map;
	}

}
