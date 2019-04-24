CREATE TABLE IPASS
(
  id bigint(20) NOT NULL auto_increment COMMENT 'pk',
  uid bigint(20) NOT NULL COMMENT '用户UID',
  appuid varchar(256) NOT NULL COMMENT '应用用户uid',
  keyword varchar(256) DEFAULT '' COMMENT '搜索关键字',
  password varchar(1024) NOT NULL COMMENT '加密密码',
  remark varchar(1024) COMMENT '备注',
  create_time bigint(20) COMMENT '单位:毫秒',
  update_time bigint(20) COMMENT '单位:毫秒',
  PRIMARY KEY (id),
  KEY `ipass_uid`  (uid, appuid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户密码信息';
