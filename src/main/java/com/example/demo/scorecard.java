package com.example.demo;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

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
}	

@Data
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

//@JsonIgnoreProperties(ignoreUnknown = true, value = {"scheduleStatus", "originalDate",
//"originalTime", "delayedOrPostponedReason", "date", "time", "location"})
@Data
class game{
	@JsonProperty("ID")
	String ID;
	
	awayTeam awayTeam;
	homeTeam homeTeam;
}

//@JsonIgnoreProperties(ignoreUnknown = true, value = {"quarterSummary"})
@Data
class gameScore{
	game game;
	String isUnplayed;
	String isInProgress;
	String isCompleted;
	String awayScore;
	String homeScore;
}

@Data
class scoreboard{
	String lastUpdatedOn;
	@JsonProperty("gameScore")
	ArrayList<gameScore> gameScores;
}

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class scorecard {
	scoreboard scoreboard;
	
}
