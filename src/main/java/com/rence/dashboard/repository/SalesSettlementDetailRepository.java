/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rence.dashboard.model.SalesSettlementDetailViewDTO;
import com.rence.dashboard.model.SalesSettlementDetailViewEntity;

public interface SalesSettlementDetailRepository extends JpaRepository<SalesSettlementDetailViewEntity, Object> {

	// 일
	@Query(nativeQuery = true, value = "select "
			+ "(select ("
			+ "(select NVL(sum(p.payment_total),0) as sales_total from paymentinfo p, roominfo rm where p.room_no=rm.room_no and p.payment_state='T' and (((rm.room_type='desk') or (rm.room_type like 'meeting%')) and p.backoffice_no=?1  and trunc(payment_date)=trunc(current_date))) + "
			+ "(select NVL(sum(p.payment_total*0.8),0) as sales_total from paymentinfo p, roominfo rm where p.room_no=rm.room_no and p.payment_state='F' and p.sales_state='T' and ((rm.room_type='desk') or (rm.room_type like 'meeting%')and p.backoffice_no=?1 and trunc(payment_date)=trunc(current_date))) + "
			+ "(select NVL(sum(payment_total*0.2),0) as sales_total from paymentinfo p, roominfo rm where p.room_no=rm.room_no and p.payment_state='F' and ((rm.room_type='desk') or (rm.room_type like 'meeting%'))and p.backoffice_no=?1  and trunc(payment_date)=trunc(current_date)) + "
			+ "(select NVL(sum(payment_total),0) as sales_total from paymentinfo p, roominfo rm where p.room_no=rm.room_no and rm.room_type='office' and p.backoffice_no=?1 and payment_state='T' and trunc(payment_date)=trunc(current_date)) ) sales_total from dual) as sales_total, "
			+ "(select NVL(sum(cancel_amount+use_mileage),0) as sales_cancel from paymentinfo where backoffice_no=?1 and cancel_state='C' and trunc(payment_date)=trunc(current_date)) as sales_cancel, "
			+ "(select ("
			+ "(select NVL(sum(p.payment_total),0) as sales_total from paymentinfo p left outer join roominfo rm on p.room_no=rm.room_no where p.payment_state='T' and (((rm.room_type='desk') or (rm.room_type like 'meeting%')) and p.backoffice_no=?1 and trunc(payment_date)=trunc(current_date)-1) ) + "
			+ "(select NVL(sum(p.payment_total*0.8),0) as sales_total from paymentinfo p left outer join roominfo rm on p.room_no=rm.room_no where p.payment_state='F' and p.sales_state='T' and ((rm.room_type='desk') or (rm.room_type like 'meeting%') and p.backoffice_no=?1 and trunc(payment_date)=trunc(current_date)-1) )+ "
			+ "(select NVL(sum(payment_total*0.2),0) as sales_total from paymentinfo p left outer join roominfo rm on p.room_no=rm.room_no where p.payment_state='F' and ((rm.room_type='desk') or (rm.room_type like 'meeting%'))and p.backoffice_no=?1 and trunc(payment_date)=trunc(current_date)-1 ) + "
			+ "(select NVL(sum(payment_total),0) as sales_total from paymentinfo p left outer join roominfo rm on p.room_no=rm.room_no where rm.room_type='office' and p.backoffice_no=?1 and payment_state='T' and trunc(payment_date)=trunc(current_date)-1 ) ) pre_sales_total from dual) as pre_sales_total, "
			+ "(select NVL(sum(cancel_amount+use_mileage),0) as sales_cancel from paymentinfo where backoffice_no=?1 and cancel_state='C' and trunc(payment_date)=trunc(current_date)-1) as pre_sales_cancel "
			+ " from dual")
	public SalesSettlementDetailViewEntity select_pay_day_sum(String backoffice_no);

	// 주
	@Query(nativeQuery = true, value = "select  "
			+ "(select ( "
			+ "(select NVL(sum(p.payment_total),0) as sales_total from paymentinfo p, roominfo rm where p.room_no=rm.room_no and p.payment_state='T' and (((rm.room_type='desk') or (rm.room_type like 'meeting%')) and p.backoffice_no=?1  and trunc(payment_date) between trunc(current_date) - interval '7' day and trunc(current_date) )) +  "
			+ "(select NVL(sum(p.payment_total*0.8),0) as sales_total from paymentinfo p, roominfo rm where p.room_no=rm.room_no and p.payment_state='F' and p.sales_state='T' and ((rm.room_type='desk') or (rm.room_type like 'meeting%')and p.backoffice_no=?1 and trunc(payment_date) between trunc(current_date) - interval '7' day and trunc(current_date))) +  "
			+ "(select NVL(sum(payment_total*0.2),0) as sales_total from paymentinfo p, roominfo rm where p.room_no=rm.room_no and p.payment_state='F' and ((rm.room_type='desk') or (rm.room_type like 'meeting%'))and p.backoffice_no=?1 and trunc(payment_date) between trunc(current_date) - interval '7' day and trunc(current_date)) +  "
			+ "(select NVL(sum(payment_total),0) as sales_total from paymentinfo p, roominfo rm where p.room_no=rm.room_no and rm.room_type='office' and p.backoffice_no=?1 and payment_state='T' and trunc(payment_date) between trunc(current_date) - interval '7' day and trunc(current_date)) ) sales_total from dual) as sales_total,  "
			+ "(select NVL(sum(cancel_amount+use_mileage),0) as sales_cancel from paymentinfo where backoffice_no=?1 and cancel_state='C' and trunc(payment_date) between trunc(current_date) - interval '7' day and trunc(current_date)) as sales_cancel,  "
			+ "(select ( "
			+ "(select NVL(sum(p.payment_total),0) as sales_total from paymentinfo p left outer join roominfo rm on p.room_no=rm.room_no where p.payment_state='T' and (((rm.room_type='desk') or (rm.room_type like 'meeting%')) and p.backoffice_no=?1 and trunc(payment_date) between trunc(current_date) - interval '14' day and trunc(current_date) - interval '7' day) ) +  "
			+ "(select NVL(sum(p.payment_total*0.8),0) as sales_total from paymentinfo p left outer join roominfo rm on p.room_no=rm.room_no where p.payment_state='F' and p.sales_state='T' and ((rm.room_type='desk') or (rm.room_type like 'meeting%') and p.backoffice_no=?1 and trunc(payment_date) between trunc(current_date) - interval '14' day and trunc(current_date) - interval '7' day) )+  "
			+ "(select NVL(sum(payment_total*0.2),0) as sales_total from paymentinfo p left outer join roominfo rm on p.room_no=rm.room_no where p.payment_state='F' and ((rm.room_type='desk') or (rm.room_type like 'meeting%'))and p.backoffice_no=?1 and trunc(payment_date) between trunc(current_date) - interval '14' day and trunc(current_date) - interval '7' day ) +  "
			+ "(select NVL(sum(payment_total),0) as sales_total from paymentinfo p left outer join roominfo rm on p.room_no=rm.room_no where rm.room_type='office' and p.backoffice_no=?1 and payment_state='T' and trunc(payment_date) between trunc(current_date) - interval '14' day and trunc(current_date) - interval '7' day ) ) pre_sales_total from dual) as pre_sales_total,  "
			+ "(select NVL(sum(cancel_amount+use_mileage),0) as sales_cancel from paymentinfo where backoffice_no=?1 and cancel_state='C' and trunc(payment_date) between trunc(current_date) - interval '14' day and trunc(current_date) - interval '7' day) as pre_sales_cancel  "
			+ "from dual")
	public SalesSettlementDetailViewEntity select_pay_week_sum(String backoffice_no);
	
	// 월
	@Query(nativeQuery = true, value = "select  "
			+ "(select ( "
			+ "(select NVL(sum(p.payment_total),0) as sales_total from paymentinfo p, roominfo rm where p.room_no=rm.room_no and p.payment_state='T' and (((rm.room_type='desk') or (rm.room_type like 'meeting%')) and p.backoffice_no=?1  and trunc(payment_date) between add_months(trunc(current_date),-1) and trunc(current_date) )) +  "
			+ "(select NVL(sum(p.payment_total*0.8),0) as sales_total from paymentinfo p, roominfo rm where p.room_no=rm.room_no and p.payment_state='F' and p.sales_state='T' and ((rm.room_type='desk') or (rm.room_type like 'meeting%')and p.backoffice_no=?1 and trunc(payment_date) between add_months(trunc(current_date),-1) and trunc(current_date))) +  "
			+ "(select NVL(sum(payment_total*0.2),0) as sales_total from paymentinfo p, roominfo rm where p.room_no=rm.room_no and p.payment_state='F' and ((rm.room_type='desk') or (rm.room_type like 'meeting%'))and p.backoffice_no=?1 and trunc(payment_date) between add_months(trunc(current_date),-1) and trunc(current_date)) +  "
			+ "(select NVL(sum(payment_total),0) as sales_total from paymentinfo p, roominfo rm where p.room_no=rm.room_no and rm.room_type='office' and p.backoffice_no=?1 and payment_state='T' and trunc(payment_date) between add_months(trunc(current_date),-1) and trunc(current_date)) ) sales_total from dual) as sales_total,  "
			+ "(select NVL(sum(cancel_amount+use_mileage),0) as sales_cancel from paymentinfo where backoffice_no=?1 and cancel_state='C' and trunc(payment_date) between add_months(trunc(current_date),-1) and trunc(current_date)) as sales_cancel,  "
			+ "(select ( "
			+ "(select NVL(sum(p.payment_total),0) as sales_total from paymentinfo p left outer join roominfo rm on p.room_no=rm.room_no where p.payment_state='T' and (((rm.room_type='desk') or (rm.room_type like 'meeting%')) and p.backoffice_no=?1 and trunc(payment_date) between add_months(trunc(current_date),-2) and add_months(trunc(current_date),-1)) ) +  "
			+ "(select NVL(sum(p.payment_total*0.8),0) as sales_total from paymentinfo p left outer join roominfo rm on p.room_no=rm.room_no where p.payment_state='F' and p.sales_state='T' and ((rm.room_type='desk') or (rm.room_type like 'meeting%') and p.backoffice_no=?1 and trunc(payment_date) between add_months(trunc(current_date),-2) and add_months(trunc(current_date),-1)) )+  "
			+ "(select NVL(sum(payment_total*0.2),0) as sales_total from paymentinfo p left outer join roominfo rm on p.room_no=rm.room_no where p.payment_state='F' and ((rm.room_type='desk') or (rm.room_type like 'meeting%'))and p.backoffice_no=?1 and trunc(payment_date) between add_months(trunc(current_date),-2) and add_months(trunc(current_date),-1) ) +  "
			+ "(select NVL(sum(payment_total),0) as sales_total from paymentinfo p left outer join roominfo rm on p.room_no=rm.room_no where rm.room_type='office' and p.backoffice_no=?1 and payment_state='T' and trunc(payment_date) between add_months(trunc(current_date),-2) and add_months(trunc(current_date),-1) ) ) pre_sales_total from dual) as pre_sales_total,  "
			+ "(select NVL(sum(cancel_amount+use_mileage),0) as sales_cancel from paymentinfo where backoffice_no=?1 and cancel_state='C' and trunc(payment_date) between add_months(trunc(current_date),-2) and add_months(trunc(current_date),-1)) as pre_sales_cancel  "
			+ "from dual")
	public SalesSettlementDetailViewEntity select_pay_month_sum(String backoffice_no);
	

}
