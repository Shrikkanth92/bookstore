package com.bookstore.resource;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.domain.User;
import com.bookstore.domain.UserBilling;
import com.bookstore.domain.UserPayment;
import com.bookstore.service.UserPaymentService;
import com.bookstore.service.UserService;


@RestController
@RequestMapping("/payment")
public class PaymentResource {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserPaymentService userPaymentService;
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	private ResponseEntity addNewCreditCardPost(
			@RequestBody UserPayment payment, Principal principal){
		
		User user = userService.findByUsername(principal.getName());
			
		UserBilling billing = payment.getUserBilling();
		
		userService.updateUserBilling(billing, payment, user);
		
		return new ResponseEntity("Credit Card Added Successfully!", HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	private ResponseEntity removeCreditCardPost(
			@RequestBody String id, Principal principal){
	
		userPaymentService.removeById(Long.parseLong(id));
		
		return new ResponseEntity("Credit Card Removed Successfully!", HttpStatus.OK); 
		
	}
	
	@RequestMapping(value="/setDefault", method=RequestMethod.POST)
	private ResponseEntity setDefaultPaymentPost(
			@RequestBody String id, Principal principal){
		User user = userService.findByUsername(principal.getName());
		
		userService.setUserDefaultPayment(Long.parseLong(id), user);
		
		return new ResponseEntity("Credit Card Removed Successfully!", HttpStatus.OK); 
		
	}
	
	
	@RequestMapping(value="/getUserPaymentList")
	private List<UserPayment> getUserPaymentList(Principal principal){
		
		User user = userService.findByUsername(principal.getName());
		
		List<UserPayment> userPayments = user.getUserPaymentList();
		
		return userPayments; 
		
	}
	
	
}