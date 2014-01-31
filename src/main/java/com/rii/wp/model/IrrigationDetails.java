/**
 * 
 */
package com.rii.wp.model;

import java.io.Serializable;
import java.text.ParseException;

import com.rii.wp.util.DateUtil;

/**
 * @author I
 *
 */
public class IrrigationDetails implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3842889500356948779L;
	
	private String date;
	private String waterAmount;
	private String operation;
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 * @throws ParseException 
	 */
	public void setDate(String date) throws ParseException {
		this.date = String.valueOf(DateUtil.getDayOfyear(date));
	}
	/**
	 * @return the waterAmount
	 */
	public String getWaterAmount() {
		return waterAmount;
	}
	/**
	 * @param waterAmount the waterAmount to set
	 */
	public void setWaterAmount(String waterAmount) {
		this.waterAmount = waterAmount;
	}
	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}
	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	

}
