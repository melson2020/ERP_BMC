-- 数据字典表
drop table if exists sys_dict_type;
create table sys_dict_type
(
    id         int auto_increment comment '唯一自增id' primary key,
    alias      varchar(100) not null comment '字典别名',
    name       varchar(50)  not null comment '字典名称',
    comments   varchar(100) null comment '描述备注',
    modify     datetime     not null comment '最新编辑日期',
    modifyUser int          not null comment '最新编辑人,关联员工表',
    state      varchar(2)   not null comment '状态:0=禁用;1=启用;'
) comment '数据字典类型表';
create index idx1 on sys_dict_type (state);
create unique index idx2 on sys_dict_type (alias);
INSERT INTO erp_bmc_transfer.sys_dict_type (id, alias, name, comments, modify, modifyUser, state) VALUES (1, 'commonState', '通用状态', '启用/禁用', '2021-04-15 13:49:12', 1, '1');

drop table if exists sys_dict;
create table sys_dict
(
    id         int auto_increment comment '唯一自增id' primary key,
    typeId     int          not null comment '数据字典类型id',
    code       varchar(100) not null comment '键',
    value      text         not null comment '值',
    sort       int          not null comment '排序',
    comments   varchar(100) null comment '描述备注',
    modify     datetime     not null comment '最新编辑日期',
    modifyUser int          not null comment '最新编辑人,关联员工表',
    state      varchar(2)   not null comment '状态:0=禁用;1=启用;'
) comment '数据字典类型表';
create unique index idx1 on sys_dict (typeId, code);
INSERT INTO erp_bmc_transfer.sys_dict (id, typeId, code, value, sort, comments, modify, modifyUser, state) VALUES (1, 1, '0', '禁用', 2, '0=禁用', '2021-04-16 13:49:49', 1, '1');
INSERT INTO erp_bmc_transfer.sys_dict (id, typeId, code, value, sort, comments, modify, modifyUser, state) VALUES (2, 1, '1', '启用', 1, '1=启用', '2021-04-15 13:49:51', 1, '1');

-- 合同定义表
drop table if exists contract;
create table contract
(
    id            int auto_increment comment '合同唯一自增id,用于其他表关联' primary key,
    contractNo    varchar(50)  not null comment '合同展示用编号,避免自增id暴露合同数量',
    createDate    datetime     not null comment '合同创建日期',
    createUser    int          not null comment '合同创建人,关联员工表',
    type          varchar(2)   not null comment '合同类型:1=意向合同;2=正式合同;',
    state         varchar(2)   not null comment '合同状态:0=已作废;1=待处理;2=进行中;3=已完成;',
    orderTicketNo varchar(50)  null comment '客户订单号码',
    deliverType   varchar(2)   null comment '运输方式',
    payDate       datetime     null comment '付款时间',
    payWay        varchar(2)   null comment '付款方式,关联字典表',
    currency      varchar(2)   null comment '付款货币,关联字典表,页面默认人民币',
    deliverDate   datetime     null comment '交货日期',
    contactUser   int          not null comment '联系人,关联员工表',
    quality       varchar(100) null comment '质量保证',
    sourceId      int          null comment '来源合同id,有值标识从意向合同转过来的,用于合同溯源',
    constraint idx4 unique (contractNo)
) comment '合同定义表';
create index idx1 on contract (createDate);
create index idx2 on contract (type);
create index idx3 on contract (state);

-- 合同公司表
drop table if exists contract_org;
create table contract_org
(
    id          int auto_increment comment '合同公司唯一自增id' primary key,
    contractNo  varchar(50)  not null comment '合同公司展示用编号,避免自增id暴露签约公司数量',
    contractId  int          not null comment '合同id',
    type        varchar(2)   not null comment '公司关联类型:1=买方;2=收货单位;3=卖方确认;4=买方确认;',
    name        varchar(200) not null comment '公司名称',
    address     text         null comment '单位地址,收货方必填',
    contactUser varchar(50)  not null comment '联系人',
    phone       varchar(50)  not null comment '联系电话',
    taxNo       varchar(50)  null comment '税号',
    account     varchar(50)  null comment '账号'
) comment '合同公司表';
create unique index idx1 on contract_org (contractId, type);

-- 合同货物表
drop table if exists contract_stock;
create table contract_stock
(
    id            int auto_increment comment '合同货物清单唯一自增id' primary key,
    contractId    int         not null comment '合同id',
    productId     int         not null comment '货物id',
    productName   varchar(50) not null comment '货物名称',
    propId        int         not null comment '属性id',
    propName      varchar(50) not null comment '属性名称',
    remark        varchar(50) null comment '备注',
    number        varchar(50) not null comment '数量,不确定是否会有特殊需求要半件,直接使用字符串',
    taxPrice      varchar(50) not null comment '含税单价,可能为小数,直接使用字符串',
    taxTotalPrice varchar(50) not null comment '含税总价,可能为小数,直接使用字符',
    contactUser   varchar(50) not null comment '联系人',
    phone         varchar(50) not null comment '联系电话',
    taxNo         varchar(50) null comment '税号',
    account       varchar(50) null comment '账号'
) comment '合同货物表';
create unique index idx1 on contract_stock (contractId, productId);

-- 合同额外属性表
drop table if exists contract_extend;
create table contract_extend
(
    id         int auto_increment comment '唯一自增id' primary key,
    contractId int          not null comment '合同id',
    code       varchar(100) not null comment '属性别名',
    value      text         null comment '属性值'
) comment '合同额外属性表';
create unique index idx1 on contract_extend (contractId, code);