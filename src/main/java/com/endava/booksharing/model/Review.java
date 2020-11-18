package com.endava.booksharing.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;

@Entity
@Data
@Table(name = "t_review")
public class Review {

    @Id
    @SequenceGenerator(name = "review_id_generator", sequenceName = "seq_reviews", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_id_generator")
    private Long id;

    @Column(name = "text_review")
    private String textReview;

    @OneToOne
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
