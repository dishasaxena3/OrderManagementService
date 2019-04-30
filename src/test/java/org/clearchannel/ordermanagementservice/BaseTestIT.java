package org.clearchannel.ordermanagementservice;
import org.clearchannel.ordermanagementservice.controller.ProductController;
import org.clearchannel.ordermanagementservice.service.CartServiceImpl;
import org.clearchannel.ordermanagementservice.service.CustomerServiceImpl;
import org.clearchannel.ordermanagementservice.service.IProductService;
import org.clearchannel.ordermanagementservice.service.ProductServiceImpl;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=TestApp.class)
public abstract class BaseTestIT {

	@Autowired
	protected  ProductServiceImpl productServiceImpl;
	
	@Autowired
	protected CartServiceImpl cartServiceImpl;
	
	@Autowired
	protected CustomerServiceImpl customerServiceImpl;
	

	
	
	
	
}
