package com.endava.booksharing.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@Table(name = "t_book")
public class Book {

    @Id
    @Column
    @SequenceGenerator(name = "book_id_generator", sequenceName = "seq_books", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_generator")
    private Long id;

    @Column
    @NotNull
    private String title;

    @Column
    @NotNull
    private int pages;

    @Column
    private String description;

    @Column(name = "added_at")
    private LocalDate addedAt;

    @Column(name = "book_language")
    private String bookLanguage;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @Column(name = "deleted_why")
    private String deletedWhy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "added_by")
    private User user;

    @OneToOne
    private User deletedBy;

    @ManyToMany(mappedBy = "book")
    private Set<Tags> tags;
}
