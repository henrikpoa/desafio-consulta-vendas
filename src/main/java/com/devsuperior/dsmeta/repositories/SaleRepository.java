package com.devsuperior.dsmeta.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SalesSumProjection;

public interface SaleRepository extends JpaRepository<Sale, Long> {	
	
	  @Query(nativeQuery = true, value = "SELECT * "
		  + "FROM TB_SALES "		  
		  + "WHERE TB_SALES.DATE BETWEEN :minDate AND :maxDate")
	  Page<Sale> search1(String minDate, String maxDate, Pageable pageable);
	  
	  @Query(nativeQuery = true, value = "SELECT * "
			  + "FROM TB_SALES "
			  + "INNER JOIN TB_SELLER ON TB_SELLER.ID = TB_SALES.SELLER_ID " 
			  + "WHERE UPPER(TB_SELLER.NAME) LIKE UPPER(CONCAT('%', :name, '%')) "
			  + "AND TB_SALES.DATE BETWEEN :minDate AND :maxDate ")
		  Page<Sale> search3(String name, String minDate, String maxDate, Pageable pageable);
	  
	  @Query(nativeQuery = true, value = "SELECT TB_SELLER.NAME, SUM(TB_SALES.AMOUNT) AS TOTAL " 
		  + "FROM TB_SALES " 
		  + "INNER JOIN TB_SELLER ON TB_SELLER.ID = TB_SALES.SELLER_ID " 
		  + "WHERE TB_SALES.DATE BETWEEN :minDate AND :maxDate "
		  + "GROUP BY TB_SELLER.NAME")
	  List<SalesSumProjection> search2(String minDate, String maxDate);	 
}
