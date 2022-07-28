package com.br.compass.config.error;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.br.compass.dto.ExceptionResponseDto;
import com.br.compass.exception.ObjectNotFoundException;

@SpringBootTest
class ErrorHandlerTest {
	
	@InjectMocks
	private ErrorHandler errorHandler;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void whenObjectNotFoundExceptionThrowThenReturnNotFound() {
		ResponseEntity<ExceptionResponseDto> response = errorHandler.exceptionObjectNotFound(new ObjectNotFoundException("Product not found"));
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ExceptionResponseDto.class, response.getBody().getClass());
		assertEquals("Product not found", response.getBody().getMessage());
	}
}
