package com.rbv.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.rbv.springdemo.entity.Customer;


@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer> getCustomers() {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Customer> theQuery =
				currentSession.createQuery("from Customer order by lastName", 
							Customer.class);
		
		List<Customer> customers = theQuery.getResultList();
		
		
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		
		Session currSession = sessionFactory.getCurrentSession();
		
		
		currSession.merge(theCustomer);
		
	}

	@Override
	public Customer getCustomer(int theId) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Customer theCustomer= currentSession.get(Customer.class, theId);
		
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Customer theCustomer= currentSession.get(Customer.class, theId);
		
		currentSession.remove(theCustomer);
	}

}
