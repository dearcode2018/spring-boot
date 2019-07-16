

/* 若存在先删除 */
DROP TABLE IF EXISTS `college_student`;
/* 学生表 */
CREATE TABLE college_student (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL COMMENT '姓名',
   `type` tinyint default 0 COMMENT '类型，1-理科，2-工科，3-文科',
  `credit` decimal(10,2) DEFAULT NULL COMMENT '学分',
  `birthday` datetime DEFAULT NULL COMMENT '出生日期 yyyy-MM-dd',
  `address` varchar(128) DEFAULT NULL COMMENT '地址',
  `remark` text COMMENT '备注，详细说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生表';

INSERT INTO college_student (name, type, credit, birthday, address) 
VALUES ('王菲', 1, 12.5, '1980-02-23', '浙江省杭州市东风路23号');

INSERT INTO college_student (name, type, credit, birthday, address) 
VALUES ('张国焘', 3, 34.4, '1979-01-29', '浙江省宁波市长龙路135号');

INSERT INTO college_student (name, type, credit, birthday, address) 
VALUES ('唐国强', 2, 11.0, '1983-10-01', '上海市浦东新区南埔路93号');

INSERT INTO college_student (name, type, credit, birthday, address) 
VALUES ('赵龙', 3, 13.4, '1981-09-02', '上海市徐汇区南京路49号');

INSERT INTO college_student (name, type, credit, birthday, address) 
VALUES ('李建', 1, 21.8, '1989-02-04', '吉林省延吉市解放路189号');

INSERT INTO college_student (name, type, credit, birthday, address) 
VALUES ('王龙云', 2, 29.9, '1985-09-13', '吉林省长春市青年路243号');

INSERT INTO college_student (name, type, credit, birthday, address) 
VALUES ('欧阳震华', 1, 25.0, '1983-02-18', '广东省广州市体育西路935号');

INSERT INTO college_student (name, type, credit, birthday, address) 
VALUES ('张勇', 3, 30.1, '1978-07-20', '广东省深圳市新田路路73号');

INSERT INTO college_student (name, type, credit, birthday, address) 
VALUES ('陈明', 1, 26.8, '1988-04-13', '广西省北海市长兴路路101号');

INSERT INTO college_student (name, type, credit, birthday, address) 
VALUES ('何云', 2, 41.3, '1981-04-16', '广西省贵港市东平路321号');
