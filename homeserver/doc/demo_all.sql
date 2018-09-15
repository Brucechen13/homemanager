
INSERT INTO `user_info` (`id`,`loginName`,`nickName`,`password`,`salt`,`state`) VALUES ('1000', 'admin', '管理员', 'd3c59d25033dbf980d29554025c23a75', '8d78869f470951332959580424d4bf4f', 0);
INSERT INTO `sys_permission` (`id`,`available`,`name`,`parentId`,`parentIds`,`permission`,`resourceType`,`url`) VALUES (1,0,'用户管理',0,'0/','userInfo:view','menu','userInfo/userList');
INSERT INTO `sys_permission` (`id`,`available`,`name`,`parentId`,`parentIds`,`permission`,`resourceType`,`url`) VALUES (2,0,'用户添加',1,'0/1','userInfo:add','button','userInfo/userAdd');
INSERT INTO `sys_permission` (`id`,`available`,`name`,`parentId`,`parentIds`,`permission`,`resourceType`,`url`) VALUES (3,0,'用户删除',1,'0/1','userInfo:del','button','userInfo/userDel');
INSERT INTO `sys_role` (`id`,`available`,`description`,`role`) VALUES (1,0,'管理员','manager');
INSERT INTO `sys_role` (`id`,`available`,`description`,`role`) VALUES (2,0,'VIP会员','vip');
INSERT INTO `sys_role` (`id`,`available`,`description`,`role`) VALUES (3,1,'普通用户','user');
INSERT INTO `sys_role_permission` (`permissionId`,`roleId`) VALUES (1,1);
INSERT INTO `sys_role_permission` (`permissionId`,`roleId`) VALUES (2,1);
INSERT INTO `sys_role_permission` (`permissionId`,`roleId`) VALUES (3,2);
INSERT INTO `sys_user_role` (`roleId`,`id`) VALUES (1,1);

