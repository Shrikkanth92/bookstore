package com.bookstore.service;

import java.util.Set;

import com.bookstore.domain.User;
import com.bookstore.domain.UserBilling;
import com.bookstore.domain.UserPayment;
import com.bookstore.domain.UserShipping;
import com.bookstore.domain.security.UserRole;

public interface UserService {
	User createUser(User user, Set<UserRole> userRoles);

	User findByUsername(String username);

	User findByEmail(String userEmail);
	
	User save(User user);
	
	User findById(Long userId);
	
	void updateUserPaymentInfo(UserShipping userShipping, UserBilling billing, UserPayment payment, User user);
	
	void updateUserBilling(UserBilling billing, UserPayment payment, User user);

	void setUserDefaultPayment(Long userPaymentId, User user);

	void updateUserShipping(UserShipping shipping, User user);

	void setUserDefaultShipping(long userShippingId, User user);
	
}
