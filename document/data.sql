CREATE TABLE `t_user` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `name` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '登录密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

INSERT INTO `test`.`t_user` (`id`, `name`, `password`, `create_time`) VALUES ('1', 'aaa', '111111', '2017-04-19 17:21:31');
