package com.endava.booksharing.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.SequenceGenerator;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import java.util.Set;

@Entity
@Table(name = "t_tag")
public class Tags {

    @Id
    @Column
    @SequenceGenerator(name = "tags_id_generator", sequenceName = "seq_tags", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tags_id_generator")
    private Long id;

    @Column(name = "tag_name")
    private String tagName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_tag",
            joinColumns = { @JoinColumn(name = "tag_id") },
            inverseJoinColumns = { @JoinColumn(name = "book_id") }
    )
    private Set<Book> book;

}
