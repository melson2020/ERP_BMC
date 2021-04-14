-- 数据字典表
drop table if exists sys_dict_type;
create table sys_dict_type
(
    n_id          int auto_increment comment '唯一自增id' primary key,
    s_alias       varchar(100) not null comment '字典别名',
    s_name        varchar(50)  not null comment '字典名称',
    s_desc        varchar(100) null comment '字典描述',
    d_modify      datetime     not null comment '最新编辑日期',
    n_modify_user int          not null comment '最新编辑人,关联员工表',
    s_state       varchar(2)   not null comment '状态:0=禁用;1=启用;'
) comment '数据字典类型表';
create index idx1 on sys_dict_type (s_state);
create unique index idx2 on sys_dict_type (s_alias);

drop table if exists sys_dict;
create table sys_dict
(
    n_id          int auto_increment comment '唯一自增id' primary key,
    n_type_id     int          not null comment '数据字典类型id',
    s_code        varchar(100) not null comment '键',
    s_value       text         not null comment '值',
    n_order       int          not null comment '排序',
    s_desc        varchar(100) null comment '键值对描述',
    d_modify      datetime     not null comment '最新编辑日期',
    n_modify_user int          not null comment '最新编辑人,关联员工表',
    s_state       varchar(2)   not null comment '状态:0=禁用;1=启用;'
) comment '数据字典类型表';
create unique index idx1 on sys_dict (n_type_id, s_code);

-- 合同定义表
drop table if exists agreement;
create table agreement
(
    n_id           int auto_increment comment '合同唯一自增id,用于其他表关联' primary key,
    s_code         varchar(50)  not null comment '合同展示用编号,避免自增id暴露合同数量',
    s_desc         varchar(100) null comment '合同备注',
    d_create       datetime     not null comment '合同创建日期',
    n_create_user  int          not null comment '合同创建人,关联员工表',
    s_type         varchar(2)   not null comment '合同类型:1=意向合同;1=正式合同;',
    s_state        varchar(2)   not null comment '合同状态:0=已作废;1=正常;',
    d_pay          datetime     null comment '付款时间',
    s_pay          varchar(2)   null comment '付款方式,关联字典表',
    s_currency     varchar(2)   null comment '付款货币,关联字典表,页面默认人民币',
    d_send         datetime     null comment '交货日期',
    n_contact_user int          not null comment '联系人,关联员工表',
    n_parent_id    int          null comment '父级合同id,有值标识从意向合同转过来的,用于合同溯源',
    constraint idx4 unique (s_code)
) comment '合同定义表';
create index idx1 on agreement (d_create);
create index idx2 on agreement (s_type);
create index idx3 on agreement (s_state);

-- 合同公司定义表
drop table if exists agreement_company;
create table agreement_company
(
    n_id           int auto_increment comment '合同公司唯一自增id' primary key,
    s_code         varchar(50)  not null comment '合同公司展示用编号,避免自增id暴露签约公司数量',
    n_agreement_id int          not null comment '合同id',
    s_type         varchar(2)   not null comment '公司关联类型:1=签约方;2=收货方;',
    s_name         varchar(200) not null comment '公司名称',
    s_address      text         null comment '单位地址,收货方必填',
    s_contact      varchar(50)  not null comment '联系人',
    s_tel          varchar(50)  not null comment '联系电话',
    s_duty         varchar(50)  null comment '税号',
    s_account      varchar(50)  null comment '账号',
    constraint idx2 unique (s_code)
) comment '合同定义表';
create unique index idx1 on agreement_company (n_agreement_id, s_type);