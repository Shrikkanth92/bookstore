package com.bookstore.resource;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.domain.Book;
import com.bookstore.domain.CartItem;
import com.bookstore.domain.ShoppingCart;
import com.bookstore.domain.User;
import com.bookstore.service.BookService;
import com.bookstore.service.CartItemService;
import com.bookstore.service.ShoppingCartService;
import com.bookstore.service.UserService;

@RestController
@RequestMapping("/cart")
public class ShoppingCartResource {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BookService bookService;
	 
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ShoppingCartService cartService;
	
	@RequestMapping("/add")
	public ResponseEntity addItem(@RequestBody HashMap<String, String> mapper, Principal principal) {
		
		String bookId = mapper.get("bookId");
		String qty = mapper.get("qty");
		
		User user = userService.findByUsername(principal.getName());
		Book book = bookService.findOne(Long.parseLong(bookId));
		
		if(Integer.parseInt(qty) > book.getInStockNumber()){
			return new ResponseEntity("Not enough Stock!", HttpStatus.BAD_REQUEST);
	}
		CartItem cartItem = cartItemService.addBookToCartItem(book, user, Integer.parseInt(qty));
		
		return new ResponseEntity("Book added successfully!", HttpStatus.OK); 
	}
	
	@RequestMapping("/getCartItemList")
	public List<CartItem> getCartItemList(Principal principal){
		User user = userService.findByUsername(principal.getName());
		ShoppingCart cart = user.getShoppingCart();
		
		List<CartItem> cartItems = cartItemService.findByShoppingCart(cart);
		
		cartService.updateShoppingCart(cart);
		
		return cartItems;
	}
	
	@RequestMapping("/getShoppingCart")
	public ShoppingCart getShoppingCart(Principal principal){
		User user = userService.findByUsername(principal.getName());
		
		ShoppingCart cart  = user.getShoppingCart();
		
		cartService.updateShoppingCart(cart);
		
		return cart;
	}
	
	@RequestMapping("/removeItem")
	public ResponseEntity removeItem(@RequestBody String id){
		cartItemService.removeCartItem(cartItemService.findById(Long.parseLong(id)));
		
		return new ResponseEntity("Book deleted successfully!", HttpStatus.OK);
	}
	
	@RequestMapping("/updateCartItem")
	public ResponseEntity updateCartItem(@RequestBody HashMap<String, String> mapper){
		String cartItemId = mapper.get("cartItemId");
		String qty = mapper.get("qty");
		
		CartItem cartItem  = cartItemService.findById(Long.parseLong(cartItemId));
		cartItem.setQty(Integer.parseInt(qty));
		
		cartItemService.updateItem(cartItem);
		
		return new ResponseEntity("Book updated successfully!", HttpStatus.OK);
	}
}
