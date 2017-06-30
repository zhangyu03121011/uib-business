create table act_hi_procinst (
    id_ nvarchar2(64) not null,
    proc_inst_id_ nvarchar2(64) not null,
    business_key_ nvarchar2(255),
    proc_def_id_ nvarchar2(64) not null,
    start_time_ timestamp(6) not null,
    end_time_ timestamp(6),
    duration_ number(19,0),
    start_user_id_ nvarchar2(255),
    start_act_id_ nvarchar2(255),
    end_act_id_ nvarchar2(255),
    super_process_instance_id_ nvarchar2(64),
    delete_reason_ nvarchar2(2000),
    tenant_id_ nvarchar2(255) default '',
    primary key (id_),
    unique (proc_inst_id_)
);

create table act_hi_actinst (
    id_ nvarchar2(64) not null,
    proc_def_id_ nvarchar2(64) not null,
    proc_inst_id_ nvarchar2(64) not null,
    execution_id_ nvarchar2(64) not null,
    act_id_ nvarchar2(255) not null,
    task_id_ nvarchar2(64),
    call_proc_inst_id_ nvarchar2(64),
    act_name_ nvarchar2(255),
    act_type_ nvarchar2(255) not null,
    assignee_ nvarchar2(255),
    start_time_ timestamp(6) not null,
    end_time_ timestamp(6),
    duration_ number(19,0),
    tenant_id_ nvarchar2(255) default '',
    primary key (id_)
);

create table act_hi_taskinst (
    id_ nvarchar2(64) not null,
    proc_def_id_ nvarchar2(64),
    task_def_key_ nvarchar2(255),
    proc_inst_id_ nvarchar2(64),
    execution_id_ nvarchar2(64),
    parent_task_id_ nvarchar2(64),
    name_ nvarchar2(255),
    description_ nvarchar2(2000),
    owner_ nvarchar2(255),
    assignee_ nvarchar2(255),
    start_time_ timestamp(6) not null,
    claim_time_ timestamp(6),
    end_time_ timestamp(6),
    duration_ number(19,0),
    delete_reason_ nvarchar2(2000),
    priority_ integer,
    due_date_ timestamp(6),
    form_key_ nvarchar2(255),
    category_ nvarchar2(255),
    tenant_id_ nvarchar2(255) default '',
    primary key (id_)
);

create table act_hi_varinst (
    id_ nvarchar2(64) not null,
    proc_inst_id_ nvarchar2(64),
    execution_id_ nvarchar2(64),
    task_id_ nvarchar2(64),
    name_ nvarchar2(255) not null,
    var_type_ nvarchar2(100),
    rev_ integer,
    bytearray_id_ nvarchar2(64),
    double_ number(*,10),
    long_ number(19,0),
    text_ nvarchar2(2000),
    text2_ nvarchar2(2000),
    create_time_ timestamp(6),
    last_updated_time_ timestamp(6),
    primary key (id_)
);

create table act_hi_detail (
    id_ nvarchar2(64) not null,
    type_ nvarchar2(255) not null,
    proc_inst_id_ nvarchar2(64),
    execution_id_ nvarchar2(64),
    task_id_ nvarchar2(64),
    act_inst_id_ nvarchar2(64),
    name_ nvarchar2(255) not null,
    var_type_ nvarchar2(64),
    rev_ integer,
    time_ timestamp(6) not null,
    bytearray_id_ nvarchar2(64),
    double_ number(*,10),
    long_ number(19,0),
    text_ nvarchar2(2000),
    text2_ nvarchar2(2000),
    primary key (id_)
);

create table act_hi_comment (
    id_ nvarchar2(64) not null,
    type_ nvarchar2(255),
    time_ timestamp(6) not null,
    user_id_ nvarchar2(255),
    task_id_ nvarchar2(64),
    proc_inst_id_ nvarchar2(64),
    action_ nvarchar2(255),
    message_ nvarchar2(2000),
    full_msg_ blob,
    primary key (id_)
);

create table act_hi_attachment (
    id_ nvarchar2(64) not null,
    rev_ integer,
    user_id_ nvarchar2(255),
    name_ nvarchar2(255),
    description_ nvarchar2(2000),
    type_ nvarchar2(255),
    task_id_ nvarchar2(64),
    proc_inst_id_ nvarchar2(64),
    url_ nvarchar2(2000),
    content_id_ nvarchar2(64),
    primary key (id_)
);

create table act_hi_identitylink (
    id_ nvarchar2(64),
    group_id_ nvarchar2(255),
    type_ nvarchar2(255),
    user_id_ nvarchar2(255),
    task_id_ nvarchar2(64),
    proc_inst_id_ nvarchar2(64),
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
create index act_idx_hi_ident_lnk_user on act_hi_identitylink(user_id_);
create index act_idx_hi_ident_lnk_task on act_hi_identitylink(task_id_);
create index act_idx_hi_ident_lnk_procinst on act_hi_identitylink(proc_inst_id_);

create index act_idx_hi_act_inst_procinst on act_hi_actinst(proc_inst_id_, act_id_);
create index act_idx_hi_act_inst_exec on act_hi_actinst(execution_id_, act_id_);
