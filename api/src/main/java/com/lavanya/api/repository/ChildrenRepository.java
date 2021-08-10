package com.lavanya.api.repository;

import com.lavanya.api.model.Children;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository extending JPA repository for persistence of Children object.
 * @author lavanya
 */
@Repository
public interface ChildrenRepository extends JpaRepository<Children, Integer> {
}
