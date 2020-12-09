package com.endava.booksharing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "t_book")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
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
    private Long pages;

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

    @ManyToOne(fetch = FetchType.LAZY,
            cascade =
                    {
                            CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.REFRESH,
                            CascadeType.PERSIST
                    })
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "added_by")
    private User user;

    @OneToOne
    private User deletedBy;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade =
                    {
                            CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.REFRESH,
                            CascadeType.PERSIST
                    })
    @JoinTable(name = "t_book_tag",
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            joinColumns = @JoinColumn(name = "book_id"))
    private Set<Tags> tags;
}
