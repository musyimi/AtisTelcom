package com.atis.customer;


import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.atis.common.entity.AuthenticationType;
import com.atis.common.entity.Country;
import com.atis.common.entity.Customer;
import com.atis.common.exception.CustomerNotFoundException;
import com.atis.setting.CountryRepository;

import net.bytebuddy.utility.RandomString;



@Service
@Transactional
public class CustomerService {
	
	public static final int CUSTOMERS_PER_PAGE = 5;
	
	@Autowired
	private CountryRepository countryRepo;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<Country> listAllCountries() {
		return countryRepo.findAllByOrderByNameAsc();
	}
	
	public boolean isEmailUnique(String email) {
		Customer customer = customerRepo.findByEmail(email);
		return customer == null;
	}
	
	public void registerCustomer(Customer customer) {
		encodePassword(customer);
		customer.setEnabled(false);
		customer.setCreatedTime(new Date());
		
		String randomCode = RandomString.make(64);
		customer.setVerificationCode(randomCode);
		
		customerRepo.save(customer);
		
	}
	
	public Customer getCustomerByEmail(String email) {
		return customerRepo.findByEmail(email);
	}
	
	public void encodePassword(Customer customer) {
		String encodedPassword = passwordEncoder.encode(customer.getPassword());
		customer.setPassword(encodedPassword);
	}
	
	public boolean verify(String verificationCode) {
		Customer customer = customerRepo.findByVerificationCode(verificationCode);
		
		if (customer == null || customer.isEnabled()) {
			return false;
		}else {
			customerRepo.enable(customer.getId());
			return true;
		}
	}
	

   public void updateAuthenticationType(Customer customer, AuthenticationType type) {
	   if (!customer.getAuthenticationType().equals(type)) {
		   customerRepo.updateAthenticationType(customer.getId(), type);
	   }
   }
   
	public void addNewCustomerUponOAuthLogin(String name, String email, String countryCode) {
		Customer customer = new Customer();
		customer.setEmail(email);
		
		setName(name, customer);
		
		customer.setEnabled(true);
		customer.setCreatedTime(new Date());
		customer.setAuthenticationType(AuthenticationType.GOOGLE);
		customer.setPassword("");
		customer.setAddressLine1("");
		customer.setCity("");
		customer.setCounty("");
		customer.setPhoneNumber("");
		customer.setPostalCode("");
		customer.setCountry(countryRepo.findByCode(countryCode));
		
		customerRepo.save(customer);
	}
	
	private void setName(String name, Customer customer) {
		String[] nameArray = name.split("");
		if (nameArray.length < 2) {
			customer.setFirstName(name);
			customer.setLastName("");
		} else {
			String firstName = nameArray[0];
			customer.setFirstName(firstName);
			
			String lastName = name.replaceFirst(firstName, "");
			customer.setLastName(lastName);
		}
	}

}
