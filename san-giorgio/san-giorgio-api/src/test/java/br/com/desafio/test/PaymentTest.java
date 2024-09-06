package br.com.desafio.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.desafio.dto.Payment;
import br.com.desafio.dto.PaymentItem;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PaymentTest {

    @Autowired
	private MockMvc mockMvc;
    
    
    @Autowired
    private ObjectMapper objectMapper;
    
   	@Test
	public void pagamentoOkTest() throws Exception {
		
   		Payment request = new Payment();
   		request.setClientId("2");
   		
   		List<PaymentItem> paymentItems = new ArrayList<PaymentItem>();
   		
   		PaymentItem paymentItem1 = new PaymentItem();
   		paymentItem1.setPaymentId("111");
   		paymentItem1.setPaymentValue(new BigDecimal(50));
   		paymentItems.add(paymentItem1);
   		
   		PaymentItem paymentItem2 = new PaymentItem();
   		paymentItem2.setPaymentId("222");
   		paymentItem2.setPaymentValue(new BigDecimal(200));
   		paymentItems.add(paymentItem2);
   		
   		PaymentItem paymentItem3 = new PaymentItem();
   		paymentItem3.setPaymentId("333");
   		paymentItem3.setPaymentValue(new BigDecimal(400));
   		paymentItems.add(paymentItem3);
   		
   		request.setPaymentItems(paymentItems);
   		
		MvcResult result = mockMvc
				.perform(put("/api/payment")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
	            .andExpect(status().isOk()).andReturn();
		
		System.out.println("Status Code: " + result.getResponse().getStatus());
		System.out.println("Body (json): ");
		System.out.println(result.getResponse().getContentAsString());
	}
   	
   	@Test
   	public void clientInexistenteTest() throws Exception {
		
   		Payment request = new Payment();
   		request.setClientId("000");
   		
   		List<PaymentItem> paymentItems = new ArrayList<PaymentItem>();
   		
   		PaymentItem paymentItem1 = new PaymentItem();
   		paymentItem1.setPaymentId("111");
   		paymentItem1.setPaymentValue(new BigDecimal(50));
   		paymentItems.add(paymentItem1);
   		
   		PaymentItem paymentItem2 = new PaymentItem();
   		paymentItem2.setPaymentId("222");
   		paymentItem2.setPaymentValue(new BigDecimal(200));
   		paymentItems.add(paymentItem2);
   		
   		PaymentItem paymentItem3 = new PaymentItem();
   		paymentItem3.setPaymentId("333");
   		paymentItem3.setPaymentValue(new BigDecimal(400));
   		paymentItems.add(paymentItem3);
   		
   		request.setPaymentItems(paymentItems);
   		
		MvcResult result = mockMvc
				.perform(put("/api/payment")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
	            .andExpect(status().isNotFound()).andReturn();
		
		System.out.println("Status Code: " + result.getResponse().getStatus());
		System.out.println("Body (json): ");
		System.out.println(result.getResponse().getContentAsString());
	}
   	
   	@Test
   	public void pagamentoInexistenteTest() throws Exception {
		
   		Payment request = new Payment();
   		request.setClientId("2");
   		
   		List<PaymentItem> paymentItems = new ArrayList<PaymentItem>();
   		
   		PaymentItem paymentItem1 = new PaymentItem();
   		paymentItem1.setPaymentId("1110");
   		paymentItem1.setPaymentValue(new BigDecimal(50));
   		paymentItems.add(paymentItem1);
   		
   		PaymentItem paymentItem2 = new PaymentItem();
   		paymentItem2.setPaymentId("2220");
   		paymentItem2.setPaymentValue(new BigDecimal(200));
   		paymentItems.add(paymentItem2);
   		
   		PaymentItem paymentItem3 = new PaymentItem();
   		paymentItem3.setPaymentId("3330");
   		paymentItem3.setPaymentValue(new BigDecimal(400));
   		paymentItems.add(paymentItem3);
   		
   		request.setPaymentItems(paymentItems);
   		
		MvcResult result = mockMvc
				.perform(put("/api/payment")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
	            .andExpect(status().isNotFound()).andReturn();
		
		System.out.println("Status Code: " + result.getResponse().getStatus());
		System.out.println("Body (json): ");
		System.out.println(result.getResponse().getContentAsString());
	}
	
	
	
	
	
	

}
