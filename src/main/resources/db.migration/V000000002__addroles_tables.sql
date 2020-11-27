alter table if exists t_role drop constraint FK9sof6539ywhxemf6ojn0fqera;
alter table if exists t_role drop column user_id;

create table user_roles
(
    user_id int8,
    role_id int8,
    PRIMARY KEY(user_id, role_id)
);

alter table if exists user_roles add constraint fk_user_roles_user_id foreign key (user_id) references t_user;
alter table if exists user_roles add constraint fk_user_roles_role_id foreign key (role_id) references t_role;