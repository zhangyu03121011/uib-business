alter table sys_role add (is_sys varchar2(64),useable varchar2(64));
comment on column sys_role.is_sys is '是否系统数据';
comment on column sys_role.useable is '是否启用';
update sys_role set useable='1';

alter table sys_user add (login_flag varchar2(64),photo varchar2(100));
comment on column sys_user.login_flag is '是否可登陆';
comment on column sys_user.photo is '头像';
update sys_user set login_flag='1';

alter table sys_office add (useable varchar2(64),primary_person varchar2(64),deputy_person varchar2(64));
comment on column sys_office.useable is '是否可用';
comment on column sys_office.primary_person is '主负责人';
comment on column sys_office.deputy_person is '副负责人';
update sys_office set useable='1';

insert into sys_dict (id, value, label, type, description, sort, parent_id, create_by, create_date, update_by, update_date, remarks, del_flag)
values ('9619c52073564b5782451bfc40c48b36', '3', '小组', 'sys_office_type', '机构类型', '80', '0', '1', to_timestamp('08-01-2014 11:28:13.953000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', to_timestamp('08-01-2014 11:28:13.953000', 'dd-mm-yyyy hh24:mi:ss.ff'), '小组', '0');
insert into sys_dict (id, value, label, type, description, sort, parent_id, create_by, create_date, update_by, update_date, remarks, del_flag)
values ('3d80ae9c017748cdb9515749486c81b7', '4', '其他', 'sys_office_type', '机构类型', '90', '0', '1', to_timestamp('08-01-2014 11:28:13.953000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', to_timestamp('08-01-2014 11:28:13.953000', 'dd-mm-yyyy hh24:mi:ss.ff'), '其他组织', '0');

insert into sys_dict (id, value, label, type, description, sort, parent_id, create_by, create_date, update_by, update_date, remarks, del_flag)
values ('2a5ce7bd9ae44f8ca72555297e6c9066', 'zhb', '综合部', 'sys_office_common', '快捷通用部门', '10', '0', '1', to_timestamp('08-01-2014 11:28:13.953000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', to_timestamp('08-01-2014 11:28:13.953000', 'dd-mm-yyyy hh24:mi:ss.ff'), '综合部', '0');
insert into sys_dict (id, value, label, type, description, sort, parent_id, create_by, create_date, update_by, update_date, remarks, del_flag)
values ('6588dd604ca24c5183d765ebcda2e245', 'kfb', '开发部', 'sys_office_common', '快捷通用部门', '10', '0', '1', to_timestamp('08-01-2014 11:28:13.953000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', to_timestamp('08-01-2014 11:28:13.953000', 'dd-mm-yyyy hh24:mi:ss.ff'), '开发部', '0');
insert into sys_dict (id, value, label, type, description, sort, parent_id, create_by, create_date, update_by, update_date, remarks, del_flag)
values ('ee1185d31e5b41d8b0cdb45dd83a95d1', 'rlb', '人力部', 'sys_office_common', '快捷通用部门', '10', '0', '1', to_timestamp('08-01-2014 11:28:13.953000', 'dd-mm-yyyy hh24:mi:ss.ff'), '1', to_timestamp('08-01-2014 11:28:13.953000', 'dd-mm-yyyy hh24:mi:ss.ff'), '人力部', '0');

