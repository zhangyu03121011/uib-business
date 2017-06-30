create table act_ge_property (
    name_ nvarchar2(64),
    value_ nvarchar2(300),
    rev_ integer,
    primary key (name_)
);

insert into act_ge_property
values ('schema.version', '5.15.1', 1);

insert into act_ge_property
values ('schema.history', 'create(5.15.1)', 1);

insert into act_ge_property
values ('next.dbid', '1', 1);

create table act_ge_bytearray (
    id_ nvarchar2(64),
    rev_ integer,
    name_ nvarchar2(255),
    deployment_id_ nvarchar2(64),
    bytes_ blob,
    generated_ number(1,0) check (generated_ in (1,0)),
    primary key (id_)
);

create table act_re_deployment (
    id_ nvarchar2(64),
    name_ nvarchar2(255),
    category_ nvarchar2(255),
    tenant_id_ nvarchar2(255) default '',
    deploy_time_ timestamp(6),
    primary key (id_)
);

create table act_re_model (
    id_ nvarchar2(64) not null,
    rev_ integer,
    name_ nvarchar2(255),
    key_ nvarchar2(255),
    category_ nvarchar2(255),
    create_time_ timestamp(6),
    last_update_time_ timestamp(6),
    version_ integer,
    meta_info_ nvarchar2(2000),
    deployment_id_ nvarchar2(64),
    editor_source_value_id_ nvarchar2(64),
    editor_source_extra_value_id_ nvarchar2(64),
    tenant_id_ nvarchar2(255) default '',
    primary key (id_)
);

create table act_ru_execution (
    id_ nvarchar2(64),
    rev_ integer,
    proc_inst_id_ nvarchar2(64),
    business_key_ nvarchar2(255),
    parent_id_ nvarchar2(64),
    proc_def_id_ nvarchar2(64),
    super_exec_ nvarchar2(64),
    act_id_ nvarchar2(255),
    is_active_ number(1,0) check (is_active_ in (1,0)),
    is_concurrent_ number(1,0) check (is_concurrent_ in (1,0)),
    is_scope_ number(1,0) check (is_scope_ in (1,0)),
    is_event_scope_ number(1,0) check (is_event_scope_ in (1,0)),
    suspension_state_ integer,
    cached_ent_state_ integer,
    tenant_id_ nvarchar2(255) default '',
    primary key (id_)
);

create table act_ru_job (
    id_ nvarchar2(64) not null,
    rev_ integer,
    type_ nvarchar2(255) not null,
    lock_exp_time_ timestamp(6),
    lock_owner_ nvarchar2(255),
    exclusive_ number(1,0) check (exclusive_ in (1,0)),
    execution_id_ nvarchar2(64),
    process_instance_id_ nvarchar2(64),
    proc_def_id_ nvarchar2(64),
    retries_ integer,
    exception_stack_id_ nvarchar2(64),
    exception_msg_ nvarchar2(2000),
    duedate_ timestamp(6),
    repeat_ nvarchar2(255),
    handler_type_ nvarchar2(255),
    handler_cfg_ nvarchar2(2000),
    tenant_id_ nvarchar2(255) default '',
    primary key (id_)
);

create table act_re_procdef (
    id_ nvarchar2(64) not null,
    rev_ integer,
    category_ nvarchar2(255),
    name_ nvarchar2(255),
    key_ nvarchar2(255) not null,
    version_ integer not null,
    deployment_id_ nvarchar2(64),
    resource_name_ nvarchar2(2000),
    dgrm_resource_name_ varchar(4000),
    description_ nvarchar2(2000),
    has_start_form_key_ number(1,0) check (has_start_form_key_ in (1,0)),
    suspension_state_ integer,
    tenant_id_ nvarchar2(255) default '',
    primary key (id_)
);

create table act_ru_task (
    id_ nvarchar2(64),
    rev_ integer,
    execution_id_ nvarchar2(64),
    proc_inst_id_ nvarchar2(64),
    proc_def_id_ nvarchar2(64),
    name_ nvarchar2(255),
    parent_task_id_ nvarchar2(64),
    description_ nvarchar2(2000),
    task_def_key_ nvarchar2(255),
    owner_ nvarchar2(255),
    assignee_ nvarchar2(255),
    delegation_ nvarchar2(64),
    priority_ integer,
    create_time_ timestamp(6),
    due_date_ timestamp(6),
    category_ nvarchar2(255),
    suspension_state_ integer,
    tenant_id_ nvarchar2(255) default '',
    primary key (id_)
);

create table act_ru_identitylink (
    id_ nvarchar2(64),
    rev_ integer,
    group_id_ nvarchar2(255),
    type_ nvarchar2(255),
    user_id_ nvarchar2(255),
    task_id_ nvarchar2(64),
    proc_inst_id_ nvarchar2(64),
    proc_def_id_ nvarchar2(64),
    primary key (id_)
);

create table act_ru_variable (
    id_ nvarchar2(64) not null,
    rev_ integer,
    type_ nvarchar2(255) not null,
    name_ nvarchar2(255) not null,
    execution_id_ nvarchar2(64),
    proc_inst_id_ nvarchar2(64),
    task_id_ nvarchar2(64),
    bytearray_id_ nvarchar2(64),
    double_ number(*,10),
    long_ number(19,0),
    text_ nvarchar2(2000),
    text2_ nvarchar2(2000),
    primary key (id_)
);

create table act_ru_event_subscr (
    id_ nvarchar2(64) not null,
    rev_ integer,
    event_type_ nvarchar2(255) not null,
    event_name_ nvarchar2(255),
    execution_id_ nvarchar2(64),
    proc_inst_id_ nvarchar2(64),
    activity_id_ nvarchar2(64),
    configuration_ nvarchar2(255),
    created_ timestamp(6) not null,
    proc_def_id_ nvarchar2(64),
    tenant_id_ nvarchar2(255) default '',
    primary key (id_)
);

create index act_idx_exec_buskey on act_ru_execution(business_key_);
create index act_idx_task_create on act_ru_task(create_time_);
create index act_idx_ident_lnk_user on act_ru_identitylink(user_id_);
create index act_idx_ident_lnk_group on act_ru_identitylink(group_id_);
create index act_idx_event_subscr_config_ on act_ru_event_subscr(configuration_);
create index act_idx_variable_task_id on act_ru_variable(task_id_);

create index act_idx_bytear_depl on act_ge_bytearray(deployment_id_);
alter table act_ge_bytearray
    add constraint act_fk_bytearr_depl
    foreign key (deployment_id_) 
    references act_re_deployment (id_);

alter table act_re_procdef
    add constraint act_uniq_procdef
    unique (key_,version_, tenant_id_);
    
create index act_idx_exe_procinst on act_ru_execution(proc_inst_id_);
alter table act_ru_execution
    add constraint act_fk_exe_procinst
    foreign key (proc_inst_id_) 
    references act_ru_execution (id_);

create index act_idx_exe_parent on act_ru_execution(parent_id_);
alter table act_ru_execution
    add constraint act_fk_exe_parent
    foreign key (parent_id_) 
    references act_ru_execution (id_);
    
create index act_idx_exe_super on act_ru_execution(super_exec_);
alter table act_ru_execution
    add constraint act_fk_exe_super
    foreign key (super_exec_) 
    references act_ru_execution (id_);
    
create index act_idx_exe_procdef on act_ru_execution(proc_def_id_);
alter table act_ru_execution
    add constraint act_fk_exe_procdef 
    foreign key (proc_def_id_) 
    references act_re_procdef (id_);    

create index act_idx_tskass_task on act_ru_identitylink(task_id_);
alter table act_ru_identitylink
    add constraint act_fk_tskass_task
    foreign key (task_id_) 
    references act_ru_task (id_);

create index act_idx_athrz_procedef  on act_ru_identitylink(proc_def_id_);
alter table act_ru_identitylink
    add constraint act_fk_athrz_procedef
    foreign key (proc_def_id_) 
    references act_re_procdef (id_);
    
create index act_idx_idl_procinst on act_ru_identitylink(proc_inst_id_);
alter table act_ru_identitylink
    add constraint act_fk_idl_procinst
    foreign key (proc_inst_id_) 
    references act_ru_execution (id_);    

create index act_idx_task_exec on act_ru_task(execution_id_);
alter table act_ru_task
    add constraint act_fk_task_exe
    foreign key (execution_id_)
    references act_ru_execution (id_);
    
create index act_idx_task_procinst on act_ru_task(proc_inst_id_);
alter table act_ru_task
    add constraint act_fk_task_procinst
    foreign key (proc_inst_id_)
    references act_ru_execution (id_);
    
create index act_idx_task_procdef on act_ru_task(proc_def_id_);
alter table act_ru_task
  add constraint act_fk_task_procdef
  foreign key (proc_def_id_)
  references act_re_procdef (id_);
  
create index act_idx_var_exe on act_ru_variable(execution_id_);
alter table act_ru_variable 
    add constraint act_fk_var_exe
    foreign key (execution_id_) 
    references act_ru_execution (id_);

create index act_idx_var_procinst on act_ru_variable(proc_inst_id_);
alter table act_ru_variable
    add constraint act_fk_var_procinst
    foreign key (proc_inst_id_)
    references act_ru_execution(id_);

create index act_idx_var_bytearray on act_ru_variable(bytearray_id_);
alter table act_ru_variable 
    add constraint act_fk_var_bytearray 
    foreign key (bytearray_id_) 
    references act_ge_bytearray (id_);

create index act_idx_job_exception on act_ru_job(exception_stack_id_);
alter table act_ru_job 
    add constraint act_fk_job_exception
    foreign key (exception_stack_id_) 
    references act_ge_bytearray (id_);
    
create index act_idx_event_subscr on act_ru_event_subscr(execution_id_);
alter table act_ru_event_subscr
    add constraint act_fk_event_exec
    foreign key (execution_id_)
    references act_ru_execution(id_);

create index act_idx_model_source on act_re_model(editor_source_value_id_);
alter table act_re_model 
    add constraint act_fk_model_source 
    foreign key (editor_source_value_id_) 
    references act_ge_bytearray (id_);

create index act_idx_model_source_extra on act_re_model(editor_source_extra_value_id_);
alter table act_re_model 
    add constraint act_fk_model_source_extra 
    foreign key (editor_source_extra_value_id_) 
    references act_ge_bytearray (id_);
    
create index act_idx_model_deployment on act_re_model(deployment_id_);    
alter table act_re_model 
    add constraint act_fk_model_deployment 
    foreign key (deployment_id_) 
    references act_re_deployment (id_);        
    
