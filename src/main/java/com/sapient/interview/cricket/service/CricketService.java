package com.sapient.interview.cricket.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.sapient.interview.cricket.model.MatchInfo;
import com.sapient.interview.cricket.model.Response;

@Service
public class CricketService {
	
	public Response getMatchDetails(String unique_id) throws ResponseStatusException {

		   String url = "https://cricapi.com/api/cricketScore?unique_id="+unique_id;
		   HttpHeaders headers = new HttpHeaders();
		   headers.set("apiKey", "WmPJrX2s3KMyZVPFwlm1vxXLXKw1");
		   HttpEntity<String> entity = new HttpEntity<String>(headers);
		   
		   RestTemplate restTempalte = new RestTemplate();
	       ResponseEntity<MatchInfo> responseEntity = restTempalte.exchange(url, HttpMethod.GET, entity, MatchInfo.class);

		  


		   Response responseModel = getResponse(responseEntity);


		   if (responseEntity.getStatusCode().is2xxSuccessful()) {
			   return responseModel;
			   }else {
			   throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No details Found");
			   }
	   }
	
	private Response getResponse(ResponseEntity response){
		MatchInfo matchInfo = (MatchInfo) response.getBody();
		String stat = matchInfo.getStat();
		
		String teamName =getWinningTeamByScore(matchInfo.getScore());
		 
		String winningTeam=teamName.replaceAll("[^a-zA-Z]", " ");
	
		String winnerTag = " (winner)";

		String team1 = matchInfo.getTeam1();
		String team2 = matchInfo.getTeam2();
		
		

		if (team1.trim().equals(winningTeam.trim())) {
			team1 = matchInfo.getTeam1() + winnerTag;
		} else if (team2.trim().equals(winningTeam.trim())) {
			team2 = matchInfo.getTeam2() + winnerTag;
		}
		
		Matcher m = Pattern.compile("^*\\d*/\\d*").matcher(teamName);
		
		
		
		String winningTeamScore = null;
		while (m.find()) {
			winningTeamScore=m.group(0);
		}

		
		Response responseModel = new Response();

		responseModel.setTeam1(team1);
		responseModel.setTeam2(team2);
		responseModel.setWinningTeamSScore(winningTeamScore);
		responseModel.setRoundRotation(getRotatedValue(winningTeamScore));

		return responseModel;

	}
	
	private String getRotatedValue(String winningTeamScore2) {
		Matcher m = Pattern.compile("^*\\d*/\\d*").matcher(winningTeamScore2);
		String winningTeamScore=null;
		while (m.find()) {
			winningTeamScore=m.group(0);
		}
		String[] str=winningTeamScore.split("/");
		String test=str[1]+str[0]+"/";
		return test;
	}

	
	
	private String getWinningTeamByScore(String score) {
		String[] replacedSpace=score.split("v");
		String winningTeamName=null;
		for (String teamName : replacedSpace) {
			if(teamName.contains("*")) {
				winningTeamName=teamName;
			}
		}
		
		return winningTeamName;
	}

	
	
	/*
	 * public static void main(String args[]) {
	 * 
	 * String score="140/8";
	 * 
	 * System.out.println(test);
	 * 
	 * }
	 */

}
