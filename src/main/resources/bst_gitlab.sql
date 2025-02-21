-- 导出 bst_gitlab 的数据库结构
CREATE DATABASE IF NOT EXISTS `bst_gitlab` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `bst_gitlab`;


-- 导出  表 bst_gitlab.develop_organize 结构
CREATE TABLE IF NOT EXISTS `develop_organize` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `code` int(11) NOT NULL COMMENT '组织编号',
  `name` int(11) NOT NULL COMMENT '组织名称',
  `ding_access_token` varchar(255) NOT NULL COMMENT '钉钉AccessToken',
  `ding_secret` varchar(128) NOT NULL COMMENT '钉钉secret',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='研发组织';

-- 数据导出被取消选择。


-- 导出  表 bst_gitlab.git_code_count 结构
CREATE TABLE IF NOT EXISTS `git_code_count` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `committer_name` varchar(128) NOT NULL COMMENT '提交者名称',
  `committer_email` varchar(128) DEFAULT NULL COMMENT '提交者邮箱',
  `add_row_count` int(11) DEFAULT '0' COMMENT '新增总行数',
  `delete_row_count` int(11) DEFAULT '0' COMMENT '删除总行数',
  `total_row_count` int(11) DEFAULT '0' COMMENT '总行数',
  `project_id` varchar(128) NOT NULL COMMENT '项目编号',
  `oper_date` varchar(32) NOT NULL COMMENT '统计日期',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `committer_name_project_id_oper_date` (`committer_name`,`project_id`,`oper_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='git代码统计';

-- 数据导出被取消选择。


-- 导出  表 bst_gitlab.git_project 结构
CREATE TABLE IF NOT EXISTS `git_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `project_id` varchar(32) NOT NULL COMMENT '项目编号',
  `name` varchar(255) DEFAULT NULL COMMENT '项目名称',
  `description` text COMMENT '项目描述',
  `default_branch` varchar(255) DEFAULT NULL COMMENT '项目默认分支',
  `tag_count` int(11) DEFAULT NULL COMMENT '项目tag数量',
  `create_time` datetime DEFAULT NULL COMMENT '项目创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `gitlab_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='git项目';

-- 数据导出被取消选择。


-- 导出  表 bst_gitlab.git_user 结构
CREATE TABLE IF NOT EXISTS `git_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `user_id` varchar(128) NOT NULL COMMENT '账号编号',
  `state` varchar(128) NOT NULL COMMENT '账号状态',
  `name` varchar(255) DEFAULT NULL COMMENT '账号中文名称',
  `user_name` varchar(255) DEFAULT NULL COMMENT '账号拼音名称',
  `email` varchar(255) DEFAULT NULL COMMENT '账号邮箱',
  `organize_code` varchar(255) DEFAULT NULL COMMENT '组织编号',
  `create_time` datetime DEFAULT NULL COMMENT '账号创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `git_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='git账号';

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
