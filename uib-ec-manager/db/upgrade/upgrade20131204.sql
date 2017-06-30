alter table sys_menu modify parent_ids varchar2(2000);

alter table sys_area modify parent_ids varchar2(2000);
alter table sys_area add sort number(10);
comment on column sys_area.sort is '排序';
update sys_area set sort = 30;

alter table sys_office modify parent_ids varchar2(2000);
alter table sys_office add sort number(10);
comment on column sys_office.sort is '排序';
update sys_office set sort = 30;

alter table sys_dict modify parent_ids varchar2(2000);