package com.example.demo.dto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.model.UnitOfMeasure;

@Component
public class StringToUomConverters implements Converter<String, UnitOfMeasure> {
	
	
	public StringToUomConverters() {}
	
	@Override
	public UnitOfMeasure convert(String uomStr) {
		System.out.println( "convert source: "+uomStr+" to uom");
		UnitOfMeasure uom = null;
		try {
			 uom = UnitOfMeasure.valueOf(uomStr);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return uom;
	}

}
