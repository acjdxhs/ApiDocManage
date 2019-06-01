CREATE TABLE if not exists `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `account` VARCHAR (32) NOT NULL COMMENT '账号',
  `password` VARCHAR(32) NOT NULL COMMENT '用户密码',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;