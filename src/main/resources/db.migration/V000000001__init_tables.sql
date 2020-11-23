CREATE SEQUENCE
hibernate_sequence
INCREMENT BY 1
MINVALUE 1
NO CYCLE;

CREATE SEQUENCE
seq_authors
INCREMENT BY 1
MINVALUE 1
NO CYCLE;

CREATE TABLE t_author
(
    id BIGINT DEFAULT nextval('seq_authors') PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL
);

CREATE SEQUENCE
seq_tags
INCREMENT BY 1
MINVALUE 1
NO CYCLE;

create table t_tag
(
    id BIGINT DEFAULT nextval('seq_tags') PRIMARY KEY,
    tag_name varchar(255) NOT NULL UNIQUE
);

CREATE SEQUENCE
seq_reviews
INCREMENT BY 1
MINVALUE 1
NO CYCLE;

create table t_review
(
    id BIGINT DEFAULT nextval('seq_reviews') PRIMARY KEY,
    text_review varchar(2048),
    book_id int8,
    user_id int8
);

create sequence
seq_books
INCREMENT BY 1
MINVALUE 1
NO CYCLE;

create table t_book
(
    id BIGINT DEFAULT nextval('seq_books') PRIMARY KEY,
    added_at date,
    book_language varchar(255),
    deleted_at date,
    deleted_why varchar(255),
    description varchar(2048),
    pages int4 not null,
    title varchar(255) not null,
    author_id int8,
    deleted_by_id int8,
    added_by int8
);

create sequence
seq_users
INCREMENT BY 1
MINVALUE 1
NO CYCLE;

create table t_user
(
    id BIGINT DEFAULT nextval('seq_users') PRIMARY KEY,
    email varchar(255) NOT NULL UNIQUE,
    password varchar(255),
    username varchar(255) NOT NULL UNIQUE
);

create sequence
seq_requests
INCREMENT BY 1
MINVALUE 1
NO CYCLE;

create table t_request
(
    id BIGINT DEFAULT nextval('seq_requests') PRIMARY KEY,
    description varchar(2048),
    book_id int8,
    user_id int8
);

create sequence
seq_assignments
INCREMENT BY 1
MINVALUE 1
NO CYCLE;

create table t_assignment
(
    id BIGINT DEFAULT nextval('seq_assignments') PRIMARY KEY,
    assign_date date,
    due_date date,
    book_id int8,
    user_id int8
);

create table t_role
(
    id BIGINT PRIMARY KEY,
    role_type varchar(255) NOT NULL UNIQUE,
    user_id int8
);

create table t_book_tag
(
    tag_id int8 not null,
    book_id int8 not null,
    primary key (tag_id, book_id)
);

alter table if exists t_assignments add constraint FKhb21tao75hdjeofsdq6ojfieb foreign key (book_id) references t_book;
alter table if exists t_assignments add constraint FK9vet15l7l1qgj54qgsvqvmanw foreign key (user_id) references t_user;
alter table if exists t_book add constraint FKjchbplv6o6eqfu9kdwp302a8v foreign key (author_id) references t_author;
alter table if exists t_book add constraint FKp57jsionoet9k83rtn44s1hw4 foreign key (deleted_by_id) references t_user;
alter table if exists t_book add constraint FK3p9vl1qpf3xfo1o0ew6x8hty7 foreign key (added_by) references t_user;
alter table if exists t_request add constraint FKdfmre73ecylyngb1qm0km9x2k foreign key (book_id) references t_book;
alter table if exists t_request add constraint FKm998kqotbfo3ugw3jiqlr2y3b foreign key (user_id) references t_user;
alter table if exists t_review add constraint FKppv3m22y453m1akm1oxiv2tk0 foreign key (book_id) references t_book;
alter table if exists t_review add constraint FK770xtfaptveh04oq864ji9n9e foreign key (user_id) references t_user;
alter table if exists t_role add constraint FK9sof6539ywhxemf6ojn0fqera foreign key (user_id) references t_user;
alter table if exists t_tags add constraint FKif0uhaiybvjrt0h03q8uef02g foreign key (book_id) references t_book;
alter table if exists t_time_request add constraint FK33dqw6ikdcyx2eqw1u8wv4qfk foreign key (book_id) references t_book;
alter table if exists t_time_request add constraint FKsk6ijxp1i8jiww4055x3kq0q6 foreign key (user_id) references t_user;
