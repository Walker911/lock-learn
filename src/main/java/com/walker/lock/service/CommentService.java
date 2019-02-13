package com.walker.lock.service;

import com.walker.lock.model.Article;
import com.walker.lock.model.Comment;
import com.walker.lock.repository.ArticleRepository;
import com.walker.lock.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author walker
 * @date 2018/12/19
 */
@Slf4j
@Service
public class CommentService {

    private final ArticleRepository articleRepository;

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(ArticleRepository articleRepository, CommentRepository commentRepository) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public void postComment(Long articleId, String content) {
        Optional<Article> articleOptional = articleRepository.findById(articleId);
        if (!articleOptional.isPresent()) {
            throw new RuntimeException("没有对应的文章");
        }
        Article article = articleOptional.get();

        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setContent(content);
        commentRepository.save(comment);

        // int retry = 0;
        // while(retry < 10) {
        //     retry++;
        //     int count = articleRepository.updateArticleWithVersion(article.getId(),
        //             article.getCommentCount() + 1, article.getVersion());
        //     if (count == 1) {
        //         return;
        //     }
        // }
        // throw exception: rollback data
        // throw new RuntimeException("服务器繁忙，更新数据失败");

        article.setCommentCount(article.getCommentCount() + 1);
        articleRepository.save(article);
    }
}
