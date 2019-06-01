CREATE TABLE if not exists `tag` (
  `uid` int(11) NOT NULL COMMENT '用户编号',
  `name` varchar (32) NOT NULL DEFAULT '' COMMENT '标签名称',
  `description` varchar (1024) NOT NULL DEFAULT '' COMMENT '标签描述',
  `attribute` varchar (1024) NOT NULL DEFAULT '' COMMENT '标签属性',
  `children` varchar (512) NOT NULL DEFAULT '' COMMENT '子标签',
  PRIMARY KEY (`uid`, `name`),
  KEY `tag_uid` (`uid`),
  CONSTRAINT `tag_t_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;