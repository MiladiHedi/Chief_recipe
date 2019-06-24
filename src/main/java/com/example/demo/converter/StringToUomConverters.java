package com.example.demo.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.entities.UnitOfMeasure;

@Component
public class StringToUomConverters implements Converter<String, UnitOfMeasure> {
	
	
	private static final Logger log = LoggerFactory.getLogger(StringToUomConverters.class);

	public StringToUomConverters() {}
	
	@Override
	public UnitOfMeasure convert(String uomStr) {
		log.debug( "convert source: "+uomStr+" to uom");
		UnitOfMeasure uom = null;
		try {
			 uom = UnitOfMeasure.valueOf(uomStr);
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return uom;
	}

}
