package uk.co.jakestanley;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import lombok.extern.log4j.Log4j;
import uk.co.jakestanley.controllers.BetController;
import uk.co.jakestanley.services.CashierService;
import uk.co.jakestanley.services.CashierService;
import uk.co.jakestanley.services.RouletteService;
import uk.co.jakestanley.services.RouletteService;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes=BetControllerTest.WebConfig.class)
public class BetControllerTest {
	
    @Configuration
    @EnableWebMvc
    static class WebConfig extends WebMvcConfigurerAdapter {
        @Bean
        public BetController betController() {
            return new BetController();
        }
        @Bean
        public CashierService cashierService() {
            return new CashierService();
        }
        @Bean
        public RouletteService rouletteService() {
        	return new RouletteService();
        }
    }
    
    private MediaType expectedMediaType;
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;
    
    @Autowired
    private CashierService cashierService;
    
    @Autowired
    private RouletteService rouletteService;
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.expectedMediaType = 
        		MediaType.parseMediaType("application/json;charset=UTF-8");
    }
    
    @Test
    public void winningBetTest() throws Exception {
    	
    	String amount = "10";
    	String winnings = "360";
    	
    	// RandomUtils.nextInt is 0 inclusive, argument exclusive
    	int winningBet = RandomUtils.nextInt(37);
    	
    	// make sure the roulette spin returns the winning bet for this test
    	RouletteService rouletteServiceSpy = Mockito.spy(rouletteService);
    	when(rouletteServiceSpy.spin()).thenReturn(winningBet);
//    	when(rouletteService.spin()).thenReturn(winningBet);

        MvcResult result = this.mockMvc.perform(post("/bet/pocket"
        		+ "?amount=" + amount + "&number=" + winningBet)
            .accept(expectedMediaType))
            .andExpect(status().isOk())
            .andExpect(content().contentType(expectedMediaType))
            .andReturn();
        
        String content = result.getResponse().getContentAsString();
        assertEquals("You won £" + winnings, content);
    }
    
    @Test
    public void losingBetTest() throws Exception {
    	
    	int amount = 10;
    	
    	int losingBet = 9;
    	int spinResult = 14;
    	
    	// make sure the roulette spin doesn't return 9
    	RouletteService rouletteServiceSpy = Mockito.spy(rouletteService);
    	Mockito.when(rouletteServiceSpy.spin()).thenReturn(spinResult);
//    	when(rouletteService.spin()).thenReturn(spinResult);
    	
    	this.mockMvc.perform(post("/bet/pocket"
        		+ "?amount=" + amount
        		+ "&number=" + losingBet)
    		.accept(expectedMediaType))
    	.andExpect(status().isOk());
    	
    }
    
    @Test
    public void badBetTest() throws Exception {
    	
    	int amount = 10;
    	
    	int[] badBets = {37, -1, 99};
    	
    	String requestUrl;
    	
    	for (int i = 0; i < badBets.length; i++) {
			int badBet = badBets[i];
			
	    	requestUrl = "/bet/pocket?amount=" + amount + "&number=" + badBet;
	    	
	    	log.info("Testing with bet: " + badBet);
	    	
	    	this.mockMvc.perform(post(requestUrl)
	    			.accept(expectedMediaType))
	    	.andExpect(status().isBadRequest());
		}
    }
    
    @Test
    public void badAmountTest() throws Exception {
    	
    }
}
