create table act_id_group (
    id_ nvarchar(64),
    rev_ int,
    name_ nvarchar(255),
    type_ nvarchar(255),
    primary key (id_)
);

create table act_id_membership (
    user_id_ nvarchar(64),
    group_id_ nvarchar(64),
    primary key (user_id_, group_id_)
);

create table act_id_user (
    id_ nvarchar(64),
    rev_ int,
    first_ nvarchar(255),
    last_ nvarchar(255),
    email_ nvarchar(255),
    pwd_ nvarchar(255),
    picture_id_ nvarchar(64),
    primary key (id_)
);

create table act_id_info (
    id_ nvarchar(64),
    rev_ int,
    user_id_ nvarchar(64),
    type_ nvarchar(64),
    key_ nvarchar(255),
    value_ nvarchar(255),
    password_ varbinary(max),
    parent_id_ nvarchar(255),
    primary key (id_)
);

alter table act_id_membership 
    add constraint act_fk_memb_group 
    foreign key (group_id_) 
    references act_id_group (id_);

alter table act_id_membership 
    add constraint act_fk_memb_user 
    foreign key (user_id_) 
    references act_id_user (id_);