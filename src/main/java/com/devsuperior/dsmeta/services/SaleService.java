package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SalesSumDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SalesSumProjection;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {
	
	LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
	LocalDate resultData = today.minusYears(1L);

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}
	
	public Page<SaleMinDTO> findAllReport(String name, String minDate, String maxDate, Pageable pageable) {
		
		System.out.println(minDate + maxDate);
		if (minDate.isEmpty()) {			
			minDate = resultData.toString();			
			}
		if (maxDate.isEmpty()) {
			maxDate = today.toString();			
			}
		System.out.println(minDate + maxDate);
		
		if (name.isEmpty()) {
			Page<Sale> result = repository.search1(minDate, maxDate, pageable);
			return result.map(x -> new SaleMinDTO(x));
			}
		else {
			Page<Sale> result = repository.search3(name, minDate, maxDate, pageable);
			return result.map(x -> new SaleMinDTO(x));
		}	  
	}
	
	public List<SalesSumDTO> findAll1(String minDate, String maxDate) {
				
		if (minDate.isEmpty()) {			
			minDate = resultData.toString();			
			}
		if (maxDate.isEmpty()) {
			maxDate = today.toString();			
			}
		
		List<SalesSumProjection> list = repository.search2(minDate, maxDate);
		List<SalesSumDTO> result = list.stream().map(x -> new SalesSumDTO(x)).collect(Collectors.toList());
		return result;
	  
	  }
}
