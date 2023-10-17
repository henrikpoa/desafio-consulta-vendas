package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projections.SalesSumProjection;

public class SalesSumDTO {
	
	private String sellerName;
	private Double total;	
		
	
	public SalesSumDTO(SalesSumProjection projection) {		
		sellerName = projection.getName();
		total = projection.getTotal();
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String name) {
		this.sellerName = name;
	}

	public Double getTotal() {
		return total;
	}
}
