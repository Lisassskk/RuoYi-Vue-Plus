insert into sys_menu values('1616', '工作流'  , '0',    '6', 'workflow',          '',                                 '', '1', '0', 'M', '0', '0', '',                       'tree-table', 103, 1, sysdate(), NULL, NULL, '');
insert into sys_menu values('1617', '模型管理', '1616', '2', 'model',             'workflow/model/index',             '', '1', '1', 'C', '0', '0', 'workflow:model:list',    'tree-table', 103, 1, sysdate(), NULL, NULL, '');
insert into sys_menu values('1618', '我的待办', '1616', '5', 'task',              '',                                 '', '1', '0', 'M', '0', '0', '',                       'tree-table', 103, 1, sysdate(), NULL, NULL, '');
insert into sys_menu values('1619', '待办任务', '1618', '2', 'taskWaiting',       'workflow/task/index',              '', '1', '1', 'C', '0', '0', '',                       'tree-table', 103, 1, sysdate(), NULL, NULL, '');
insert into sys_menu values('1620', '流程定义', '1616', '3', 'processDefinition', 'workflow/processDefinition/index', '', '1', '1', 'C', '0', '0', '',                       'tree-table', 103, 1, sysdate(), NULL, NULL, '');
insert into sys_menu values('1621', '流程实例', '1630', '1', 'processInstance',   'workflow/processInstance/index',   '', '1', '1', 'C', '0', '0', '',                       'tree-table', 103, 1, sysdate(), NULL, NULL, '');
insert into sys_menu values('1622', '流程分类', '1616', '1', 'category',          'workflow/category/index',          '', '1', '0', 'C', '0', '0', 'workflow:category:list', 'tree-table', 103, 1, sysdate(), NULL, NULL, '');
insert into sys_menu values('1629', '我发起的', '1618', '1', 'myDocument',        'workflow/task/myDocument',         '', '1', '1', 'C', '0', '0', '',                       'tree-table', 103, 1, sysdate(), NULL, NULL, '');
insert into sys_menu values('1630', '流程监控', '1616', '4', 'monitor',           '',                                 '', '1', '0', 'M', '0', '0', '',                       'tree-table', 103, 1, sysdate(), NULL, NULL, '');
insert into sys_menu values('1631', '待办任务', '1630', '2', 'allTaskWaiting',    'workflow/task/allTaskWaiting',     '', '1', '1', 'C', '0', '0', '',                       'tree-table', 103, 1, sysdate(), NULL, NULL, '');


-- 流程分类管理相关按钮
insert into sys_menu values ('1623', '流程分类查询', '1622', '1', '#', '', '', 1, 0, 'F', '0', '0', 'workflow:category:query', '#', 103, 1, sysdate(), null, null, '');
insert into sys_menu values ('1624', '流程分类新增', '1622', '2', '#', '', '', 1, 0, 'F', '0', '0', 'workflow:category:add',   '#', 103, 1, sysdate(), null, null, '');
insert into sys_menu values ('1625', '流程分类修改', '1622', '3', '#', '', '', 1, 0, 'F', '0', '0', 'workflow:category:edit',  '#', 103, 1, sysdate(), null, null, '');
insert into sys_menu values ('1626', '流程分类删除', '1622', '4', '#', '', '', 1, 0, 'F', '0', '0', 'workflow:category:remove','#', 103, 1, sysdate(), null, null, '');
insert into sys_menu values ('1627', '流程分类导出', '1622', '5', '#', '', '', 1, 0, 'F', '0', '0', 'workflow:category:export','#', 103, 1, sysdate(), null, null, '');
-- 请假单信息
DROP TABLE if EXISTS test_leave;
create table test_leave
(
    id          bigint not null
        constraint test_leave_pk
            primary key,
    leave_type  varchar(255),
    start_date  timestamp,
    end_date    timestamp,
    remark      varchar(255),
    create_dept bigint,
    create_by   bigint,
    create_time timestamp,
    update_by   bigint,
    update_time timestamp,
    tenant_id   varchar(20)
);

comment on table test_leave is '请假申请表';

comment on column test_leave.id is '主键';

comment on column test_leave.leave_type is '请假类型';

comment on column test_leave.start_date is '开始时间';

comment on column test_leave.end_date is '结束时间';

comment on column test_leave.remark is '请假原因';

comment on column test_leave.create_dept is '创建部门';

comment on column test_leave.create_by is '创建者';

comment on column test_leave.create_time is '创建时间';

comment on column test_leave.update_by is '更新者';

comment on column test_leave.update_time is '更新时间';

comment on column test_leave.tenant_id is '租户编码';

alter table test_leave
    owner to postgres;

-- 流程分类信息表
DROP TABLE if EXISTS wf_category;
create table wf_category
(
    id            bigint not null
        constraint wf_category_pk
            primary key,
    category_name varchar(255),
    category_code varchar(255),
    parent_id     bigint,
    sort_num      bigint,
    tenant_id     bigint,
    create_dept   bigint,
    create_by     bigint,
    create_time   timestamp,
    update_by     bigint,
    update_time   timestamp
);

comment on table wf_category is '流程分类';

comment on column wf_category.id is '主键';

comment on column wf_category.category_name is '分类名称';

comment on column wf_category.category_code is '分类编码';

comment on column wf_category.parent_id is '父级id';

comment on column wf_category.sort_num is '排序';

comment on column wf_category.tenant_id is '租户id';

comment on column wf_category.create_dept is '创建部门';

comment on column wf_category.create_by is '创建者';

comment on column wf_category.create_time is '创建时间';

comment on column wf_category.update_by is '修改者';

comment on column wf_category.update_time is '修改时间';

alter table wf_category
    owner to postgres;

create unique index uni_category_code
    on wf_category (category_code);

INSERT INTO sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1638, '请假申请', 5, 1, 'leave', 'workflow/leave/index', 1, 0, 'C', '0', '0', 'demo:leave:list', '#', 103, 1, sysdate(), NULL, NULL, '请假申请菜单');
INSERT INTO sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1639, '请假申请查询', 1638, 1, '#', '', 1, 0, 'F', '0', '0', 'demo:leave:query', '#', 103, 1, sysdate(), NULL, NULL, '');
INSERT INTO sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1640, '请假申请新增', 1638, 2, '#', '', 1, 0, 'F', '0', '0', 'demo:leave:add', '#', 103, 1, sysdate(), NULL, NULL, '');
INSERT INTO sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1641, '请假申请修改', 1638, 3, '#', '', 1, 0, 'F', '0', '0', 'demo:leave:edit', '#', 103, 1, sysdate(), NULL, NULL, '');
INSERT INTO sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1642, '请假申请删除', 1638, 4, '#', '', 1, 0, 'F', '0', '0', 'demo:leave:remove', '#', 103, 1, sysdate(), NULL, NULL, '');
INSERT INTO sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (1643, '请假申请导出', 1638, 5, '#', '', 1, 0, 'F', '0', '0', 'demo:leave:export', '#', 103, 1, sysdate(), NULL, NULL, '');
