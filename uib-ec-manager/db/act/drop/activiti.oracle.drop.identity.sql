alter table act_id_membership 
    drop constraint act_fk_memb_group;
    
alter table act_id_membership 
    drop constraint act_fk_memb_user;

drop index act_idx_memb_group;
drop index act_idx_memb_user;

drop table  act_id_info;
drop table  act_id_membership;
drop table  act_id_group;
drop table  act_id_user;
