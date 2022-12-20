/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rence.dashboard.model.SalesSettlementSummaryViewEntity;

public interface SalesSettlementSummaryRepository extends JpaRepository<SalesSettlementSummaryViewEntity, Object> {

	// 일일 정산 요약
	@Query(nativeQuery = true, value = "select " 
			+ "(select ("
			+ "(select NVL(sum(p.payment_total),0) as sales_total from paymentinfo p, roominfo rm where p.room_no=rm.room_no and p.payment_state='T' and (((rm.room_type='desk') or (rm.room_type like 'meeting%')) and p.backoffice_no=?1  and trunc(payment_date)=trunc(current_date))) + "
			+ "(select NVL(sum(p.payment_total*0.8),0) as sales_total from paymentinfo p, roominfo rm where p.room_no=rm.room_no and p.payment_state='F' and p.sales_state='T' and ((rm.room_type='desk') or (rm.room_type like 'meeting%')and p.backoffice_no=?1 and trunc(payment_date)=trunc(current_date))) + "
			+ "(select NVL(sum(payment_total*0.2),0) as sales_total from paymentinfo p, roominfo rm where p.room_no=rm.room_no and p.payment_state='F' and ((rm.room_type='desk') or (rm.room_type like 'meeting%'))and p.backoffice_no=?1  and trunc(payment_date)=trunc(current_date)) + "
			+ "(select NVL(sum(payment_total),0) as sales_total from paymentinfo p, roominfo rm where p.room_no=rm.room_no and rm.room_type='office' and p.backoffice_no=?1 and trunc(payment_date)=trunc(current_date)) ) sales_total from dual) as sales_total, "
			+ "(select NVL(sum(cancel_amount+use_mileage),0) as sales_cancel from paymentinfo where backoffice_no=?1 and cancel_state='C' and trunc(payment_date)=trunc(current_date)) as sales_cancel "
			+ " from dual")
	public SalesSettlementSummaryViewEntity select_pay_day_sum(String backoffice_no);

}
