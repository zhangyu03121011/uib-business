drop table if exists act_ge_property cascade constraints;
drop table if exists act_ge_bytearray cascade constraints;
drop table if exists act_re_deployment cascade constraints;
drop table if exists act_re_model cascade constraints;
drop table if exists act_ru_execution cascade constraints;
drop table if exists act_ru_job cascade constraints;
drop table if exists act_re_procdef cascade constraints;
drop table if exists act_ru_task cascade constraints;
drop table if exists act_ru_identitylink cascade constraints;
drop table if exists act_ru_variable cascade constraints;
drop table if exists act_ru_event_subscr cascade constraints;

drop index if exists act_idx_exec_buskey;
drop index if exists act_idx_task_create;
drop index if exists act_idx_ident_lnk_user;
drop index if exists act_idx_ident_lnk_group;
drop index if exists act_idx_variable_task_id;
drop index if exists act_idx_event_subscr_config_;
drop index if exists act_idx_athrz_procedef;