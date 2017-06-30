drop index act_idx_bytear_depl;
drop index act_idx_exe_procinst;
drop index act_idx_exe_parent;
drop index act_idx_exe_super;
drop index act_idx_tskass_task;
drop index act_idx_task_exec;
drop index act_idx_task_procinst;
drop index act_idx_task_procdef;
drop index act_idx_var_exe;
drop index act_idx_var_procinst;
drop index act_idx_var_bytearray;
drop index act_idx_job_exception;
drop index act_idx_model_source;
drop index act_idx_model_source_extra;
drop index act_idx_model_deployment;

drop index act_idx_exec_buskey;
drop index act_idx_task_create;
drop index act_idx_ident_lnk_user;
drop index act_idx_ident_lnk_group;
drop index act_idx_variable_task_id;

alter table act_ge_bytearray 
    drop constraint act_fk_bytearr_depl;

alter table act_ru_execution
    drop constraint act_fk_exe_procinst;

alter table act_ru_execution 
    drop constraint act_fk_exe_parent;

alter table act_ru_execution 
    drop constraint act_fk_exe_super;
	
alter table act_ru_execution 
    drop constraint act_fk_exe_procdef;
    
alter table act_ru_identitylink
    drop constraint act_fk_tskass_task;

alter table act_ru_identitylink
    drop constraint act_fk_athrz_procedef;

alter table act_ru_task
	drop constraint act_fk_task_exe;

alter table act_ru_task
	drop constraint act_fk_task_procinst;
	
alter table act_ru_task
	drop constraint act_fk_task_procdef;
    
alter table act_ru_variable
    drop constraint act_fk_var_exe;
    
alter table act_ru_variable
	drop constraint act_fk_var_procinst;

alter table act_ru_variable
    drop constraint act_fk_var_bytearray;

alter table act_ru_job
    drop constraint act_fk_job_exception;
    
alter table act_ru_event_subscr
    drop constraint act_fk_event_exec;

alter table act_re_procdef
    drop constraint act_uniq_procdef;

alter table act_re_model
    drop constraint act_fk_model_source;

alter table act_re_model
    drop constraint act_fk_model_source_extra;
    
alter table act_re_model
    drop constraint act_fk_model_deployment;    
    
drop index act_idx_event_subscr_config_;
drop index act_idx_event_subscr;
drop index act_idx_athrz_procedef;

drop table  act_ge_property;
drop table  act_ge_bytearray;
drop table  act_re_deployment;
drop table  act_re_model;
drop table  act_re_procdef;
drop table  act_ru_identitylink;
drop table  act_ru_variable;
drop table  act_ru_task;
drop table  act_ru_execution;
drop table  act_ru_job;
drop table  act_ru_event_subscr;
