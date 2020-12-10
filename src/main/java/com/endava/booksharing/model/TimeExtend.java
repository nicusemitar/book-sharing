package com.endava.booksharing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "t_request")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TimeExtend {

    @Id
    @SequenceGenerator(name = "request_id_generator", sequenceName = "seq_requests", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "request_id_generator")
    private Long id;

    @Column
    private String description;

    @OneToOne
    private Assignments assignment;

    @Column
    private LocalDate requestedDate;
}