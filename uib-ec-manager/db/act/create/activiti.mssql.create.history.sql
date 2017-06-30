create table act_hi_procinst (
    id_ nvarchar(64) not null,
    proc_inst_id_ nvarchar(64) not null,
    business_key_ nvarchar(255),
    proc_def_id_ nvarchar(64) not null,
    start_time_ datetime not null,
    end_time_ datetime,
    duration_ numeric(19,0),
    start_user_id_ nvarchar(255),
    start_act_id_ nvarchar(255),
    end_act_id_ nvarchar(255),
    super_process_instance_id_ nvarchar(64),
    delete_reason_ nvarchar(4000),
    tenant_id_ nvarchar(255) default '',
    primary key (id_),
    unique (proc_inst_id_)
);

create table act_hi_actinst (
    id_ nvarchar(64) not null,
    proc_def_id_ nvarchar(64) not null,
    proc_inst_id_ nvarchar(64) not null,
    execution_id_ nvarchar(64) not null,
    act_id_ nvarchar(255) not null,
    task_id_ nvarchar(64),
    call_proc_inst_id_ nvarchar(64),
    act_name_ nvarchar(255),
    act_type_ nvarchar(255) not null,
    assignee_ nvarchar(255),
    start_time_ datetime not null,
    end_time_ datetime,
    duration_ numeric(19,0),
    tenant_id_ nvarchar(255) default '',
    primary key (id_)
);

create table act_hi_taskinst (
    id_ nvarchar(64) not null,
    proc_def_id_ nvarchar(64),
    task_def_key_ nvarchar(255),
    proc_inst_id_ nvarchar(64),
    execution_id_ nvarchar(64),
    name_ nvarchar(255),
    parent_task_id_ nvarchar(64),
    description_ nvarchar(4000),
    owner_ nvarchar(255),
    assignee_ nvarchar(255),
    start_time_ datetime not null,
    claim_time_ datetime,
    end_time_ datetime,
    duration_ numeric(19,0),
    delete_reason_ nvarchar(4000),
    priority_ int,
    due_date_ datetime,
    form_key_ nvarchar(255),
    category_ nvarchar(255),
    tenant_id_ nvarchar(255) default '',
    primary key (id_)
);

create table act_hi_varinst (
    id_ nvarchar(64) not null,
    proc_inst_id_ nvarchar(64),
    execution_id_ nvarchar(64),
    task_id_ nvarchar(64),
    name_ nvarchar(255) not null,
    var_type_ nvarchar(100),
    rev_ int,
    bytearray_id_ nvarchar(64),
    double_ double precision,
    long_ numeric(19,0),
    text_ nvarchar(4000),
    text2_ nvarchar(4000),
    create_time_ datetime,
    last_updated_time_ datetime,
    primary key (id_)
);

create table act_hi_detail (
    id_ nvarchar(64) not null,
    type_ nvarchar(255) not null,
    proc_inst_id_ nvarchar(64),
    execution_id_ nvarchar(64),
    task_id_ nvarchar(64),
    act_inst_id_ nvarchar(64),
    name_ nvarchar(255) not null,
    var_type_ nvarchar(255),
    rev_ int,
    time_ datetime not null,
    bytearray_id_ nvarchar(64),
    double_ double precision,
    long_ numeric(19,0),
    text_ nvarchar(4000),
    text2_ nvarchar(4000),
    primary key (id_)
);

create table act_hi_comment (
    id_ nvarchar(64) not null,
    type_ nvarchar(255),
    time_ datetime not null,
    user_id_ nvarchar(255),
    task_id_ nvarchar(64),
    proc_inst_id_ nvarchar(64),
    action_ nvarchar(255),
    message_ nvarchar(4000),
    full_msg_ varbinary(max),
    primary key (id_)
);

create table act_hi_attachment (
    id_ nvarchar(64) not null,
    rev_ integer,
    user_id_ nvarchar(255),
    name_ nvarchar(255),
    description_ nvarchar(4000),
    type_ nvarchar(255),
    task_id_ nvarchar(64),
    proc_inst_id_ nvarchar(64),
    url_ nvarchar(4000),
    content_id_ nvarchar(64),
    primary key (id_)
);

create table act_hi_identitylink (
    id_ nvarchar(64),
    group_id_ nvarchar(255),
    type_ nvarchar(255),
    user_id_ nvarchar(255),
    task_id_ nvarchar(64),
    proc_inst_id_ nvarchar(64),
    primary key (id_)
);

create index act_idx_hi_pro_inst_end on act_hi_procinst(end_time_);
create index act_idx_hi_pro_i_buskey on act_hi_procinst(business_key_);
create index act_idx_hi_act_inst_start on act_hi_actinst(start_time_);
create index act_idx_hi_act_inst_end on act_hi_actinst(end_time_);
create index act_idx_hi_detail_proc_inst on act_hi_detail(proc_inst_id_);
create index act_idx_hi_detail_act_inst on act_hi_detail(act_inst_id_);
create index act_idx_hi_detail_time on act_hi_detail(time_);
create index act_idx_hi_detail_name on act_hi_detail(name_);
create index act_idx_hi_detail_task_id on act_hi_detail(task_id_);
create index act_idx_hi_procvar_proc_inst on act_hi_varinst(proc_inst_id_);
create index act_idx_hi_procvar_name_type on act_hi_varinst(name_, var_type_);
create index act_idx_hi_act_inst_procinst on act_hi_actinst(proc_inst_id_, act_id_);
create index act_idx_hi_act_inst_exec on act_hi_actinst(execution_id_, act_id_);
create index act_idx_hi_ident_lnk_user on act_hi_identitylink(user_id_);
create index act_idx_hi_ident_lnk_task on act_hi_identitylink(task_id_);
create index act_idx_hi_ident_lnk_procinst on act_hi_identitylink(proc_inst_id_);