package com.endava.booksharing.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToOne;

@Entity
@Data
@Table(name = "t_time_request")
public class TimeExtendRequest {

    @Id
    @SequenceGenerator(name = "request_id_generator", sequenceName = "seq_requests", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "request_id_generator")
    private Long id;

    @Column
    private String description;

    @OneToOne
    private Book book;

    @OneToOne
    private User user;

}
