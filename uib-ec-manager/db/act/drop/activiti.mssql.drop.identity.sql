if exists (select table_name from information_schema.tables where table_name = 'act_id_membership') alter table act_id_membership drop constraint act_fk_memb_group;
if exists (select table_name from information_schema.tables where table_name = 'act_id_membership') alter table act_id_membership drop constraint act_fk_memb_user;
    
if exists (select table_name from information_schema.tables where table_name = 'act_id_info') drop table act_id_info;
if exists (select table_name from information_schema.tables where table_name = 'act_id_membership') drop table act_id_membership;
if exists (select table_name from information_schema.tables where table_name = 'act_id_group') drop table act_id_group;
if exists (select table_name from information_schema.tables where table_name = 'act_id_user') drop table act_id_user;
