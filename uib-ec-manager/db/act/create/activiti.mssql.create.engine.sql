create table act_ge_property (
    name_ nvarchar(64),
    value_ nvarchar(300),
    rev_ int,
    primary key (name_)
);

insert into act_ge_property
values ('schema.version', '5.15.1', 1);

insert into act_ge_property
values ('schema.history', 'create(5.15.1)', 1);

insert into act_ge_property
values ('next.dbid', '1', 1);

create table act_ge_bytearray (
    id_ nvarchar(64),
    rev_ int,
    name_ nvarchar(255),
    deployment_id_ nvarchar(64),
    bytes_  varbinary(max),
    generated_ tinyint,
    primary key (id_)
);

create table act_re_deployment (
    id_ nvarchar(64),
    name_ nvarchar(255),
    category_ nvarchar(255),
    tenant_id_ nvarchar(255) default '',
    deploy_time_ datetime,
    primary key (id_)
);

create table act_re_model (
    id_ nvarchar(64) not null,
    rev_ int,
    name_ nvarchar(255),
    key_ nvarchar(255),
    category_ nvarchar(255),
    create_time_ datetime,
    last_update_time_ datetime,
    version_ int,
    meta_info_ nvarchar(4000),
    deployment_id_ nvarchar(64),
    editor_source_value_id_ nvarchar(64),
    editor_source_extra_value_id_ nvarchar(64),
    tenant_id_ nvarchar(255) default '',
    primary key (id_)
);

create table act_ru_execution (
    id_ nvarchar(64),
    rev_ int,
    proc_inst_id_ nvarchar(64),
    business_key_ nvarchar(255),
    parent_id_ nvarchar(64),
    proc_def_id_ nvarchar(64),
    super_exec_ nvarchar(64),
    act_id_ nvarchar(255),
    is_active_ tinyint,
    is_concurrent_ tinyint,
    is_scope_ tinyint,
    is_event_scope_ tinyint,
    suspension_state_ tinyint,
    cached_ent_state_ int,
    tenant_id_ nvarchar(255) default '',
    primary key (id_)
);

create table act_ru_job (
    id_ nvarchar(64) not null,
  rev_ int,
    type_ nvarchar(255) not null,
    lock_exp_time_ datetime,
    lock_owner_ nvarchar(255),
    exclusive_ bit,
    execution_id_ nvarchar(64),
    process_instance_id_ nvarchar(64),
    proc_def_id_ nvarchar(64),
    retries_ int,
    exception_stack_id_ nvarchar(64),
    exception_msg_ nvarchar(4000),
    duedate_ datetime null,
    repeat_ nvarchar(255),
    handler_type_ nvarchar(255),
    handler_cfg_ nvarchar(4000),
    tenant_id_ nvarchar(255) default '',
    primary key (id_)
);

create table act_re_procdef (
    id_ nvarchar(64) not null,
    rev_ int,
    category_ nvarchar(255),
    name_ nvarchar(255),
    key_ nvarchar(255) not null,
    version_ int not null,
    deployment_id_ nvarchar(64),
    resource_name_ nvarchar(4000),
    dgrm_resource_name_ nvarchar(4000),
    description_ nvarchar(4000),
    has_start_form_key_ tinyint,
    suspension_state_ tinyint,
    tenant_id_ nvarchar(255) default '',
    primary key (id_)
);

create table act_ru_task (
    id_ nvarchar(64),
    rev_ int,
    execution_id_ nvarchar(64),
    proc_inst_id_ nvarchar(64),
    proc_def_id_ nvarchar(64),
    name_ nvarchar(255),
    parent_task_id_ nvarchar(64),
    description_ nvarchar(4000),
    task_def_key_ nvarchar(255),
    owner_ nvarchar(255),
    assignee_ nvarchar(255),
    delegation_ nvarchar(64),
    priority_ int,
    create_time_ datetime,
    due_date_ datetime,
    category_ nvarchar(255),
    suspension_state_ int,
    tenant_id_ nvarchar(255) default '',
    primary key (id_)
);

create table act_ru_identitylink (
    id_ nvarchar(64),
    rev_ int,
    group_id_ nvarchar(255),
    type_ nvarchar(255),
    user_id_ nvarchar(255),
    task_id_ nvarchar(64),
    proc_inst_id_ nvarchar(64),
    proc_def_id_ nvarchar(64),
    primary key (id_)
);

create table act_ru_variable (
    id_ nvarchar(64) not null,
    rev_ int,
    type_ nvarchar(255) not null,
    name_ nvarchar(255) not null,
    execution_id_ nvarchar(64),
    proc_inst_id_ nvarchar(64),
    task_id_ nvarchar(64),
    bytearray_id_ nvarchar(64),
    double_ double precision,
    long_ numeric(19,0),
    text_ nvarchar(4000),
    text2_ nvarchar(4000),
    primary key (id_)
);

create table act_ru_event_subscr (
    id_ nvarchar(64) not null,
    rev_ int,
    event_type_ nvarchar(255) not null,
    event_name_ nvarchar(255),
    execution_id_ nvarchar(64),
    proc_inst_id_ nvarchar(64),
    activity_id_ nvarchar(64),
    configuration_ nvarchar(255),
    created_ datetime not null,
    proc_def_id_ nvarchar(64),
    tenant_id_ nvarchar(255) default '',
    primary key (id_)
);

create index act_idx_exec_buskey on act_ru_execution(business_key_);
create index act_idx_task_create on act_ru_task(create_time_);
create index act_idx_ident_lnk_user on act_ru_identitylink(user_id_);
create index act_idx_ident_lnk_group on act_ru_identitylink(group_id_);
create index act_idx_event_subscr_config_ on act_ru_event_subscr(configuration_);
create index act_idx_variable_task_id on act_ru_variable(task_id_);
create index act_idx_athrz_procedef on act_ru_identitylink(proc_def_id_);
create index act_idx_execution_proc on act_ru_execution(proc_def_id_);
create index act_idx_execution_parent on act_ru_execution(parent_id_);
create index act_idx_execution_super on act_ru_execution(super_exec_);
create index act_idx_execution_idandrev on act_ru_execution(id_, rev_);
create index act_idx_variable_ba on act_ru_variable(bytearray_id_);
create index act_idx_variable_exec on act_ru_variable(execution_id_);
create index act_idx_variable_procinst on act_ru_variable(proc_inst_id_);
create index act_idx_ident_lnk_task on act_ru_identitylink(task_id_);
create index act_idx_ident_lnk_procinst on act_ru_identitylink(proc_inst_id_);
create index act_idx_task_exec on act_ru_task(execution_id_);
create index act_idx_task_procinst on act_ru_task(proc_inst_id_);
create index act_idx_exec_proc_inst_id on act_ru_execution(proc_inst_id_);
create index act_idx_task_proc_def_id on act_ru_task(proc_def_id_);
create index act_idx_event_subscr_exec_id on act_ru_event_subscr(execution_id_);
create index act_idx_job_exception_stack_id on act_ru_job(exception_stack_id_);

alter table act_ge_bytearray
    add constraint act_fk_bytearr_depl 
    foreign key (deployment_id_) 
    references act_re_deployment (id_);

alter table act_re_procdef
    add constraint act_uniq_procdef
    unique (key_,version_, tenant_id_);
    
alter table act_ru_execution
    add constraint act_fk_exe_parent 
    foreign key (parent_id_) 
    references act_ru_execution (id_);
    
alter table act_ru_execution
    add constraint act_fk_exe_super 
    foreign key (super_exec_) 
    references act_ru_execution (id_);

alter table act_ru_execution
    add constraint act_fk_exe_procdef 
    foreign key (proc_def_id_) 
    references act_re_procdef (id_);

alter table act_ru_identitylink
    add constraint act_fk_tskass_task 
    foreign key (task_id_) 
    references act_ru_task (id_);
    
alter table act_ru_identitylink
    add constraint act_fk_athrz_procedef
    foreign key (proc_def_id_) 
    references act_re_procdef (id_);
    
alter table act_ru_identitylink
    add constraint act_fk_idl_procinst
    foreign key (proc_inst_id_) 
    references act_ru_execution (id_);       
    
alter table act_ru_task
    add constraint act_fk_task_exe
    foreign key (execution_id_)
    references act_ru_execution (id_);
    
alter table act_ru_task
    add constraint act_fk_task_procinst
    foreign key (proc_inst_id_)
    references act_ru_execution (id_);
    
alter table act_ru_task
  add constraint act_fk_task_procdef
  foreign key (proc_def_id_)
  references act_re_procdef (id_);
  
alter table act_ru_variable 
    add constraint act_fk_var_exe 
    foreign key (execution_id_) 
    references act_ru_execution (id_);

alter table act_ru_variable
    add constraint act_fk_var_procinst
    foreign key (proc_inst_id_)
    references act_ru_execution(id_);

alter table act_ru_variable 
    add constraint act_fk_var_bytearray 
    foreign key (bytearray_id_) 
    references act_ge_bytearray (id_);
  
alter table act_ru_job 
    add constraint act_fk_job_exception 
    foreign key (exception_stack_id_) 
    references act_ge_bytearray (id_);
    
alter table act_ru_event_subscr
    add constraint act_fk_event_exec
    foreign key (execution_id_)
    references act_ru_execution(id_);
    
alter table act_re_model 
    add constraint act_fk_model_source 
    foreign key (editor_source_value_id_) 
    references act_ge_bytearray (id_);

alter table act_re_model 
    add constraint act_fk_model_source_extra 
    foreign key (editor_source_extra_value_id_) 
    references act_ge_bytearray (id_);

alter table act_re_model 
    add constraint act_fk_model_deployment 
    foreign key (deployment_id_) 
    references act_re_deployment (id_);    
