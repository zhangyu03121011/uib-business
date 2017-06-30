create table act_id_group (
    id_ nvarchar2(64),
    rev_ integer,
    name_ nvarchar2(255),
    type_ nvarchar2(255),
    primary key (id_)
);

create table act_id_membership (
    user_id_ nvarchar2(64),
    group_id_ nvarchar2(64),
    primary key (user_id_, group_id_)
);

create table act_id_user (
    id_ nvarchar2(64),
    rev_ integer,
    first_ nvarchar2(255),
    last_ nvarchar2(255),
    email_ nvarchar2(255),
    pwd_ nvarchar2(255),
    picture_id_ nvarchar2(64),
    primary key (id_)
);

create table act_id_info (
    id_ nvarchar2(64),
    rev_ integer,
    user_id_ nvarchar2(64),
    type_ nvarchar2(64),
    key_ nvarchar2(255),
    value_ nvarchar2(255),
    password_ blob,
    parent_id_ nvarchar2(255),
    primary key (id_)
);

create index act_idx_memb_group on act_id_membership(group_id_);
alter table act_id_membership 
    add constraint act_fk_memb_group 
    foreign key (group_id_) 
    references act_id_group (id_);

create index act_idx_memb_user on act_id_membership(user_id_);
alter table act_id_membership 
    add constraint act_fk_memb_user
    foreign key (user_id_) 
    references act_id_user (id_);
