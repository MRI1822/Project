package com.example.demo;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/*@Data
class awayTeam{
	@JsonProperty("ID")
	String ID;
	@JsonProperty("City")
	String City;
	@JsonProperty("Name")
	String Name;
	@JsonProperty("Abbreviation")
	String Abbreviation;
}

@Data
class homeTeam{
	@JsonProperty("ID")
	String ID;
	@JsonProperty("City")
	String City;
	@JsonProperty("Name")
	String Name;
	@JsonProperty("Abbreviation")
	String Abbreviation;
}*/

@Data
class gameentry{
	String date;
	String time;
	awayTeam awayTeam;
	homeTeam homeTeam;	
}

@Data
class dailygameschedule{
	String lastUpdatedOn;
	@JsonProperty("gameentry")
	ArrayList<gameentry> gameentries;
}

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class dailyS {
	dailygameschedule dailygameschedule;
	
}
