package com.rence.office.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rence.office.model.PaymentInfoEntity;

public interface OfficePaymentInfoRepository extends JpaRepository<PaymentInfoEntity, Object>{
	
	
	@Query(nativeQuery = true,
			value = "select * from payment_view where reserve_no=?1 and rownum=1"
			)
	public PaymentInfoEntity select_one_final_payment_info(String reserve_no);

}
