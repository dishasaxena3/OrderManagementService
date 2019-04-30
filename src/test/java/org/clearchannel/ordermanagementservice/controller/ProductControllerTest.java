package org.clearchannel.ordermanagementservice.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.Optional;

import org.clearchannel.ordermanagementservice.TestApp;
import org.clearchannel.ordermanagementservice.entity.Product;
import org.clearchannel.ordermanagementservice.service.IProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=TestApp.class)
public class ProductControllerTest {

	MockMvc mockMvc;
	@MockBean
	IProductService productServiceMock;
	@Autowired
	protected WebApplicationContext wac;
	@Autowired
	ProductController productController;
	@Autowired
	ObjectMapper objectMapper;

	@Before
	public void setup() throws Exception {

		this.mockMvc = MockMvcBuilders.standaloneSetup(this.productController).build();// Standalone context

	}

	@Test
	public void whenGetAllProductsIsCalledThenReturnListOfProducts() throws Exception {

		Product item1 = new Product.ProductBuilder("Item1", 10).description("Health Product").quantity(1).build();
		Product item2 = new Product.ProductBuilder("Item2", 20).description("Beauty Product").quantity(1).build();

		objectMapper = objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

		when(productServiceMock.getAllProducts()).thenReturn(Arrays.asList(item1, item2));

		mockMvc.perform(get("/oms/api/v1/products")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].name", is("Item1"))).andExpect(jsonPath("$[0].price", is(10.0)))
				.andExpect(jsonPath("$[0].quantity", is(1)))
				.andExpect(jsonPath("$[0].description", is("Health Product")))
				.andExpect(jsonPath("$[1].name", is("Item2"))).andExpect(jsonPath("$[1].price", is(20.0)))
				.andExpect(jsonPath("$[1].quantity", is(1)))
				.andExpect(jsonPath("$[1].description", is("Beauty Product")));

		verify(productServiceMock, times(1)).getAllProducts();
		verifyNoMoreInteractions(productServiceMock);

	}

	@Test
	public void whenAddProductIsCalledThenReturnAddedProduct() throws Exception {

		Product item1 = new Product.ProductBuilder("Item1", 10).description("Health Product").quantity(1).build();

		objectMapper = objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

		when(productServiceMock.save(item1)).thenReturn(item1);

		mockMvc.perform(post("/oms/api/v1/products").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(item1))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.name", is("Item1"))).andExpect(jsonPath("$.price", is(10.0)))
				.andExpect(jsonPath("$.quantity", is(1))).andExpect(jsonPath("$.description", is("Health Product")));

		verify(productServiceMock, times(1)).save(item1);
		verifyNoMoreInteractions(productServiceMock);

	}

	@Test(expected = Exception.class)
	public void whenGETProductIsCalledThenThrowExceptionAsProductDoesNotExist() throws Exception {

		Product item1 = new Product.ProductBuilder("Item1", 10).description("Health Product").quantity(1).build();

		objectMapper = objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

		mockMvc.perform(get("/oms/api/v1/products/1"));

		verify(productServiceMock, times(1)).getProduct(Mockito.anyLong());
		verifyNoMoreInteractions(productServiceMock);

	}

	@Test
	public void whenGETProductIsCalledThenReturnProduct() throws Exception {

		Product item1 = new Product.ProductBuilder("Item1", 10).description("Health Product").quantity(1).build();

		objectMapper = objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

		when(productServiceMock.getProduct(Mockito.anyLong())).thenReturn(Optional.of(item1));

		mockMvc.perform(get("/oms/api/v1/products/1")).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.name", is("Item1"))).andExpect(jsonPath("$.price", is(10.0)))
				.andExpect(jsonPath("$.quantity", is(1))).andExpect(jsonPath("$.description", is("Health Product")));

		verify(productServiceMock, times(1)).getProduct(Mockito.anyLong());
		verifyNoMoreInteractions(productServiceMock);

	}

	@Test
	public void whenUpdateProductIsCalledThenReturnUpdatedProduct() throws Exception {

		Product item1 = new Product.ProductBuilder("Item1", 10).description("Health Product").quantity(10).build();
		Product item2 = new Product.ProductBuilder("Item1", 10).description("Health Product").quantity(20).build();
		objectMapper = objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		when(productServiceMock.getProduct(Mockito.anyLong())).thenReturn(Optional.of(item1));
		when(productServiceMock.updateProduct(item1)).thenReturn(item1);

		mockMvc.perform(put("/oms/api/v1/products/update/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(item2))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("Item1"))).andExpect(jsonPath("$.price", is(10.0)))
				.andExpect(jsonPath("$.quantity", is(20))).andExpect(jsonPath("$.description", is("Health Product")));

		verify(productServiceMock, times(1)).updateProduct(item1);

	}

	@Test
	public void whenDeleteProductByIdIsCalledThenReturnNothing() throws Exception {

		objectMapper = objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		Product item1 = new Product.ProductBuilder("Item1", 10).description("Health Product").quantity(10).build();
		when(productServiceMock.getProduct(Mockito.anyLong())).thenReturn(Optional.of(item1));

		Mockito.doNothing().when(productServiceMock).deleteProductById(Mockito.anyLong());

		mockMvc.perform(delete("/oms/api/v1/products/delete/1")).andExpect(status().isOk());

		verify(productServiceMock, times(1)).deleteProductById(Mockito.anyLong());

	}

	@Test
	public void whenDeleteAllProductIsCalledThenReturnNothing() throws Exception {

		objectMapper = objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		Product item1 = new Product.ProductBuilder("Item1", 10).description("Health Product").quantity(10).build();

		Mockito.doNothing().when(productServiceMock).deleteAllProduct();

		mockMvc.perform(delete("/oms/api/v1/products/deleteAll")).andExpect(status().isOk());

		verify(productServiceMock, times(1)).deleteAllProduct();

	}

}
