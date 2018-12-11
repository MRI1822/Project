package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MainController {
	

	@GetMapping("/")
    public String renderIndex(){

       /* User activeUser = userRepository.findByUserId(activeUserId);
        model.addAttribute("userName", activeUser.getUserName());
        List <Team> favoriteTeams = new ArrayList <Team>();
        if (activeUser.getFavoriteTeams() != null) {
            for (int teamId : activeUser.getFavoriteTeams()) {
                favoriteTeams.add(teamRepository.findByTeamId(teamId));
            }
            model.addAttribute("activeUserFavoriteTeams", favoriteTeams);
        } else {
            model.addAttribute("activeUserFavoriteTeams", null);
        }*/
        return "index";
    }

	
	//Using PoJo Classes
	@GetMapping("/teams")
	public ModelAndView getTeams() {
		ModelAndView showTeams = new ModelAndView("showTeams");
		showTeams.addObject("name", "Human"); 
		
		//Endpoint to call
		String url ="https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/overall_team_standings.json";
		//Encode Username and Password
        String encoding = Base64.getEncoder().encodeToString("cdffbb43-1abe-4a55-9b72-bd15d3:scholes".getBytes());
        // TOKEN:PASS
        //Add headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic "+encoding);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		//Make the call
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<NBATeamStanding> response = restTemplate.exchange(url, HttpMethod.GET, request, NBATeamStanding.class);
		NBATeamStanding ts = response.getBody(); 
        System.out.println(ts.toString());
		//Send the object to view
        showTeams.addObject("teamStandingEntries", ts.getOverallteamstandings().getTeamstandingsentries());
        
		return showTeams;
	}

	//Using PoJo Classes
	@GetMapping("/today")
	public ModelAndView getDailyS() {
		ModelAndView showDailyS = new ModelAndView("showDailyS");
		showDailyS.addObject("name", "Human"); 
		
		//Endpoint to call
		String url ="https://api.mysportsfeeds.com/v1.1/pull/nba/2018-2019-regular/daily_game_schedule.json?fordate=20181124";
		//Encode Username and Password
        String encoding = Base64.getEncoder().encodeToString("cdffbb43-1abe-4a55-9b72-bd15d3:scholes".getBytes());
        // TOKEN:PASS
        //Add headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic "+encoding);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		//Make the call
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<dailyS> response = restTemplate.exchange(url, HttpMethod.GET, request, dailyS.class);
		dailyS ts = response.getBody(); 
        System.out.println(ts.toString());
		//Send the object to view
        showDailyS.addObject("gameentries", ts.getDailygameschedule().getGameentries());
        
		return showDailyS;
	}

	//Using PoJo Classes
	@GetMapping("/scores")
	public ModelAndView getScores() {
		ModelAndView showScores = new ModelAndView("showScores");
		showScores.addObject("name", "Human"); 
		
		//Endpoint to call
		String url ="https://api.mysportsfeeds.com/v1.1/pull/nba/2018-2019-regular/scoreboard.json?fordate=20181104";
		//Encode Username and Password
        String encoding = Base64.getEncoder().encodeToString("cdffbb43-1abe-4a55-9b72-bd15d3:scholes".getBytes());
        // TOKEN:PASS
        //Add headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic "+encoding);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		//Make the call
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<scorecard> response = restTemplate.exchange(url, HttpMethod.GET, request, scorecard.class);
		scorecard ts = response.getBody(); 
        System.out.println(ts.toString());
		//Send the object to view
        showScores.addObject("gameScores", ts.getScoreboard().getGameScores());
        
		return showScores;
	}
	
	
	//Using objectMapper
	@GetMapping("/team")
	public ModelAndView getTeamInfo(
			@RequestParam("id") String teamID 
			) {
		ModelAndView teamInfo = new ModelAndView("teamInfo");
		ArrayList<HashMap<String, String>> gameDetails = new ArrayList<HashMap<String, String>>();
		String url = "https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/team_gamelogs.json?team=" + teamID;
		String encoding = Base64.getEncoder().encodeToString("cdffbb43-1abe-4a55-9b72-bd15d3:scholes".getBytes());
        
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic "+encoding);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		String str = response.getBody(); 
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode root = mapper.readTree(str);
			System.out.println(str);
			//JsonNode jsonNode1 = actualObj.get("lastUpdatedOn");
	        System.out.println(root.get("teamgamelogs").get("lastUpdatedOn").asText());
	        System.out.println(root.get("teamgamelogs").get("gamelogs").getNodeType());
	        JsonNode gamelogs = root.get("teamgamelogs").get("gamelogs");
	        
	        if(gamelogs.isArray()) {
	        	
	        	gamelogs.forEach(gamelog -> {
	        		JsonNode game = gamelog.get("game");
	        		HashMap<String,String> gameDetail = new HashMap<String, String>();
	        		gameDetail.put("id", game.get("id").asText());
	        		gameDetail.put("date", game.get("date").asText());
	        		gameDetail.put("time", game.get("time").asText());
	        		gameDetail.put("awayTeam", game.get("awayTeam").get("Abbreviation").asText());
					gameDetail.put("homeTeam", game.get("homeTeam").get("Abbreviation").asText());
	        		gameDetails.add(gameDetail);
	        		
	        	});
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		teamInfo.addObject("gameDetails", gameDetails);
		
        
		return teamInfo;
	}
}

