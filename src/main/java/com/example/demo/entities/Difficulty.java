package com.example.demo.entities;


public enum Difficulty {

	EASY("EASY"),
	MODERATE("MODERATE"),
	KIND_OF_HARD("KIND_OF_HARD"),
	HARD("HARD");

	private final String difficulty;
	
	private Difficulty(String difficulty) {
	    this.difficulty = difficulty;
	}
 
	  public String getDifficulty() {
	        return difficulty;
	    }

}
