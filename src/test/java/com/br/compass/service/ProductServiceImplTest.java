package com.br.compass.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;

import com.br.compass.dto.ProductDto;
import com.br.compass.dto.ProductForm;
import com.br.compass.exception.ObjectNotFoundException;
import com.br.compass.model.Product;
import com.br.compass.repository.ProductRepository;

@SpringBootTest
public class ProductServiceImplTest {

	private static final Long ID = 1L;
	private static final Double PRICE = 2700.0;
	private static final String DESCRIPTION = "ele voa";
	private static final String NAME = "Noteboot s145 lenovo";

	@Mock
	private ProductRepository productRepository;

	private Pageable pageable;
	private UriComponentsBuilder uriBuilder;

	@InjectMocks
	private ProductServiceImplements productService;

	private Product product;
	private ProductDto productDto;
	private Optional<Product> productOpt;
	private ProductForm productForm;
	private Page<Product> productPage;

	@BeforeEach
	public void instantiate() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	// findById

	@Test
	public void whenFindByIdThenReturnAnProductDtoInstance() {
		// quando o método findById for chamado mock seu parametro com qualquer numero
		// do tipo long e retorne um ProductOpt.
		when(productRepository.findById(Mockito.anyLong())).thenReturn(productOpt);

		ProductDto response = productService.findById(ID);

		assertNotNull(response);
		assertEquals(ProductDto.class, response.getClass());
		assertEquals(productDto.getId(), response.getId());
		assertEquals(productDto.getDescription(), response.getDescription());
		assertEquals(productDto.getPrice(), response.getPrice());
		assertEquals(productDto.getName(), response.getName());
	}

	@Test
	public void whenFindByIdThenReturnAnObjectNotFoundException() {
		when(productRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object not found"));

		try {
			productService.findById(ID);
		} catch (Exception exception) {
			assertEquals(ObjectNotFoundException.class, exception.getClass());
			assertEquals("Object not found", exception.getMessage());
		}
	}

	// findAll

	@Test
	public void whenFindAllThenReturnAnListOfUser() {
		when(productRepository.findAll(pageable)).thenReturn(productPage);

		Page<ProductDto> productPage = productService.findAll(pageable);
		ProductDto productObj = productPage.getContent().get(0);

		assertEquals(1L, productPage.getContent().size());
		assertNotNull(productPage);
		assertEquals(ProductDto.class, productObj.getClass());
		assertEquals(ID, productObj.getId());
		assertEquals(NAME, productObj.getName());
		assertEquals(DESCRIPTION, productObj.getDescription());
		assertEquals(PRICE, productObj.getPrice());

	}

	// save

	@Test
	public void whenSaveThenReturnAnProductDtoInstance() {
		when(productRepository.save(any())).thenReturn(product);

		ProductDto response = productService.saveProduct(productForm, uriBuilder);

		assertNotNull(response);
		assertEquals(ProductDto.class, response.getClass());
		assertEquals(productDto.getId(), response.getId());
		assertEquals(productDto.getName(), response.getName());
		assertEquals(productDto.getDescription(), response.getDescription());
		assertEquals(productDto.getPrice(), response.getPrice());
	}

	// update

	@Test
	public void whenUpdateThenReturnAnProductDtoInstance() {
		when(productRepository.findById(Mockito.anyLong())).thenReturn(productOpt);
		when(productRepository.save(any())).thenReturn(product);

		ProductDto response = productService.updateProduct(productForm, ID);

		assertNotNull(response);
		assertEquals(ProductDto.class, response.getClass());
		assertEquals(productDto.getId(), response.getId());
		assertEquals(productDto.getName(), response.getName());
		assertEquals(productDto.getDescription(), response.getDescription());
		assertEquals(productDto.getPrice(), response.getPrice());
	}
	
	// delete

	@Test
	public void deleteByIdWithSuccess() {
		when(productRepository.findById(anyLong())).thenReturn(productOpt);
		doNothing().when(productRepository).deleteById(ID);

		productRepository.deleteById(ID);
		// verifica se o deleteById foi chamado e quantas vezes foi chamado
		verify(productRepository, times(1)).deleteById(anyLong());
	}

	@Test
	public void deleteByIdWithfailed() {
		when(productRepository.findById(anyLong())).thenReturn(productOpt);
		doNothing().when(productRepository).deleteById(ID);

		try {
			productRepository.deleteById(1L);
		} catch (Exception exception) {
			assertEquals(ObjectNotFoundException.class, exception.getClass());
			assertEquals("Object not found", exception.getMessage());
		}
	}

	@Test
	public void whenSearchThenReturnAnListOfUser() {
		//when(productRepository.findByName(anyDouble(), anyDouble(), anyString(), any())).thenReturn(productPage);

		Page<ProductDto> products = productService.search(10.0, 2800.0, "lenovo", pageable);
		ProductDto product = productService.search(10.0, 2800.0, "lenovo", pageable).getContent().get(0);

		assertEquals(1L, products.getContent().size());
		assertNotNull(products);
		assertEquals(product.getClass(), product.getClass());
		assertEquals(ID, product.getId());
		assertEquals(NAME, product.getName());
		assertEquals(DESCRIPTION, product.getDescription());
		assertEquals(PRICE, product.getPrice());
	}

	private void startUser() {
		/*this.product = new Product(NAME, DESCRIPTION, PRICE);
		product.setId(ID);
		this.productDto = new ProductDto(ID, NAME, DESCRIPTION, PRICE);
		this.productOpt = Optional.of(product);
		this.productForm = new ProductForm(NAME, DESCRIPTION, PRICE);
		this.productPage = new PageImpl<>(List.of(product));*/
		
	}
}
