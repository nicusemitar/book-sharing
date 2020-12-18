package com.endava.booksharing.model;

import com.endava.booksharing.model.enums.TagsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Data
@Table(name = "t_tag")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Tags {

    @Id
    @Column
    @SequenceGenerator(name = "tags_id_generator", sequenceName = "seq_tags", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tags_id_generator")
    private Long id;

    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "tag_type")
    @Enumerated(EnumType.STRING)
    private TagsType tagType;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "t_book_tag",
            joinColumns = {@JoinColumn(name = "tag_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Book> book;
}