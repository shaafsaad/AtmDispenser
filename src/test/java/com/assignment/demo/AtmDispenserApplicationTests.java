package com.assignment.demo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.assignment.exception.ATMException;
import com.assignment.service.AuthService;
import com.assignment.service.WithdrawService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AtmDispenserApplicationTests {
	
	private MockMvc mockMvc;
	int[] test = {320,90,130,150,80,500};
	
	@MockBean
	AuthService authService;
	
	@Autowired
	WithdrawService withdrawService;
	
	@Autowired
    private WebApplicationContext wac;

	@Before
	public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        when(authService.isUserAuthorize()).thenReturn(true);

	}
	
	@Test
	public void withdraw() throws Exception {
		for (int i=0; i < test.length; i++) {
		mockMvc.perform(MockMvcRequestBuilders.put("/user/withdraw/" + test[i]).accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.success").value(Boolean.TRUE)).andDo(print());
		}
	}
	
	@Test
	public void invalidAmount10() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/user/withdraw/10").accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.success").value(Boolean.FALSE)).andDo(print());
	}
	
	@Test
	public void invalidAmount95() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/user/withdraw/95").accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.success").value(Boolean.FALSE)).andDo(print());
	}
	
	@Test
	public void maxWithdrawLimit() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/user/withdraw/550").accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.success").value(Boolean.FALSE)).andDo(print());
	}
	
	@Test
	public void invalidAmount() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/user/withdraw/0").accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.success").value(Boolean.FALSE)).andDo(print());
	}

}
