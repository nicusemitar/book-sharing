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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "t_assignment")
public class Assignments {

    @Id
    @SequenceGenerator(name = "assignments_id_generator", sequenceName = "seq_assignments", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assignments_id_generator")
    private Long id;

    @Column(name = "assign_date")
    private LocalDate assignDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    private Book book;


}
