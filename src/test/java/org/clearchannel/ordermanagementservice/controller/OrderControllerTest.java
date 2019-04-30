package org.clearchannel.ordermanagementservice.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.clearchannel.ordermanagementservice.TestApp;
import org.clearchannel.ordermanagementservice.dao.OrderRepository;
import org.clearchannel.ordermanagementservice.entity.Cart;
import org.clearchannel.ordermanagementservice.entity.Customer;
import org.clearchannel.ordermanagementservice.entity.Order;
import org.clearchannel.ordermanagementservice.entity.OrderStatus;
import org.clearchannel.ordermanagementservice.entity.Product;
import org.clearchannel.ordermanagementservice.request.CartRequest;
import org.clearchannel.ordermanagementservice.service.CartServiceImpl;
import org.clearchannel.ordermanagementservice.service.CheckoutServiceImpl;
import org.clearchannel.ordermanagementservice.service.ICartService;
import org.clearchannel.ordermanagementservice.service.ICheckoutService;
import org.clearchannel.ordermanagementservice.service.ICustomerService;
import org.clearchannel.ordermanagementservice.service.IOrderService;
import org.clearchannel.ordermanagementservice.service.IProductService;
import org.hamcrest.collection.IsMapContaining;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=TestApp.class)
public class OrderControllerTest {

	MockMvc mockMvc;
	@MockBean
	IOrderService orderServiceMock;
	@Autowired
	protected WebApplicationContext wac;
	@Autowired
	OrderController orderController;
	@Autowired
	ObjectMapper objectMapper;
	@MockBean
	CheckoutServiceImpl checkoutServiceImpl;
//	@Autowired
//	IOrderService orderService;
	

	/*@Autowired
	OrderRepository orderRepository;

	@Autowired
	ICustomerService customerService;

	@Autowired
	ICheckoutService checkoutService;

	@Autowired
	CartServiceImpl cartServiceImpl;
	
	@Autowired
	IProductService productService;
	
	@Autowired
	ICartService cartService;*/
	
	@Before
	public void setup() throws Exception {

		this.mockMvc = MockMvcBuilders.standaloneSetup(this.orderController).build();// Standalone context

	}
	
	
	@Test
	public void whenGetAllOrdersForACustomerIsCalledThenReturnListOfOrders() throws Exception {
		
		List<Order> orderList=new ArrayList<>();
		
		
		Product item1=Mockito.mock(Product.class);
		Product item2=Mockito.mock(Product.class);
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date=LocalDate.now();
		String dateCreated = date.format(pattern);
	
		
		Map<Product,Integer> productMap=new HashMap<>();
		productMap.put(item1, 10);
		productMap.put(item2, 20);
		Cart cart=new Cart(1, productMap);
		Customer customer= new Customer("Saurabh", "Chudleigh court",cart);
		Order order=new Order(OrderStatus.COMPLETED, customer);
		order.setOrderDetails(customer);
		
		orderList.add(order);
		
		objectMapper = objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

		when(orderServiceMock.getAllOrdersForCustomer(1)).thenReturn(orderList);

		mockMvc.perform(get("/oms/api/v1/orders/customer/1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].dateCreated", is(dateCreated)))
				.andExpect(jsonPath("$[0].orderDetails.address", is("Chudleigh court")));
		
			

	
		
	}
	
	
	@Test
	public void whenGetOrderByIdIsCalledThenReturnOrder() throws Exception {
		
		Product item1=Mockito.mock(Product.class);
		Product item2=Mockito.mock(Product.class);
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date=LocalDate.now();
		String dateCreated = date.format(pattern);
		
		
		Map<Product,Integer> productMap=new HashMap<>();
		productMap.put(item1, 10);
		productMap.put(item2, 20);
		Cart cart=new Cart(1, productMap);
		Customer customer= new Customer("Saurabh", "Chudleigh court",cart);
		Order order=new Order(OrderStatus.COMPLETED, customer);
		order.setOrderDetails(customer);
		UUID uuid=new UUID(1L, 2L);
		when(orderServiceMock.getOrderById(uuid)).thenReturn(order);
		
		objectMapper = objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

		mockMvc.perform(get("/oms/api/v1/orders/"+uuid)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.dateCreated", is(dateCreated)))
				.andExpect(jsonPath("$.orderDetails.address", is("Chudleigh court")));
			

	}
	
	

	@Test
	public void whenCreateOrderIsCalledThenReturnOrder() throws Exception {
		
		Product item1 = new Product.ProductBuilder("Item1", 10).description("Health Product").quantity(100).build();
	
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date=LocalDate.now();
		String dateCreated = date.format(pattern);
		
		CartRequest cartRequest=new CartRequest(1, item1, 10);
		Map<Product,Integer> productMap=new HashMap<>();
		productMap.put(item1, 10);
		Cart cart=new Cart(1, productMap);
		Customer customer= new Customer("Saurabh", "Chudleigh court",cart);
		Order order=new Order(OrderStatus.COMPLETED, customer);
		order.setOrderDetails(customer);
		UUID uuid=new UUID(1L, 2L);
		when(orderServiceMock.createOrder(cartRequest)).thenReturn(order);
		
		objectMapper = objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

		mockMvc.perform(post("/oms/api/v1/orders").contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsBytes(cartRequest))).andExpect(status().isCreated())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.dateCreated", is(dateCreated)))
				.andExpect(jsonPath("$.orderDetails.address", is("Chudleigh court")));
			
		

	}
	
}
