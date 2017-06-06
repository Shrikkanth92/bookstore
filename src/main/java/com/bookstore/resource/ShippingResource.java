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
import com.bookstore.domain.UserShipping;
import com.bookstore.service.UserService;
import com.bookstore.service.UserShippingService;

@RestController
@RequestMapping("/shipping")
public class ShippingResource {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserShippingService userShippingService;
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	private ResponseEntity addNewUserShippingPost(
			@RequestBody UserShipping shipping, Principal principal){
		
		User user = userService.findByUsername(principal.getName());
			
		userService.updateUserShipping(shipping, user);
		
		return new ResponseEntity("Shipping added Successfully!", HttpStatus.OK);
		 
	}
	
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	private ResponseEntity removeUserShippingPost(
			@RequestBody String id, Principal principal){
	
		userShippingService.removeById(Long.parseLong(id));
		
		return new ResponseEntity("Shipping Removed Successfully!", HttpStatus.OK); 
		
	}
	
	@RequestMapping(value="/setDefault", method=RequestMethod.POST)
	private ResponseEntity setDefaultShippingPost(
			@RequestBody String id, Principal principal){
		User user = userService.findByUsername(principal.getName());
		
		userService.setUserDefaultShipping(Long.parseLong(id), user);
		
		return new ResponseEntity("Shipping details default set Successfully!", HttpStatus.OK); 
		
	}
	
	
	@RequestMapping(value="/getUserShippingList")
	private List<UserShipping> getUserShippingList(Principal principal){
		
		User user = userService.findByUsername(principal.getName());
		
		List<UserShipping> userShippingList = user.getUserShippingList();
			
		return userShippingList; 
		
	}
	
}
