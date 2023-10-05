package com.vivekanandpv.springbootapijpafileiovalidation.repositories;

import com.vivekanandpv.springbootapijpafileiovalidation.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
