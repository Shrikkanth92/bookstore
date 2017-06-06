package com.bookstore.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.domain.UserPayment;
import com.bookstore.repository.UserPaymentRepository;
import com.bookstore.service.UserPaymentService;

@Service
public class UserPaymentServiceImpl implements UserPaymentService{
	
	@Autowired
	private UserPaymentRepository userPaymentRepository;

	@Override
	public UserPayment findById(Long id) {
		// TODO Auto-generated method stub
		return userPaymentRepository.findOne(id);
	}

	@Override
	public void removeById(Long id) {
		// TODO Auto-generated method stub
		userPaymentRepository.delete(id);
	}

}
