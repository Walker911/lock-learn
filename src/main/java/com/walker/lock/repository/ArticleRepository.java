package com.walker.lock.repository;

import com.walker.lock.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.Optional;

/**
 * @author walker
 * @date 2018/12/19
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {

    /**
     * 悲观锁
     * select for update: 行锁，其他线程读写需等待，其它数据不影响
     * 查询条件必须是索引列，若不是索引列就会变成表锁，把整个表都锁住
     *
     * @param id article id
     * @return article
     */
    @Query(value = "select * from article where id = :id for update", nativeQuery = true)
    Optional<Article> findArticleForUpdate(Long id);

    /**
     * Pessimistic lock
     * nativeQuery = true: Illegal attempt to set lock mode on a native SQL query
     * jpa 查询时添加 for update
     * select article0_.id as id1_0_, article0_.comment_count as comment_2_0_,
     * article0_.title as title3_0_ from article article0_ where article0_.id=?
     * for update
     *
     * @param id article id
     * @return article
     */
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select a from Article a where a.id = :id")
    Optional<Article> findArticleWithPessimisticLock(Long id);

    /**
     * 乐观锁：版本号机制
     * update article with version
     *
     * @param id 评论的文章id
     * @param commentCount 评论数量
     * @param version 版本号
     * @return
     */
    @Modifying
    @Query(value = "update article set comment_count = :commentCount, version = version + 1 where id = :id " +
            "and version = :version", nativeQuery = true)
    int updateArticleWithVersion(Long id, Long commentCount, Long version);
}
