package com.br.compass.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.br.compass.dto.ProductDto;
import com.br.compass.dto.ProductForm;
import com.br.compass.model.Product;
import com.br.compass.service.ProductServiceImplements;

@SpringBootTest
class ProductControllerTest {
	
	private static final Long ID = 1L;
	private static final Double PRICE = 2700.0;
	private static final String DESCRIPTION = "ele voa";
	private static final String NAME = "Noteboot s145 lenovo";

	private Pageable pageable;
	
	@Mock
	private UriComponentsBuilder uriBuilder;

	@Mock
	private ProductServiceImplements service;
	
	@InjectMocks
	private ProductController controller;

	private Product product;
	private ProductDto productDto;
	private Optional<Product> productOpt;
	private ProductForm productForm;
	private Page<ProductDto> productPage;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	//findById
	
	@Test
	public void whenFindByIdThenReturnSuccess() {
		when(service.findById(anyLong())).thenReturn(productDto);
		
		ResponseEntity<ProductDto> response = controller.findById(ID);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ProductDto.class, response.getBody().getClass());
		assertEquals(productDto.getId(), response.getBody().getId());
		assertEquals(productDto.getName(), response.getBody().getName());
		assertEquals(productDto.getDescription(), response.getBody().getDescription());
		assertEquals(productDto.getPrice(), response.getBody().getPrice());
	}
	
	//findAll
	
	@Test
	public void whenFindAllThenReturnSuccess() {
		when(service.findAll(pageable)).thenReturn(productPage);
		
		ResponseEntity<Page<ProductDto>> responsePage = controller.list(pageable);
		ProductDto responseObj = responsePage.getBody().getContent().get(0);
		
		assertNotNull(responsePage);
		assertNotNull(responsePage.getBody());
		assertEquals(HttpStatus.OK, responsePage.getStatusCode());
		assertEquals(ResponseEntity.class, responsePage.getClass());
		assertEquals(PageImpl.class, responsePage.getBody().getClass());
		assertEquals(ProductDto.class, responseObj.getClass());
		assertEquals(productDto.getId(), responseObj.getId());
		assertEquals(productDto.getName(), responseObj.getName());
		assertEquals(productDto.getDescription(), responseObj.getDescription());
		assertEquals(productDto.getPrice(), responseObj.getPrice());
	}
	
	@Test
	public void whenCreateThenReturnCreated() {
		when(service.saveProduct(any(), any())).thenReturn(productDto);
		
		ResponseEntity<ProductDto> response = controller.create(productForm, uriBuilder);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ProductDto.class, response.getBody().getClass());
		assertEquals(productDto.getId(), response.getBody().getId());
		assertEquals(productDto.getName(), response.getBody().getName());
		assertEquals(productDto.getDescription(), response.getBody().getDescription());
		assertEquals(productDto.getPrice(), response.getBody().getPrice());
	}
	
	@Test
	public void whenUpdateThenReturnSuccess() {
		when(service.updateProduct(any(), anyLong())).thenReturn(productDto);
		
		ResponseEntity<ProductDto> response = controller.update(productForm, ID);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ProductDto.class, response.getBody().getClass());
		assertEquals(productDto.getId(), response.getBody().getId());
		assertEquals(productDto.getName(), response.getBody().getName());
		assertEquals(productDto.getDescription(), response.getBody().getDescription());
		assertEquals(productDto.getPrice(), response.getBody().getPrice());
	}
	
	@Test
	public void deleteByIdWithSuccess() {
		when(service.deleteById(anyLong())).thenReturn(productDto);
		
		ResponseEntity<ProductDto> response = controller.delete(ID);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		verify(service, times(1)).deleteById(anyLong());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ProductDto.class, response.getBody().getClass());
		assertEquals(productDto.getId(), response.getBody().getId());
		assertEquals(productDto.getName(), response.getBody().getName());
		assertEquals(productDto.getDescription(), response.getBody().getDescription());
		assertEquals(productDto.getPrice(), response.getBody().getPrice());	
	}
	
	@Test
	public void whenSearchThenReturnSuccess() {
		when(service.search(anyDouble(), anyDouble(), anyString(), any())).thenReturn(productPage);

		ResponseEntity<Page<ProductDto>> responsePage = controller.search(10.0, 2800.0, "lenovo", pageable);
		ProductDto responseObj = controller.search(10.0, 2800.0, "lenovo", pageable).getBody().getContent().get(0);
		
		assertNotNull(responsePage);
		assertNotNull(responsePage.getBody());
		assertEquals(HttpStatus.OK, responsePage.getStatusCode());
		assertEquals(ResponseEntity.class, responsePage.getClass());
		assertEquals(PageImpl.class, responsePage.getBody().getClass());
		assertEquals(ProductDto.class, responseObj.getClass());
		assertEquals(productDto.getId(), responseObj.getId());
		assertEquals(productDto.getName(), responseObj.getName());
		assertEquals(productDto.getDescription(), responseObj.getDescription());
		assertEquals(productDto.getPrice(), responseObj.getPrice());
	}
	
	private void startUser() {
		this.product = new Product(NAME, DESCRIPTION, PRICE);
		product.setId(ID);
		this.productDto = new ProductDto(ID, NAME, DESCRIPTION, PRICE);
		this.productForm = new ProductForm(NAME, DESCRIPTION, PRICE);
		this.productPage = new PageImpl<ProductDto>(List.of(productDto));
		this.uriBuilder = UriComponentsBuilder.newInstance();
	}
}
