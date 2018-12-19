package com.walker.lock.repository;

import com.walker.lock.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author walker
 * @date 2018/12/19
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
