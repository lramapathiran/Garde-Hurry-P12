package com.lavanya.api.repository;

import com.lavanya.api.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository extending JPA repository for persistence of Comment object.
 * @author lavanya
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer>{
}
