package org.clearchannel.ordermanagementservice.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.clearchannel.ordermanagementservice.TestApp;
import org.clearchannel.ordermanagementservice.entity.Cart;
import org.clearchannel.ordermanagementservice.entity.Customer;
import org.clearchannel.ordermanagementservice.entity.Order;
import org.clearchannel.ordermanagementservice.entity.OrderStatus;
import org.clearchannel.ordermanagementservice.entity.Product;
import org.clearchannel.ordermanagementservice.request.CheckoutRequest;
import org.clearchannel.ordermanagementservice.service.ICheckoutService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=TestApp.class)
public class CheckoutControllerTest {

	MockMvc mockMvc;
	@MockBean
	ICheckoutService checkoutServiceMock;
	@Autowired
	protected WebApplicationContext wac;
	@Autowired
	CheckoutController checkoutController;
	@Autowired
	ObjectMapper objectMapper;

	@Before
	public void setup() throws Exception {

		this.mockMvc = MockMvcBuilders.standaloneSetup(this.checkoutController).build();// Standalone context

	}
	
	
	@Test
	public void whenPerformCheckoutIsCalledThenReturnOrder() throws JsonProcessingException, Exception{
		Product item1=Mockito.mock(Product.class);
		Product item2=Mockito.mock(Product.class);
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date=LocalDate.now();
		String string = date.format(pattern);
		
		LocalDate dateParsed = LocalDate.parse(string, pattern);
		Map<Product,Integer> productMap=new HashMap<>();
		productMap.put(item1, 10);
		productMap.put(item2, 20);
		
		
		Order order=new Order(OrderStatus.COMPLETED, new Customer("Saurabh", "Chudleigh court", new Cart(1,productMap)));
		CheckoutRequest checkoutRequest=new CheckoutRequest();
		checkoutRequest.setCustomerId(1);
		Mockito.when(checkoutServiceMock.performCheckout(1)).thenReturn(order);
		
		objectMapper = objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		
		mockMvc.perform(post("/oms/api/v1/checkout").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(checkoutRequest)))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.dateCreated", is(string)));
				
				
				
				
		verify(checkoutServiceMock, times(1)).performCheckout(1);
		verifyNoMoreInteractions(checkoutServiceMock);

		
	}
	
	
	
	
}
