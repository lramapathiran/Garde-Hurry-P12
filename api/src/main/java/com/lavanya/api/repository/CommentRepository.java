package com.lavanya.api.repository;

import com.lavanya.api.model.Comment;
import com.lavanya.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository extending JPA repository for persistence of Comment object.
 * @author lavanya
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer>{

    /**
     * Query to retrieve the list of comments made on a particular user and ordered by date descendant.
     * @param user of interest whom we wish to retrieve the list of comments made on.
     * @return List of Comment.
     */
    List<Comment> findByUserCommentedOrderByTimeDesc(User user);
}
