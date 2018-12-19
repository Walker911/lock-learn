package com.walker.lock.web;

import com.walker.lock.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author walker
 * @date 2018/12/19
 */
@Slf4j
@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("comment")
    public String comment(Long articleId, String content) {
        try {
            commentService.postComment(articleId, content);
        } catch (Exception e) {
            log.error("error: ", e);
            return "error: " + e.getMessage();
        }
        return "success";
    }
}
