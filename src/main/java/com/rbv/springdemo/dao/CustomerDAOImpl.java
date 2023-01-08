package com.rbv.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.rbv.springdemo.entity.Customer;
import com.rbv.springdemo.util.SortUtils;


@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer> getCustomers(int theSortField) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		String thefieldName = null;
		
		switch (theSortField) {
		case SortUtils.FIRST_NAME: {
			
			thefieldName = "firstName" ;
			break;
		}
		case SortUtils.LAST_NAME: {
			thefieldName = "lastName" ;
			break;
		}
		case SortUtils.EMAIL:{
			thefieldName = "email" ;
			break;
		}
		default:
			thefieldName = "lastName";
		}
		
		String queryString = "from Customer order by " + thefieldName;
		
		Query<Customer> theQuery =
				currentSession.createQuery(queryString, 
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

	@Override
	public List<Customer> searchCustomers(String theSearchString) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Customer> theQuery = null;
		
		if (theSearchString !=null &&
				theSearchString.trim().length() > 0) {
			theQuery = currentSession.createQuery("from Customer where lower(firstName) like :theString or "
					+ " lower(lastName) like :theString order by lastName", Customer.class);
			
			theQuery.setParameter("theString", "%" +
						theSearchString.toLowerCase() +"%");
		}
		else {
			theQuery = currentSession.createQuery("from Customer order by lastName", 
							Customer.class);
		}
		List<Customer> customers = theQuery.getResultList();
		
		
		return customers;
	}

}
