package com.walker.lock.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author walker
 * @date 2018/12/19
 */
@Data
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Long commentCount;

    /**
     * jpa 乐观锁：版本号机制
     */
    @Version
    private Long version;
}
