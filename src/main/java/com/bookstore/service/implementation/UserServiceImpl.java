package com.bookstore.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.domain.ShoppingCart;
import com.bookstore.domain.User;
import com.bookstore.domain.UserBilling;
import com.bookstore.domain.UserPayment;
import com.bookstore.domain.UserShipping;
import com.bookstore.domain.security.UserRole;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserBillingRepository;
import com.bookstore.repository.UserPaymentRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.repository.UserShippingRepository;
import com.bookstore.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private  UserPaymentRepository userPaymentRepository;
	
	@Autowired
	private UserBillingRepository userBillingRepository;
	
	@Autowired
	private UserShippingRepository userShippingRepository;
	
	@Transactional
	public User createUser(User user, Set<UserRole> userRoles) {
		User localUser = userRepository.findByUsername(user.getUsername());
		if(null != localUser){
			LOG.info("User {} already exists", user.getUsername());
		} else {
			for(UserRole ur : user.getUserRoles()){
				if(roleRepository.findOne(ur.getUserRoleId()) != null){
					LOG.info("Role {} already exists", ur.getRole());
				}else{
				roleRepository.save(ur.getRole());
				}
			}
			
			user.getUserRoles().addAll(userRoles);
			
			ShoppingCart shoppingCart = new ShoppingCart();
			shoppingCart.setUser(user);
			
			user.setShoppingCart(shoppingCart);
			user.setUserPaymentList(new ArrayList<UserPayment>());
			user.setUserShippingList(new ArrayList<UserShipping>());
			
			localUser = userRepository.save(user);
		}
		return localUser;
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByEmail(String userEmail) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(userEmail);
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public User findById(Long userId) {
		// TODO Auto-generated method stub
		return userRepository.findOne(userId);
	}

	@Override
	public void updateUserPaymentInfo(UserShipping userShipping, UserBilling billing, UserPayment payment, User user) {
		save(user);
		userPaymentRepository.save(payment);
		userBillingRepository.save(billing);
	}

	@Override
	public void updateUserBilling(UserBilling billing, UserPayment payment, User user) {
		payment.setUser(user);
		payment.setUserBilling(billing);
		payment.setDefaultPayment(true);
		billing.setUserPayment(payment);
		user.getUserPaymentList().add(payment);
		save(user);
		
	}
	
	@Override
	public void setUserDefaultPayment(Long userPaymentId, User user) {
		List<UserPayment> userPaymentList = (List<UserPayment>) userPaymentRepository.findAll();
		
		for (UserPayment userPayment : userPaymentList) {
			if(userPayment.getId() == userPaymentId) {
				userPayment.setDefaultPayment(true);
				userPaymentRepository.save(userPayment);
			} else {
				userPayment.setDefaultPayment(false);
				userPaymentRepository.save(userPayment);
			}
		}
	}

	@Override
	public void updateUserShipping(UserShipping shipping, User user) {
		shipping.setUser(user);
		shipping.setUserShippingDefault(true);
		user.getUserShippingList().add(shipping);
		save(user);
	}

	@Override
	public void setUserDefaultShipping(long userShippingId, User user) {
		List<UserShipping> userShippingList = (List<UserShipping>) userShippingRepository.findAll();
		
		for (UserShipping userShipping : userShippingList) {
			if(userShipping.getId() == userShippingId) {
				userShipping.setUserShippingDefault(true);
				userShippingRepository.save(userShipping);
			} else {
				userShipping.setUserShippingDefault(false);
				userShippingRepository.save(userShipping);
			}
		}
		
	}
	
}
