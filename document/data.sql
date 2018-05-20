CREATE TABLE `t_user` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `name` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '登录密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

INSERT INTO `test`.`t_user` (`id`, `name`, `password`, `create_time`) VALUES ('1', 'aaa', '111111', '2017-04-19 17:21:31');




CREATE DEFINER=`root`@`%` PROCEDURE `sql_create_table`()
begin
declare i int;
declare num varchar(2);
set i=0;
set num = "0";
while i<32 do
set num = concat("",i);
 IF i < 10 THEN
    set num = concat("0",i);
 END IF;
set @sql_drop_table = concat(
'drop TABLE IF EXISTS t_user_', num);
set @sql_create_table = concat(
'CREATE TABLE IF NOT EXISTS t_user_', num,
' like t_user');
PREPARE sql_drop_table FROM @sql_drop_table;
PREPARE sql_create_table FROM @sql_create_table;
EXECUTE sql_drop_table;
EXECUTE sql_create_table;


set i=i+1;
end while;
end


