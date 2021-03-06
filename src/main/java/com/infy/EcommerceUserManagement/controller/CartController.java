package com.infy.EcommerceUserManagement.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.EcommerceUserManagement.exception.UserException;
import com.infy.EcommerceUserManagement.model.Cart;
import com.infy.EcommerceUserManagement.service.CartService;

@RestController
public class CartController {

	@Autowired
	CartService cartService;

	@Autowired
	Environment environment;

	private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

	@PostMapping(value = "api/cart/add")
	public ResponseEntity<String> addToCart(@RequestBody Cart cart) throws UserException {

		ResponseEntity<String> responseEntity = null;

		try {

			cartService.addToCart(cart);
			String successMessage = environment.getProperty("cart.ADD_SUCCESS");
			responseEntity = new ResponseEntity<String>(successMessage, HttpStatus.OK);

		} catch (Exception exception) {
			LOGGER.error("Error: " + exception.getMessage(), exception);

			String errorMessage;

			if (exception.getMessage() == null) {
				errorMessage = environment.getProperty("General.EXCEPTION");
				responseEntity = new ResponseEntity<String>(errorMessage, HttpStatus.OK);
				return responseEntity;

			} else {
				errorMessage = environment.getProperty(exception.getMessage());

				if (errorMessage == null)
					errorMessage = environment.getProperty("General.EXCEPTION");
				responseEntity = new ResponseEntity<String>(errorMessage, HttpStatus.OK);
				return responseEntity;

			}
		}

		return responseEntity;

	}

	@DeleteMapping(value = "api/cart/remove")
	public ResponseEntity<String> removeFromCart(@RequestBody Cart cart) {
		ResponseEntity<String> responseEntity = null;

		try {

			cartService.removeFromCart(cart);
			String successMessage = environment.getProperty("cart.REMOVE_SUCCESS");
			responseEntity = new ResponseEntity<String>(successMessage, HttpStatus.OK);

		} catch (Exception exception) {
			LOGGER.error("Error: " + exception.getMessage(), exception);

			String errorMessage;

			if (exception.getMessage() == null) {
				errorMessage = environment.getProperty("General.EXCEPTION");
				responseEntity = new ResponseEntity<String>(errorMessage, HttpStatus.OK);
				return responseEntity;

			} else {
				errorMessage = environment.getProperty(exception.getMessage());

				if (errorMessage == null)
					errorMessage = environment.getProperty("General.EXCEPTION");
				responseEntity = new ResponseEntity<String>(errorMessage, HttpStatus.OK);
				return responseEntity;

			}
		}

		return responseEntity;

	}

	@GetMapping(value = "api/cart")
	public List<Cart> getAllCartItem(@RequestParam(name = "buyerId") int buyerId) {
		return cartService.getAllCartItem(buyerId);

	}

	@GetMapping(value = "api/cart/checkout")
	public List<Cart> checkOutFromCart(@RequestParam(name = "buyerId") int buyerId) {
		return cartService.checkOutFromCart(buyerId);

	}

}
