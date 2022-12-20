/**
 * 
 * @author 최진실
 *
 */
package com.rence.dashboard.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesSettlementDetailViewDTO implements Serializable{

	private String sales_income;
	
	private String sales_total;
	
	private String sales_cancel;
	
	private String pre_sales_income;
	
	private String pre_sales_total;
	
	private String pre_sales_cancel;
	
	private String sales_gap;
}
