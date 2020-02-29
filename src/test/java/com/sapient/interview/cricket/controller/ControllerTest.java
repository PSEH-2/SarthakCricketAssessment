package com.sapient.interview.cricket.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.sapient.interview.cricket.exceptionalhandling.ResourceNotFoundException;
import com.sapient.interview.cricket.model.Response;
import com.sapient.interview.cricket.service.CricketService;

@RunWith (SpringJUnit4ClassRunner.class)
public class ControllerTest {
	
	 private MockMvc mockMvc;


	    @Mock
	    CricketService cricketServices;

	    @InjectMocks
	    CricketController cricketController;

	    @Before
	     public void setUp()throws  Exception {
	        MockitoAnnotations.initMocks(this);
	    }
	    
	    @Test
	    public void getMatchDetailsSuccess() throws ResourceNotFoundException {
	        Response responseModel = new Response( "team1:India",  "team2: SriLanka", "146/6", "6146/");
	        Mockito.when(cricketServices.getMatchDetails(Mockito.any(String.class))).thenReturn(responseModel);

	        Response resultModel =cricketController.getMatchDetails("12345");

	        Assert.assertNotNull(resultModel);

	    }

}
