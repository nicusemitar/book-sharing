ALTER TABLE IF EXISTS t_request DROP CONSTRAINT FKdfmre73ecylyngb1qm0km9x2k,
                                DROP CONSTRAINT FKm998kqotbfo3ugw3jiqlr2y3b,
                                DROP COLUMN book_id,
                                DROP COLUMN user_id,
                                ADD assignment_id bigint not null,
                                ADD requested_date DATE not null,
                                ADD CONSTRAINT fk_request_assignment_id foreign key (assignment_id) references t_assignment;