package com.example.demo.model;

public enum UnitOfMeasure {

	SELECT("SELECT"),
	TEASPOON("TEASPOON"),
	TABLESPOON("TABLESPOON"),
	CUP("CUP"),
	PINCH("PINCH"),
	EACH("EACH"),
	DASH("DASH"),
	LITER("LITER"),
	MILLILITER("MILLILITER"),
	DECILITER("DECILITER"),
	GRAIN("GRAIN"),
    OUNCE("OUNCE"),
	POUND("POUND"),
	GRAM("GRAM"),
	MILLIGRAM("MILLIGRAM"),
	KILO("KILO"),
	PINT("PINT");
	
	private final String unit;
	
	private UnitOfMeasure(String unit) {
	    this.unit = unit;
	}
 
	  public String getValue() {
	        return unit;
	    }
}
