package com.lavanya.api.repository;

import com.lavanya.api.model.Childcare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository extending JPA repository for persistence of Childcare object.
 * @author lavanya
 */
@Repository
public interface ChildcareRepository extends JpaRepository<Childcare, Integer> {
}
