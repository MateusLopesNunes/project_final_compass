package com.br.compass;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.br.compass.repository.ProductRepository;
import com.br.compass.service.ProductServiceImplements;

@SpringBootTest
class CrudProductsCompassApplicationTests {

	@Autowired
	private ProductRepository productRepository;
	
	@Test
	void contextLoads() {
		
	}

}
