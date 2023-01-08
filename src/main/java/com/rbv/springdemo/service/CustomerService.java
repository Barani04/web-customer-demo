package com.rbv.springdemo.service;

import java.util.List;

import com.rbv.springdemo.entity.Customer;

public interface CustomerService {

		public List<Customer> getCustomers();

		public void saveCustomer(Customer theCustomer);

		public Customer getCustomer(int theId);

		public void deleteCustomer(int theId);
}
