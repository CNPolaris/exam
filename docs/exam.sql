/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : k12exam

 Target Server Type    : MySQL
 Target Server Version : 50699
 File Encoding         : 65001

 Date: 20/07/2022 10:38:26
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `class_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '班级编码',
  `class_count` int NULL DEFAULT NULL COMMENT '班级人数',
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '班级名称',
  `level` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '班级表';

-- ----------------------------
-- Records of class
-- ----------------------------
BEGIN;
INSERT INTO `class` (`id`, `class_code`, `class_count`, `class_name`, `level`, `create_time`, `status`) VALUES (1, '747a9b6b-b925-45b3-be60-d8ba2a451c58', 1, '三中', 1, '2022-02-08 10:40:43', 1), (2, 'd190b7be-f639-4fb7-9a8f-df512135b409', 0, '十五班', 1, '2022-03-13 17:11:14', 0), (3, '747a9b6b-b925-45b3-be60-d8ba2a451c58', 1, '三中12', 1, '2022-02-08 10:40:43', 0), (4, '747a9b6b-b925-45b3-be60-d8ba2a451c58', 1, '三中一年级一般', 1, '2022-02-08 10:40:43', 0);
COMMIT;

-- ----------------------------
-- Table structure for class_teacher
-- ----------------------------
DROP TABLE IF EXISTS `class_teacher`;
CREATE TABLE `class_teacher`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `class_id` int NULL DEFAULT NULL COMMENT '班级表',
  `teacher_id` int NULL DEFAULT NULL COMMENT '教师表',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  `subject_id` int NULL DEFAULT NULL COMMENT '教授学科',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '班级-教师关联表';

-- ----------------------------
-- Records of class_teacher
-- ----------------------------
BEGIN;
INSERT INTO `class_teacher` (`id`, `class_id`, `teacher_id`, `status`, `subject_id`) VALUES (1, 1, 33, 1, 2);
COMMIT;

-- ----------------------------
-- Table structure for class_user
-- ----------------------------
DROP TABLE IF EXISTS `class_user`;
CREATE TABLE `class_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `class_id` int NULL DEFAULT NULL COMMENT '班级id',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `status` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '班级-用户关联表';

-- ----------------------------
-- Records of class_user
-- ----------------------------
BEGIN;
INSERT INTO `class_user` (`id`, `class_id`, `user_id`, `status`) VALUES (6, 1, 37, 1);
COMMIT;

-- ----------------------------
-- Table structure for exam_class
-- ----------------------------
DROP TABLE IF EXISTS `exam_class`;
CREATE TABLE `exam_class`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `exam_id` int NULL DEFAULT NULL COMMENT '试卷id',
  `class_id` int NULL DEFAULT NULL COMMENT '班级id',
  `status` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '试卷-班级关联表';

-- ----------------------------
-- Records of exam_class
-- ----------------------------
BEGIN;
INSERT INTO `exam_class` (`id`, `exam_id`, `class_id`, `status`) VALUES (1, 11, 1, 1), (7, 16, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for exam_paper
-- ----------------------------
DROP TABLE IF EXISTS `exam_paper`;
CREATE TABLE `exam_paper`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '试卷名称',
  `subject_id` int NULL DEFAULT NULL COMMENT '学科id',
  `paper_type` int NULL DEFAULT NULL COMMENT '试卷类型(1.固定试卷 2.时段试卷 3.任务试卷)',
  `grade_level` int NULL DEFAULT NULL COMMENT '年级(1-12)',
  `score` int NULL DEFAULT NULL COMMENT '试卷总分(百分制)',
  `question_count` int NULL DEFAULT NULL COMMENT '题目数量',
  `suggest_time` int NULL DEFAULT NULL COMMENT '建议时长(分钟)',
  `limit_start_time` datetime NULL DEFAULT NULL COMMENT '时段试卷 开始时间',
  `limit_end_time` datetime NULL DEFAULT NULL COMMENT '时段试卷 结束时间',
  `frame_text_content_id` int NULL DEFAULT NULL COMMENT '试卷框架 ',
  `create_user` int NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `task_exam_id` int NULL DEFAULT NULL COMMENT '任务试卷id',
  `status` int NULL DEFAULT NULL COMMENT '状态（0.启用 1.终止）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '试卷表';

-- ----------------------------
-- Records of exam_paper
-- ----------------------------
BEGIN;
INSERT INTO `exam_paper` (`id`, `name`, `subject_id`, `paper_type`, `grade_level`, `score`, `question_count`, `suggest_time`, `limit_start_time`, `limit_end_time`, `frame_text_content_id`, `create_user`, `create_time`, `task_exam_id`, `status`) VALUES (11, '一年级第一学期语文综合测评', 2, 1, 1, 260, 10, 60, NULL, NULL, 77, 1, '2022-04-19 13:34:19', NULL, 1);
COMMIT;

-- ----------------------------
-- Table structure for exam_paper_answer
-- ----------------------------
DROP TABLE IF EXISTS `exam_paper_answer`;
CREATE TABLE `exam_paper_answer`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `exam_paper_id` int NULL DEFAULT NULL COMMENT '试卷id',
  `paper_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '试卷名称',
  `paper_type` int NULL DEFAULT NULL COMMENT '试卷类型(1.固定试卷 2.时段试卷 3.任务试卷)',
  `subject_id` int NULL DEFAULT NULL COMMENT '学科id',
  `system_score` int NULL DEFAULT NULL COMMENT '系统判定得分',
  `user_score` int NULL DEFAULT NULL COMMENT '最终得分',
  `paper_score` int NULL DEFAULT NULL COMMENT '试卷总分',
  `question_correct` int NULL DEFAULT NULL COMMENT '做对题目数量',
  `question_count` int NULL DEFAULT NULL COMMENT '题目总数量',
  `do_time` int NULL DEFAULT NULL COMMENT '做题时间(秒)',
  `status` int NULL DEFAULT NULL COMMENT '试卷状态(0.待判分 1.完成)',
  `create_user` int NULL DEFAULT NULL COMMENT '答题学生',
  `create_time` datetime NULL DEFAULT NULL COMMENT '提交时间',
  `task_exam_id` int NULL DEFAULT NULL COMMENT '任务试卷id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '试卷答案表';

-- ----------------------------
-- Records of exam_paper_answer
-- ----------------------------
BEGIN;
INSERT INTO `exam_paper_answer` (`id`, `exam_paper_id`, `paper_name`, `paper_type`, `subject_id`, `system_score`, `user_score`, `paper_score`, `question_correct`, `question_count`, `do_time`, `status`, `create_user`, `create_time`, `task_exam_id`) VALUES (20, 11, '一年级第一学期语文综合测评', 1, 2, 130, 190, 260, 5, 10, 59, 1, 37, '2022-04-19 13:58:49', NULL), (21, 16, '好生气', 4, 2, 70, 70, 70, 3, 3, 65, 1, 37, '2022-04-21 12:48:53', NULL), (22, 16, '好生气', 4, 2, 70, 70, 70, 3, 3, 65, 1, 37, '2022-04-21 12:49:12', NULL), (23, 11, '一年级第一学期语文综合测评', 1, 2, 0, 0, 260, 0, 10, 47, 1, 37, '2022-05-12 17:03:45', NULL), (24, 11, '一年级第一学期语文综合测评', 1, 2, 120, 120, 260, 4, 10, 24, 1, 37, '2022-05-12 17:04:36', NULL), (25, 11, '一年级第一学期语文综合测评', 1, 2, 70, 70, 260, 3, 10, 24, 1, 37, '2022-05-12 17:08:55', NULL);
COMMIT;

-- ----------------------------
-- Table structure for exam_paper_question_customer_answer
-- ----------------------------
DROP TABLE IF EXISTS `exam_paper_question_customer_answer`;
CREATE TABLE `exam_paper_question_customer_answer`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `question_id` int NULL DEFAULT NULL COMMENT '题目id',
  `exam_paper_id` int NOT NULL COMMENT '试卷id',
  `exam_paper_answer_id` int NULL DEFAULT NULL COMMENT '答卷id',
  `question_type` int NULL DEFAULT NULL COMMENT '问题类型',
  `subject_id` int NULL DEFAULT NULL COMMENT '学科id',
  `customer_score` int NULL DEFAULT NULL COMMENT '得分',
  `question_score` int NULL DEFAULT NULL COMMENT '题目原始分数',
  `question_text_content_id` int NULL DEFAULT NULL COMMENT '问题内容',
  `answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作题答案',
  `text_content_id` int NULL DEFAULT NULL COMMENT '做题内容',
  `do_right` bit(1) NULL DEFAULT NULL COMMENT '是否正确',
  `create_user` int NULL DEFAULT NULL COMMENT '做题人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '答题时间',
  `item_order` int NULL DEFAULT NULL COMMENT '题目序号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '试卷题目答案表';

-- ----------------------------
-- Records of exam_paper_question_customer_answer
-- ----------------------------
BEGIN;
INSERT INTO `exam_paper_question_customer_answer` (`id`, `question_id`, `exam_paper_id`, `exam_paper_answer_id`, `question_type`, `subject_id`, `customer_score`, `question_score`, `question_text_content_id`, `answer`, `text_content_id`, `do_right`, `create_user`, `create_time`, `item_order`) VALUES (58, 20, 11, 20, 1, 2, 30, 30, 65, 'A', NULL, b'1', 37, '2022-04-19 13:58:49', 1), (59, 22, 11, 20, 1, 2, 30, 30, 67, 'B', NULL, b'1', 37, '2022-04-19 13:58:49', 2), (60, 23, 11, 20, 1, 2, 30, 30, 68, 'A', NULL, b'1', 37, '2022-04-19 13:58:49', 3), (61, 26, 11, 20, 5, 2, 20, 30, 71, NULL, 78, b'0', 37, '2022-04-19 13:58:49', 4), (62, 21, 11, 20, 1, 2, 30, 30, 66, 'B', NULL, b'1', 37, '2022-04-19 13:58:49', 5), (63, 25, 11, 20, 5, 2, 20, 30, 70, NULL, 79, b'0', 37, '2022-04-19 13:58:49', 6), (64, 27, 11, 20, 2, 2, 0, 30, 72, 'A,B,C,D', NULL, b'0', 37, '2022-04-19 13:58:49', 7), (65, 30, 11, 20, 3, 2, 10, 10, 75, 'A', NULL, b'1', 37, '2022-04-19 13:58:49', 8), (66, 29, 11, 20, 4, 2, 10, 40, 74, NULL, 80, b'0', 37, '2022-04-19 13:58:49', 9), (67, 28, 11, 20, 4, 2, 10, 20, 73, NULL, 81, b'0', 37, '2022-04-19 13:58:49', 10), (68, 30, 16, 21, 3, 2, 10, 10, 75, 'A', NULL, b'1', 37, '2022-04-21 12:48:53', 1), (69, 20, 16, 21, 1, 2, 30, 30, 65, 'A', NULL, b'1', 37, '2022-04-21 12:48:53', 2), (70, 21, 16, 21, 1, 2, 30, 30, 66, 'B', NULL, b'1', 37, '2022-04-21 12:48:53', 3), (71, 30, 16, 22, 3, 2, 10, 10, 75, 'A', NULL, b'1', 37, '2022-04-21 12:49:12', 1), (72, 20, 16, 22, 1, 2, 30, 30, 65, 'A', NULL, b'1', 37, '2022-04-21 12:49:12', 2), (73, 21, 16, 22, 1, 2, 30, 30, 66, 'B', NULL, b'1', 37, '2022-04-21 12:49:12', 3), (74, 20, 11, 23, 1, 2, 0, 30, 65, NULL, NULL, b'0', 37, '2022-05-12 17:03:45', 1), (75, 22, 11, 23, 1, 2, 0, 30, 67, NULL, NULL, b'0', 37, '2022-05-12 17:03:45', 2), (76, 23, 11, 23, 1, 2, 0, 30, 68, NULL, NULL, b'0', 37, '2022-05-12 17:03:45', 3), (77, 26, 11, 23, 5, 2, 0, 30, 71, NULL, 88, b'0', 37, '2022-05-12 17:03:45', 4), (78, 21, 11, 23, 1, 2, 0, 30, 66, NULL, NULL, b'0', 37, '2022-05-12 17:03:45', 5), (79, 25, 11, 23, 5, 2, 0, 40, 70, NULL, 89, b'0', 37, '2022-05-12 17:03:45', 6), (80, 27, 11, 23, 2, 2, 0, 30, 72, '', NULL, b'0', 37, '2022-05-12 17:03:45', 7), (81, 30, 11, 23, 3, 2, 0, 10, 75, NULL, NULL, b'0', 37, '2022-05-12 17:03:45', 8), (82, 29, 11, 23, 4, 2, 0, 40, 74, NULL, 90, b'0', 37, '2022-05-12 17:03:45', 9), (83, 28, 11, 23, 4, 2, 0, 20, 73, NULL, 91, b'0', 37, '2022-05-12 17:03:45', 10), (84, 20, 11, 24, 1, 2, 30, 30, 65, 'A', NULL, b'1', 37, '2022-05-12 17:04:36', 1), (85, 22, 11, 24, 1, 2, 0, 30, 67, 'A', NULL, b'0', 37, '2022-05-12 17:04:36', 2), (86, 23, 11, 24, 1, 2, 30, 30, 68, 'A', NULL, b'1', 37, '2022-05-12 17:04:36', 3), (87, 26, 11, 24, 5, 2, 0, 30, 71, NULL, 92, b'0', 37, '2022-05-12 17:04:36', 4), (88, 21, 11, 24, 1, 2, 30, 30, 66, 'B', NULL, b'1', 37, '2022-05-12 17:04:36', 5), (89, 25, 11, 24, 5, 2, 0, 40, 70, NULL, 93, b'0', 37, '2022-05-12 17:04:36', 6), (90, 27, 11, 24, 2, 2, 30, 30, 72, 'A,B,C,D', NULL, b'1', 37, '2022-05-12 17:04:36', 7), (91, 30, 11, 24, 3, 2, 0, 10, 75, 'B', NULL, b'0', 37, '2022-05-12 17:04:36', 8), (92, 29, 11, 24, 4, 2, 0, 40, 74, NULL, 94, b'0', 37, '2022-05-12 17:04:36', 9), (93, 28, 11, 24, 4, 2, 0, 20, 73, NULL, 95, b'0', 37, '2022-05-12 17:04:36', 10), (94, 20, 11, 25, 1, 2, 30, 30, 65, 'A', NULL, b'1', 37, '2022-05-12 17:08:55', 1), (95, 22, 11, 25, 1, 2, 0, 30, 67, 'A', NULL, b'0', 37, '2022-05-12 17:08:55', 2), (96, 23, 11, 25, 1, 2, 30, 30, 68, 'A', NULL, b'1', 37, '2022-05-12 17:08:55', 3), (97, 26, 11, 25, 5, 2, 0, 30, 71, NULL, 96, b'0', 37, '2022-05-12 17:08:55', 4), (98, 21, 11, 25, 1, 2, 0, 30, 66, 'A', NULL, b'0', 37, '2022-05-12 17:08:55', 5), (99, 25, 11, 25, 5, 2, 0, 40, 70, NULL, 97, b'0', 37, '2022-05-12 17:08:55', 6), (100, 27, 11, 25, 2, 2, 0, 30, 72, 'B,C', NULL, b'0', 37, '2022-05-12 17:08:55', 7), (101, 30, 11, 25, 3, 2, 10, 10, 75, 'A', NULL, b'1', 37, '2022-05-12 17:08:55', 8), (102, 29, 11, 25, 4, 2, 0, 40, 74, NULL, 98, b'0', 37, '2022-05-12 17:08:55', 9), (103, 28, 11, 25, 4, 2, 0, 20, 73, NULL, 99, b'0', 37, '2022-05-12 17:08:55', 10);
COMMIT;

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip',
  `address` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登陆地址',
  `browser` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `login_time` datetime NULL DEFAULT NULL COMMENT '登录时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 385 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of login_log
-- ----------------------------
BEGIN;
INSERT INTO `login_log` (`id`, `user_name`, `ip`, `address`, `browser`, `login_time`) VALUES (114, 'admin', '192.168.52.3', ' 局域网', 'Chrome 100.0.4896.127', '2022-04-19 17:14:25'), (115, '084618233', '192.168.52.3', ' 局域网', 'Chrome 100.0.4896.127', '2022-04-19 17:15:21'), (116, 'admin', '192.168.52.3', ' 局域网', 'Chrome 100.0.4896.127', '2022-04-19 17:27:27'), (117, 'admin', '192.168.52.3', ' 局域网', 'Chrome 100.0.4896.127', '2022-04-19 17:42:15'), (118, 'teacher', '192.168.31.149', ' 局域网', 'Chrome 100.0.4896.127', '2022-04-19 17:57:21'), (119, 'teacher', '192.168.31.149', ' 局域网', 'Chrome 100.0.4896.127', '2022-04-19 21:43:50'), (120, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-20 09:34:18'), (121, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-20 09:34:18'), (122, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-20 09:34:43'), (123, '84618237', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-20 15:25:00'), (124, '84618237', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-21 12:40:48'), (125, '84618237', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-21 12:40:54'), (126, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-21 13:40:19'), (127, '84618237', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-21 13:47:45'), (128, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-23 12:31:12'), (129, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-23 12:31:14'), (130, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-24 11:18:45'), (131, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-26 09:41:08'), (132, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-26 17:57:56'), (133, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-27 09:38:04'), (134, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-27 17:57:16'), (135, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-27 17:58:25'), (136, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-27 17:59:48'), (137, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-27 18:01:05'), (138, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-27 18:05:11'), (139, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-27 18:08:40'), (140, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-28 10:03:58'), (141, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-28 10:03:59'), (142, 'student', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-28 15:30:17'), (143, 'student', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-29 09:58:28'), (144, 'student', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-04-29 09:58:30'), (145, 'student', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-05-01 10:30:58'), (146, 'student', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-05-01 10:30:58'), (147, 'student', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-05-01 10:30:58'), (148, 'student', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-05-01 10:31:25'), (149, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-05-01 11:46:23'), (150, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-05-01 17:29:21'), (151, 'teacher', '192.168.32.1', ' 局域网', 'Chrome 100.0.4896.127', '2022-05-01 19:23:53'), (152, 'teacher', '192.168.32.1', ' 局域网', 'Chrome 100.0.4896.127', '2022-05-01 19:36:15'), (153, 'student', '192.168.32.1', ' 局域网', 'Chrome 100.0.4896.127', '2022-05-01 19:36:35'), (154, 'student', '192.168.32.1', ' 局域网', 'Chrome 100.0.4896.127', '2022-05-01 19:40:25'), (155, 'student', '192.168.32.1', ' 局域网', 'Chrome 100.0.4896.127', '2022-05-01 19:40:31'), (156, 'teacher', '192.168.32.1', ' 局域网', 'Chrome 100.0.4896.127', '2022-05-01 19:41:00'), (157, 'student', '192.168.32.1', ' 局域网', 'Chrome 100.0.4896.127', '2022-05-01 19:41:13'), (158, 'admin', '192.168.32.1', ' 局域网', 'Chrome 100.0.4896.127', '2022-05-01 19:42:12'), (159, 'student', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-05-01 19:49:36'), (160, 'admin', '192.168.31.149', ' 局域网', 'Chrome 100.0.4896.127', '2022-05-01 20:05:26'), (161, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-05-01 20:33:29'), (162, 'admin', '192.168.32.1', ' 局域网', 'Chrome 100.0.4896.127', '2022-05-01 20:44:28'), (163, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-05-02 12:22:01'), (164, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-05-02 12:22:03'), (165, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-05-02 12:22:12'), (166, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-05-02 12:22:28'), (167, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-05-03 10:28:38'), (168, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-05-03 13:34:16'), (169, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-05-03 13:34:16'), (170, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-05-03 13:34:16'), (171, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-05-03 13:34:50'), (172, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-05-03 14:38:16'), (173, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-05-03 20:11:01'), (174, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-05-07 13:14:55'), (175, 'student', '169.254.211.99', ' 本地链路', 'Chrome 100.0.4896.127', '2022-05-07 15:15:27'), (176, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.54', '2022-05-11 09:25:17'), (177, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.54', '2022-05-11 09:25:17'), (178, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.54', '2022-05-11 09:25:17'), (179, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.54', '2022-05-11 10:09:07'), (180, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.54', '2022-05-11 10:17:20'), (181, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.64', '2022-05-12 13:52:02'), (182, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.64', '2022-05-12 15:18:00'), (183, 'student', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.64', '2022-05-12 16:59:11'), (184, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.64', '2022-05-12 18:35:36'), (185, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.64', '2022-05-12 19:09:02'), (186, 'admin', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-17 17:10:10'), (187, 'admin', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-17 17:10:16'), (188, 'admin', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-18 10:02:45'), (189, 'student', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-18 10:02:45'), (190, 'teacher', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-18 10:02:45'), (191, 'admin', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-18 10:02:48'), (192, 'teacher', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-18 10:02:50'), (193, 'student', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-18 13:00:30'), (194, 'student', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-18 13:04:55'), (195, 'admin', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-18 14:08:39'), (196, 'admin', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-18 14:37:56'), (197, 'admin', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-18 15:02:57'), (198, 'teacher', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-18 15:52:27'), (199, 'teacher', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-18 15:52:28'), (200, 'teacher', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-18 16:55:03'), (201, 'teacher', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-18 17:00:58'), (202, 'student', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.67', '2022-05-20 10:11:09'), (203, 'student', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.67', '2022-05-20 10:11:12'), (204, 'student', '192.168.31.149', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-20 12:50:20'), (205, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.67', '2022-05-22 10:04:28'), (206, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.67', '2022-05-22 10:04:29'), (207, 'student', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.67', '2022-05-22 15:31:50'), (208, 'student', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.67', '2022-05-23 10:02:15'), (209, 'student', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.67', '2022-05-23 10:02:43'), (210, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.67', '2022-05-23 10:09:54'), (211, 'student', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.67', '2022-05-23 10:12:31'), (212, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.67', '2022-05-23 10:25:33'), (213, 'student', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.67', '2022-05-23 13:01:42'), (214, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.67', '2022-05-23 13:02:27'), (215, 'student', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-24 12:32:26'), (216, 'teacher', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-24 12:32:31'), (217, 'admin', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-24 12:32:43'), (218, 'admin', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-24 15:06:12'), (219, 'admin', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-24 15:16:23'), (220, 'teacher', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-24 15:19:42'), (221, 'student', '192.168.31.1', ' 局域网', 'Chrome 101.0.4951.67', '2022-05-24 15:24:09'), (222, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.67', '2022-05-24 16:05:05'), (223, 'teacher', '169.254.211.99', ' 本地链路', 'Chrome 101.0.4951.67', '2022-05-24 17:45:53'), (224, 'admin', '192.168.31.149', ' 局域网', 'Chrome 103', '2022-07-19 15:48:24'), (225, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 103', '2022-07-19 15:48:24'), (226, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 103', '2022-07-19 15:48:24'), (227, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 103', '2022-07-19 15:48:24'), (228, 'admin', '192.168.31.149', ' 局域网', 'Chrome 103', '2022-07-19 15:48:24'), (229, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 103', '2022-07-19 15:48:24'), (230, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 103', '2022-07-19 15:48:24'), (231, 'admin', '192.168.31.149', ' 局域网', 'Chrome 103', '2022-07-19 15:48:24'), (232, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 103', '2022-07-19 15:48:55'), (233, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 103', '2022-07-19 15:52:59'), (234, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 103', '2022-07-19 15:54:48'), (235, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 103', '2022-07-19 16:01:24'), (236, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 103', '2022-07-19 16:01:24'), (237, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 103', '2022-07-19 16:02:14'), (238, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 103', '2022-07-19 16:20:45'), (239, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 103', '2022-07-19 16:23:26'), (240, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 16:25:19'), (241, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 16:25:51'), (242, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 16:28:20'), (243, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 16:29:38'), (244, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 16:30:14'), (245, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 17:09:40'), (246, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 17:09:40'), (247, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 17:11:12'), (248, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 17:11:13'), (249, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 17:12:12'), (250, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 17:12:13'), (251, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 17:16:41'), (252, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 17:16:41'), (253, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 17:18:40'), (254, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 17:18:40'), (255, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 17:21:55'), (256, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 17:21:56'), (257, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 17:22:57'), (258, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 17:22:58'), (259, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 17:23:18'), (260, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 17:23:19'), (261, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 17:23:19'), (262, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 18:24:12'), (263, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 18:24:12'), (264, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 18:24:13'), (265, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:32:15'), (266, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:32:15'), (267, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:32:15'), (268, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:34:13'), (269, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:34:14'), (270, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:34:14'), (271, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:39:32'), (272, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:39:32'), (273, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:39:32'), (274, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:42:25'), (275, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:42:25'), (276, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:42:26'), (277, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:48:34'), (278, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:48:34'), (279, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:48:34'), (280, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:56:34'), (281, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:56:34'), (282, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:56:34'), (283, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:58:53'), (284, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:58:53'), (285, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:58:54'), (286, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:58:54'), (287, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:58:54'), (288, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:58:55'), (289, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 19:58:55'), (290, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:09:55'), (291, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:09:55'), (292, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:09:55'), (293, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:13:09'), (294, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:13:09'), (295, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:13:09'), (296, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:15:11'), (297, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:15:11'), (298, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:15:12'), (299, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:15:34'), (300, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:15:34'), (301, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:15:34'), (302, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:19:20'), (303, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:19:20'), (304, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:19:20'), (305, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:26:17'), (306, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:26:17'), (307, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:26:17'), (308, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:29:29'), (309, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:29:29'), (310, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:29:29'), (311, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:30:05'), (312, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:30:05'), (313, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:30:06'), (314, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:32:09'), (315, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:32:10'), (316, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:32:10'), (317, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:33:00'), (318, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:33:00'), (319, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:33:00'), (320, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:34:38'), (321, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:34:38'), (322, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:34:39'), (323, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:35:11'), (324, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:35:12'), (325, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:35:13'), (326, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:35:51'), (327, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:35:52'), (328, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:35:52'), (329, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:38:09'), (330, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:38:09'), (331, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:38:10'), (332, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:45:13'), (333, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:45:13'), (334, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:45:14'), (335, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:51:42'), (336, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:51:42'), (337, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:51:43'), (338, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:51:43'), (339, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:51:43'), (340, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:51:44'), (341, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:51:44'), (342, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:57:50'), (343, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:57:50'), (344, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:57:50'), (345, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:58:49'), (346, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:58:49'), (347, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:58:49'), (348, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:58:49'), (349, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:58:50'), (350, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:58:50'), (351, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:58:50'), (352, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:58:52'), (353, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:58:52'), (354, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:58:52'), (355, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:58:52'), (356, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:58:53'), (357, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:58:53'), (358, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:58:53'), (359, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 20:58:53'), (360, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 21:23:24'), (361, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 21:23:24'), (362, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 21:23:24'), (363, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 21:23:25'), (364, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 21:23:25'), (365, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 21:23:25'), (366, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 21:23:26'), (367, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 21:23:26'), (368, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 21:23:26'), (369, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 21:23:26'), (370, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 21:23:27'), (371, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 21:23:28'), (372, 'admin', '169.254.211.99', ' 本地链路', 'Chrome 103', '2022-07-19 22:24:58'), (373, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 22:56:33'), (374, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 22:56:33'), (375, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 22:56:33'), (376, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 22:56:34'), (377, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 22:56:34'), (378, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 22:56:34'), (379, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 22:56:35'), (380, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 22:56:35'), (381, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 22:56:35'), (382, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 22:56:36'), (383, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 22:56:36'), (384, 'admin', '169.254.211.99', ' 本地链路', 'Python-Requests 2.28.1', '2022-07-19 22:56:36');
COMMIT;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `send_user_id` int NULL DEFAULT NULL COMMENT '发送者id',
  `send_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发送者用户名',
  `send_user_real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发送者真实姓名',
  `receive_user_count` int NULL DEFAULT NULL COMMENT '接收人数',
  `read_count` int NULL DEFAULT NULL COMMENT '已读人数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '消息表';

-- ----------------------------
-- Records of message
-- ----------------------------
BEGIN;
INSERT INTO `message` (`id`, `title`, `content`, `create_time`, `send_user_id`, `send_user_name`, `send_user_real_name`, `receive_user_count`, `read_count`) VALUES (6, '测试消息功能修复', '测试消息功能修复', '2022-05-17 17:59:05', 1, 'admin', '管理员', 2, 0);
COMMIT;

-- ----------------------------
-- Table structure for message_user
-- ----------------------------
DROP TABLE IF EXISTS `message_user`;
CREATE TABLE `message_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `message_id` int NULL DEFAULT NULL COMMENT '消息内容id',
  `receive_user_id` int NULL DEFAULT NULL COMMENT '接收人id',
  `receive_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '接收人用户名',
  `receive_user_real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '接收人真实姓名',
  `readed` bit(1) NULL DEFAULT NULL COMMENT '是否已读(0.未读 1.已读)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '发送时间',
  `read_time` datetime NULL DEFAULT NULL COMMENT '阅读时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户消息表';

-- ----------------------------
-- Records of message_user
-- ----------------------------
BEGIN;
INSERT INTO `message_user` (`id`, `message_id`, `receive_user_id`, `receive_user_name`, `receive_user_real_name`, `readed`, `create_time`, `read_time`) VALUES (13, 6, 33, 'teacher', '张三', b'1', '2022-05-17 17:59:05', NULL), (14, 6, 37, 'student', '李四', b'1', '2022-05-17 17:59:05', NULL);
COMMIT;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限名',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '资源定位符',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `status` int(1) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '状态(0.有效 1.无效)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `category_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 156 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表';

-- ----------------------------
-- Records of permission
-- ----------------------------
BEGIN;
INSERT INTO `permission` (`id`, `name`, `url`, `description`, `status`, `create_time`, `category_id`) VALUES (1, '管理员', '/api/**', 'de', 1, '2022-01-17 10:27:04', 1), (10, '教师端', '/api/teacher/**', '包含教师端全部权限', 1, '2022-05-02 14:19:37', 1), (14, '学生端全部资源', '/api/student/**', '用于学生角色使用', 1, '2022-05-03 17:36:18', 1), (15, '获取消息列表', '/api/message/list', '{   \"limit\": 0,   \"page\": 0,   \"receiveUserId\": 0,   \"sendUser\": \"string\" }', 1, '2022-05-22 10:38:15', 2), (16, ' 标记消息已读', '/api/message/read/{id}', NULL, 1, '2022-05-22 10:38:50', 2), (17, ' 根据id获取未读消息', '/api/message/unread/count', NULL, 1, '2022-05-22 10:39:03', 2), (18, '获取学生按照时间排序的考试结果', '/api/student/analysis/result/{id}', NULL, 1, '2022-05-22 10:39:22', 3), (19, ' 获取指定年级的视频', '/api/student/video/level', NULL, 1, '2022-05-22 10:39:37', 4), (20, ' 获取视频列表', '/api/student/video/list', NULL, 1, '2022-05-22 10:39:56', 4), (21, '播放视频', '/api/student/video/preview/{path}', NULL, 1, '2022-05-22 10:40:13', 4), (22, '选择视频', '/api/student/video/select/{id}', NULL, 1, '2022-05-22 10:40:28', 4), (23, ' 批改列表', '/api/student/exam/answer/complete/list', NULL, 1, '2022-05-22 10:40:40', 5), (24, ' 自主阅卷', '/api/student/exam/answer/edit', NULL, 1, '2022-05-22 10:40:51', 5), (25, ' 考试期间获取答案缓存', '/api/student/exam/answer/get/{id}', NULL, 1, '2022-05-22 10:41:01', 5), (26, '学生查看试卷详情', '/api/student/exam/answer/read/{id}', NULL, 1, '2022-05-22 10:41:12', 5), (27, '个人考试记录', '/api/student/exam/answer/record/list', NULL, 1, '2022-05-22 10:41:22', 5), (28, '考试期间设置答案缓存', '/api/student/exam/answer/set/{id}', NULL, 1, '2022-05-22 10:41:39', 5), (29, '学生提交考试答案', '/api/student/exam/answer/submit', NULL, 1, '2022-05-22 10:41:50', 5), (30, '学生考试', '/api/student/exam/do/{id}', NULL, 1, '2022-05-22 10:42:06', 6), (31, ' 获取学生所属的试卷', '/api/student/exam/paper/own', NULL, 1, '2022-05-22 10:42:18', 6), (32, '试卷中心', '/api/student/exam/paper/page', NULL, 1, '2022-05-22 10:42:28', 6), (33, '获取任务试卷', '/api/student/exam/paper/task', NULL, 1, '2022-05-22 10:42:41', 6), (34, '选择试卷', '/api/student/exam/select/{id}', NULL, 1, '2022-05-22 10:42:56', 6), (35, '选择题目答案', '/api/student/question/answer/select/{id}', NULL, 1, '2022-05-22 10:43:23', 7), (36, ' 获取试卷错题列表', '/api/student/question/false/{id}', NULL, 1, '2022-05-22 10:43:31', 7), (37, ' 获取学生的错题', '/api/student/question/false/list', NULL, 1, '2022-05-22 10:43:41', 7), (38, '成绩分析--简单统计', '/api/exam/analysis/statistics', NULL, 1, '2022-05-22 10:43:54', 8), (39, '成绩分析-学生考试情况统计', '/api/exam/analysis/statistics/student', NULL, 1, '2022-05-22 10:44:04', 8), (40, '成绩分析-考生成绩', '/api/exam/analysis/student/list', NULL, 1, '2022-05-22 10:44:14', 8), (41, '教师发送消息', '/api/teacher/message/send', NULL, 1, '2022-05-22 10:44:27', 9), (42, '消息发送记录', '/api/teacher/message/send/history', NULL, 1, '2022-05-22 10:44:40', 9), (43, '获取试卷id对应的班级', '/api/teacher/class/analyse/list/{id}', NULL, 1, '2022-05-22 10:44:50', 10), (44, '添加或编辑班级', '/api/teacher/class/edit', NULL, 1, '2022-05-22 10:44:58', 10), (45, '班级列表', '/api/teacher/class/list', NULL, 1, '2022-05-22 10:45:09', 10), (46, '获取班级学生id用户发送消息', ' /api/teacher/class/message/student/{id}', NULL, 1, '2022-05-22 10:45:35', 10), (47, '分页获取班级列表', '/api/teacher/class/page', NULL, 1, '2022-05-22 10:45:44', 10), (48, '查询班级学生列表', '/api/teacher/class/student/{id}', NULL, 1, '2022-05-22 10:45:52', 10), (49, '教师阅卷', '/api/teacher/exam/answer/edit', NULL, 1, '2022-05-22 10:46:03', 11), (50, ' 教师阅卷', '/api/teacher/exam/answer/read/{id}', NULL, 1, '2022-05-22 10:46:12', 11), (51, '获取班级某张试卷的考试记录', '/api/teacher/exam/answer/record', NULL, 1, '2022-05-22 10:46:21', 11), (52, ' 教师查看班级学生提交的试卷', '/api/teacher/exam/answer/record/list', NULL, 1, '2022-05-22 10:46:30', 11), (53, '教师创建试卷', '/api/teacher/exam/create', NULL, 1, '2022-05-22 10:46:42', 12), (54, ' 删除试卷', '/api/teacher/exam/delete/{id}', NULL, 1, '2022-05-22 10:46:54', 12), (55, '教师端获取试卷列表', ' /api/teacher/exam/list', NULL, 1, '2022-05-22 10:47:05', 12), (56, ' 教师自己创建的试卷', '/api/teacher/exam/list/my', NULL, 1, '2022-05-22 10:47:18', 12), (57, '获取班级试卷', '/api/teacher/exam/paper/class/{id}', NULL, 1, '2022-05-22 10:47:28', 12), (58, ' 教师进行成绩分析查看列表', '/api/teacher/exam/result/list', NULL, 1, '2022-05-22 10:47:37', 12), (59, '选择查看试卷', '/api/teacher/exam/select/{id}', NULL, 1, '2022-05-22 10:47:47', 12), (60, ' 更新试卷', ' /api/teacher/exam/update', NULL, 1, '2022-05-22 10:47:56', 12), (61, ' 教师管理的班级总数和总人数', '/api/teacher/dash/info', NULL, 1, '2022-05-22 10:48:08', 13), (62, ' 选择题目答案', '/api/admin/question/answer/select/{id}', NULL, 1, '2022-05-22 10:48:21', 14), (63, '添加题目', '/api/admin/question/create', NULL, 1, '2022-05-22 10:48:28', 14), (64, ' 删除题目', '/api/admin/question/delete/{id}', NULL, 1, '2022-05-22 10:48:38', 14), (65, ' 编辑题目', '/api/admin/question/edit', NULL, 1, '2022-05-22 10:48:47', 14), (66, ' 题目列表', '/api/admin/question/list', NULL, 1, '2022-05-22 10:49:37', 14), (67, ' 选择题目', '/api/admin/question/select/{id}', NULL, 1, '2022-05-22 10:49:49', 14), (68, ' 更新题目', '/api/admin/question/update', NULL, 1, '2022-05-22 10:50:11', 14), (69, ' 获取全部学科', '/api/admin/subject/all', NULL, 1, '2022-05-22 10:50:30', 15), (70, ' 添加学科', '/api/admin/subject/create', NULL, 1, '2022-05-22 10:50:39', 15), (71, ' 删除学科', '/api/admin/subject/delete/{id}', NULL, 1, '2022-05-22 10:50:47', 15), (72, ' 获取学科列表', '/api/admin/subject/list', NULL, 1, '2022-05-22 10:50:58', 15), (73, ' 获取学科', '/api/admin/subject/name', NULL, 1, '2022-05-22 10:51:08', 15), (74, '查询学科', '/api/admin/subject/select/{id}', NULL, 1, '2022-05-22 10:51:16', 15), (75, ' 修改学科状态', '/api/admin/subject/status/{id}', NULL, 1, '2022-05-22 10:51:24', 15), (76, '获取教师教授学科', '/api/admin/subject/teacher/all', NULL, 1, '2022-05-22 10:51:32', 15), (77, ' 更新学科', '/api/admin/subject/update/{id}', NULL, 1, '2022-05-22 10:51:43', 15), (78, ' 添加权限目录', '/api/admin/category/create', NULL, 1, '2022-05-22 10:51:55', 16), (79, '权限目录删除', '/api/admin/category/delete/{id}', NULL, 1, '2022-05-22 10:52:07', 16), (80, ' 获取资源类别', '/api/admin/category/list', NULL, 1, '2022-05-22 10:52:15', 16), (81, ' 分页获取资源类别', '/api/admin/category/page', NULL, 1, '2022-05-22 10:52:23', 16), (82, ' 更新权限目录', '/api/admin/category/update/{id}', NULL, 1, '2022-05-22 10:52:31', 16), (83, '获取全部权限', '/api/admin/permission/all', NULL, 1, '2022-05-22 10:52:47', 17), (84, '根据资源类别获取权限列表', '/api/admin/permission/category', NULL, 1, '2022-05-22 10:52:54', 17), (85, '添加权限', '/api/admin/permission/create', NULL, 1, '2022-05-22 10:53:01', 17), (86, ' 删除权限', '/api/admin/permission/delete/{id}', NULL, 1, '2022-05-22 10:53:11', 17), (87, '获取权限列表', '/api/admin/permission/list', NULL, 1, '2022-05-22 10:53:22', 17), (88, ' 根据角色id获取权限列表', '/api/admin/permission/role', NULL, 1, '2022-05-22 10:53:32', 17), (89, ' 查询权限', '/api/admin/permission/search', NULL, 1, '2022-05-22 10:53:41', 17), (90, '更新权限有效状态', '/api/admin/permission/status/{id}', NULL, 1, '2022-05-22 10:53:51', 17), (91, ' 更新权限', '/api/admin/permission/update', NULL, 1, '2022-05-22 10:53:59', 17), (92, ' 管理员消息列表', '/api/admin/message/admin/list', NULL, 1, '2022-05-22 10:54:22', 18), (93, ' 发送全体消息', '/api/admin/message/send', NULL, 1, '2022-05-22 10:54:30', 18), (94, '删除班级', '/api/admin/class/delete/{id}', NULL, 1, '2022-05-22 10:54:40', 19), (95, ' 添加或编辑班级', '/api/admin/class/edit', NULL, 1, '2022-05-22 10:54:47', 10), (96, ' 获取用户操作日志', '/api/admin/user/event/log/list', NULL, 1, '2022-05-22 10:55:24', 20), (97, ' 获取管理员列表', ' /api/admin/user/admin/list', NULL, 1, '2022-05-22 10:55:36', 21), (98, '删除用户', '/api/admin/user/delete/{id}', NULL, 1, '2022-05-22 10:55:44', 21), (99, ' 获取全部用户列表', '/api/admin/user/list', NULL, 1, '2022-05-22 10:55:52', 20), (100, ' 管理员修改其他用户密码', '/api/admin/user/password/{id}', NULL, 1, '2022-05-22 10:56:01', 21), (101, ' 选择用户', '/api/admin/user/select/{id}', NULL, 1, '2022-05-22 10:56:08', 21), (102, '根据用户名模糊搜索用户', '/api/admin/user/selectByUsername', NULL, 1, '2022-05-22 10:56:17', 21), (103, '更新用户有效状态', ' /api/admin/user/status/{id}', NULL, 1, '2022-05-22 10:56:25', 21), (104, ' 获取学生列表', '/api/admin/user/student/list', NULL, 1, '2022-05-22 10:56:33', 21), (105, ' 获取教师列表', '/api/admin/user/teacher/list', NULL, 1, '2022-05-22 10:56:41', 21), (106, '批量上传用户', '/api/admin/user/upload', NULL, 1, '2022-05-22 10:56:49', 21), (107, '查询登录日志', '/api/admin/log/login/last', NULL, 1, '2022-05-22 10:56:59', 22), (108, '批改列表', '/api/admin/exam/answer/complete/list', NULL, 1, '2022-05-22 10:57:26', 23), (109, ' 管理员阅卷', '/api/admin/exam/answer/edit', NULL, 1, '2022-05-22 10:57:35', 23), (110, '查看试卷', '/api/admin/exam/answer/read/{id}', NULL, 1, '2022-05-22 10:57:43', 23), (111, ' 管理员查看全部考试记录', '/api/admin/exam/answer/record/list', NULL, 1, '2022-05-22 10:57:52', 23), (112, '删除视频', '/api/video/delete/{id}', NULL, 1, '2022-05-22 10:58:00', 24), (113, ' 视频列表', '/api/video/list', NULL, 1, '2022-05-22 10:59:17', 24), (114, ' 播放视频', '/api/video/preview/{path}', NULL, 1, '2022-05-22 10:59:30', 24), (115, '上传视频', '/api/video/upload', NULL, 1, '2022-05-22 10:59:40', 24), (116, ' 添加权限角色关联', '/api/admin/rp/create', NULL, 1, '2022-05-22 10:59:51', 25), (117, ' 删除权限角色关联', '/api/admin/rp/delete', NULL, 1, '2022-05-22 11:00:00', 25), (118, ' 获取角色权限表', '/api/admin/rp/list', NULL, 1, '2022-05-22 11:00:08', 25), (119, ' 用户全部权限', ' /api/admin/rp/listAll', NULL, 1, '2022-05-22 11:00:17', 25), (120, '查询权限角色关联', '/api/admin/rp/search', NULL, 1, '2022-05-22 11:00:28', 25), (121, ' 更新权限角色关联', '/api/admin/rp/update', NULL, 1, '2022-05-22 11:00:36', 25), (122, ' 添加角色', '/api/admin/role/create', NULL, 1, '2022-05-22 11:00:46', 26), (123, '删除角色', '/api/admin/role/delete', NULL, 1, '2022-05-22 11:00:57', 26), (124, '获取角色列表', '/api/admin/role/list', NULL, 1, '2022-05-22 11:01:05', 26), (125, ' 更新角色有效状态', '/api/admin/role/status', NULL, 1, '2022-05-22 11:01:13', 26), (126, '更新角色信息', '/api/admin/role/update', NULL, 1, '2022-05-22 11:01:20', 26), (127, '成绩分析', '/api/admin/exam/analyze', NULL, 1, '2022-05-22 11:01:30', 27), (128, ' 管理员创建试卷', '/api/admin/exam/create', NULL, 1, '2022-05-22 11:01:38', 27), (129, '试卷列表', '/api/admin/exam/list', NULL, 1, '2022-05-22 11:02:01', 27), (130, ' 获取用户所属的试卷', '/api/admin/exam/paper/own', NULL, 1, '2022-05-22 11:02:11', 27), (131, ' 获取任务试卷', ' /api/admin/exam/paper/task', NULL, 1, '2022-05-22 11:02:18', 27), (132, ' 选择查看试卷', ' /api/admin/exam/select/', NULL, 1, '2022-05-22 11:02:28', 27), (133, ' 改变试卷有效状态', '/api/admin/exam/status/', NULL, 1, '2022-05-22 11:02:36', 27), (134, '管理员- 更新试卷', '/api/admin/exam/update', NULL, 1, '2022-05-22 11:02:56', 27), (135, '基础数据统计', '/api/admin/dash/base/count', NULL, 1, '2022-05-22 11:03:14', 28), (136, ' 登录日志统计分析', '/api/admin/dash/log', NULL, 1, '2022-05-22 11:03:21', 28), (137, '近期新建题目数量', ' /api/admin/dash/question', NULL, 1, '2022-05-22 11:03:29', 28), (138, '系统信息', '/api/admin/monitor/server', NULL, 1, '2022-05-22 11:03:40', 29), (139, ' 权限分配', '/api/admin/permission/allocate', NULL, 1, '2022-05-22 11:03:48', 29), (140, ' 添加教师', '/api/admin/teacher/add/{id}', NULL, 1, '2022-05-22 11:04:00', 29), (141, '移除教师', '/api/admin/teacher/remove', NULL, 1, '2022-05-22 11:04:10', 29), (142, ' 分配角色', '/api/admin/user/role', NULL, 1, '2022-05-22 11:04:20', 29), (143, '恢复帐户', '/api/admin/user/status/able', NULL, 1, '2022-05-22 11:04:33', 29), (144, ' 禁用帐户', ' /api/admin/user/status/disable/**', NULL, 1, '2022-05-22 11:04:44', 29), (145, ' 上传图片', '/api/image/upload', NULL, 1, '2022-05-22 11:05:03', 30), (146, ' 获取用户信息', '/api/info', NULL, 1, '2022-05-22 11:05:10', 30), (147, ' 登录', '/api/login', NULL, 1, '2022-05-22 11:05:17', 30), (148, ' 登出', '/api/logout', NULL, 1, '2022-05-22 11:05:24', 30), (149, ' 用户获取收到的消息数量', ' /api/message/count', NULL, 1, '2022-05-22 11:05:31', 30), (150, ' 刷新token', '/api/refreshToken', NULL, 1, '2022-05-22 11:05:37', 30), (151, ' 更新头像', '/api/user/avatar/save', NULL, 1, '2022-05-22 11:05:45', 30), (152, '根据id获取用户信息', '/api/user/info/**', NULL, 1, '2022-05-22 11:05:55', 30), (153, ' 更新密码', '/api/user/password/edit', NULL, 1, '2022-05-22 11:06:04', 30), (154, ' 更新用户信息', ' /api/user/update', NULL, 1, '2022-05-22 11:06:13', 30);
COMMIT;

-- ----------------------------
-- Table structure for permission_category
-- ----------------------------
DROP TABLE IF EXISTS `permission_category`;
CREATE TABLE `permission_category`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of permission_category
-- ----------------------------
BEGIN;
INSERT INTO `permission_category` (`id`, `name`, `sort`, `create_time`) VALUES (1, '系统管理', 1, '2022-05-17 17:59:05'), (2, '学生端消息管理模块', 3, '2022-05-22 10:37:08'), (3, '学生端成绩分析模块', 2, '2022-05-22 11:07:25'), (4, '学生端视频管理模块', 4, '2022-05-22 11:07:37'), (5, '学生端试卷答案管理模块', 5, '2022-05-22 11:07:48'), (6, '学生端试卷管理模块', 6, '2022-05-22 11:07:56'), (7, '学生端题目管理模块', 7, '2022-05-22 11:08:04'), (8, '教师端成绩分析模块', 8, '2022-05-22 11:08:13'), (9, '教师端消息管理模块', 9, '2022-05-22 11:08:20'), (10, '教师端班级管理模块', 10, '2022-05-22 11:08:29'), (11, '教师端试卷答案管理模块', 11, '2022-05-22 11:08:37'), (12, '教师端试卷管理模块', 12, '2022-05-22 11:08:46'), (13, '教师端首页模块', 13, '2022-05-22 11:08:55'), (14, '管理员、教师通用题目管理模块', 14, '2022-05-22 11:09:27'), (15, '管理员端学科管理模块', 15, '2022-05-22 11:09:37'), (16, '管理员端权限目录管理模块', 16, '2022-05-22 11:09:46'), (17, '管理员端权限管理模块', 17, '2022-05-22 11:09:54'), (18, '管理员端消息管理模块', 18, '2022-05-22 11:10:04'), (19, '管理员端班级班级模块', 19, '2022-05-22 11:10:13'), (20, '管理员端用户操作日志', 20, '2022-05-22 11:10:20'), (21, '管理员端用户管理模块', 21, '2022-05-22 11:10:30'), (22, '管理员端登录日志管理模块', 22, '2022-05-22 11:10:37'), (23, '管理员端考试答案管理模块', 23, '2022-05-22 11:10:46'), (24, '管理员端视频管理模块', 24, '2022-05-22 11:10:52'), (25, '管理员端角色权限关联模块', 25, '2022-05-22 11:11:00'), (26, '管理员端角色管理模块', 26, '2022-05-22 11:11:10'), (27, '管理员端试卷管理模块', 27, '2022-05-22 11:11:17'), (28, '管理员端首页模块', 28, '2022-05-22 11:11:24'), (29, '系统管理员模块', 29, '2022-05-22 11:11:32'), (30, '通用模块', 30, '2022-05-22 11:11:41');
COMMIT;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `question_type` int NULL DEFAULT NULL COMMENT '问题类型(1.单选题 2.多选题 3.判断题 4.填空题 5.简答题)',
  `subject_id` int NULL DEFAULT NULL COMMENT '学科id',
  `score` int NULL DEFAULT NULL COMMENT '题目总分',
  `grade_level` int NULL DEFAULT NULL COMMENT '年级',
  `difficult` int NULL DEFAULT NULL COMMENT '难度系数',
  `correct` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '正确答案',
  `info_text_content_id` int NULL DEFAULT NULL COMMENT '题目 填空、题干、解析、答案等信息',
  `create_user` int NULL DEFAULT NULL COMMENT '创建人',
  `status` int NULL DEFAULT NULL COMMENT '状态(0.有效 1.无效)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 90 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '题目表';

-- ----------------------------
-- Records of question
-- ----------------------------
BEGIN;
INSERT INTO `question` (`id`, `question_type`, `subject_id`, `score`, `grade_level`, `difficult`, `correct`, `info_text_content_id`, `create_user`, `status`, `create_time`) VALUES (19, 1, 1, 40, 1, 5, 'A', 64, 1, 1, '2022-04-19 13:13:52'), (20, 1, 2, 30, 1, 3, 'A', 65, 1, 1, '2022-04-19 13:15:45'), (21, 1, 2, 30, 1, 3, 'B', 66, 1, 1, '2022-04-19 13:16:43'), (22, 1, 2, 30, 1, 3, 'B', 67, 1, 1, '2022-04-19 13:18:04'), (23, 1, 2, 30, 1, 3, 'A', 68, 1, 1, '2022-04-19 13:19:04'), (24, 1, 1, 30, 1, 3, 'D', 69, 1, 1, '2022-04-19 13:19:48'), (25, 5, 2, 40, 1, 4, '可用于描写某些人经过艰难困苦的生活折磨或经过长途跋涉之后的外貌', 70, 1, 1, '2022-04-19 13:21:17'), (26, 5, 2, 30, 1, 3, '是什么原因（让你）谨守美好的品德,而使自己被流放呢?', 71, 1, 1, '2022-04-19 13:22:02'), (27, 2, 2, 30, 1, 3, 'A,B,C,D', 72, 1, 1, '2022-04-19 13:24:19'), (28, 4, 2, 20, 1, 3, '', 73, 1, 1, '2022-04-19 13:29:31'), (29, 4, 2, 40, 1, 3, '', 74, 1, 1, '2022-04-19 13:31:32'), (30, 3, 2, 10, 1, 2, 'A', 75, 1, 1, '2022-04-19 13:32:23');
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` int NULL DEFAULT NULL COMMENT '角色编码(1.学生 2.教师 3.管理员 4.系统管理员)',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
  `status` int NULL DEFAULT NULL COMMENT '角色状态(0.有效 1.无效)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `user_count` int NULL DEFAULT NULL COMMENT '后台用户数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` (`id`, `code`, `description`, `name`, `status`, `create_time`, `update_time`, `user_count`) VALUES (1, 1, '系统管理员', 'admin', 1, '2022-01-17 13:46:12', '2022-05-12 14:54:01', 1), (2, 2, '教师', 'teacher', 1, '2022-01-17 13:46:12', '2022-04-19 15:38:30', 0), (3, 3, '学生', 'student', 1, '2022-01-17 13:46:12', '2022-01-17 13:46:12', 0);
COMMIT;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` int NULL DEFAULT NULL COMMENT '权限id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限关联表';

-- ----------------------------
-- Records of role_permission
-- ----------------------------
BEGIN;
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`, `create_time`, `status`) VALUES (1, 1, 1, '2022-01-27 13:11:57', 1), (13, 2, 1, '2022-01-27 13:11:57', 1), (14, 3, 1, '2022-01-27 13:11:57', 1);
COMMIT;

-- ----------------------------
-- Table structure for subject
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学科名称',
  `level` int NULL DEFAULT NULL COMMENT '年级',
  `level_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '年级名称',
  `item_order` int NULL DEFAULT NULL COMMENT '排序',
  `status` int NULL DEFAULT NULL COMMENT '学科状态(0.有效 1.无效)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学科表';

-- ----------------------------
-- Records of subject
-- ----------------------------
BEGIN;
INSERT INTO `subject` (`id`, `name`, `level`, `level_name`, `item_order`, `status`) VALUES (1, '英语', 1, '一年级', 3, 1), (2, '语文', 1, '一年级', 1, 1), (3, '数学', 1, '一年级', 4, 1), (4, '化学', 1, '一年级', 4, 1), (5, '地理', 1, '一年级', 4, 1), (6, '生物', 1, '一年级', 4, 1);
COMMIT;

-- ----------------------------
-- Table structure for task_exam
-- ----------------------------
DROP TABLE IF EXISTS `task_exam`;
CREATE TABLE `task_exam`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `grade_level` int NULL DEFAULT NULL COMMENT '年级',
  `frame_text_content_id` int NULL DEFAULT NULL COMMENT '任务框架id',
  `create_user` int NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `status` int(255) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '状态(0.有效 1.无效)',
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人用户名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '任务表';

-- ----------------------------
-- Records of task_exam
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for task_exam_customer_answer
-- ----------------------------
DROP TABLE IF EXISTS `task_exam_customer_answer`;
CREATE TABLE `task_exam_customer_answer`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `task_exam_id` int NULL DEFAULT NULL COMMENT '任务id',
  `create_user` int NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `text_content_id` int NULL DEFAULT NULL COMMENT '内容id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户任务表';

-- ----------------------------
-- Records of task_exam_customer_answer
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for text_content
-- ----------------------------
DROP TABLE IF EXISTS `text_content`;
CREATE TABLE `text_content`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 157 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文本表';

-- ----------------------------
-- Records of text_content
-- ----------------------------
BEGIN;
INSERT INTO `text_content` (`id`, `content`, `create_time`) VALUES (64, '{\"questionItemObjects\":[{\"prefix\":\"A\",\"content\":\"english teacher\"},{\"prefix\":\"B\",\"content\":\"math teacher\"},{\"prefix\":\"C\",\"content\":\"history teacher\"},{\"prefix\":\"D\",\"content\":\"chinese tacher\"}],\"correct\":\"A\",\"analyze\":\"English teacher must to be your best love teacher\",\"titleContent\":\"who is your  best love teacher?\"}', '2022-04-19 13:13:52'), (65, '{\"questionItemObjects\":[{\"prefix\":\"A\",\"content\":\"险衅（xìn）　　陨（yùn）首　日笃（dǔ）　笨拙（zhuó\"},{\"prefix\":\"B\",\"content\":\"先妣（pǐ）　坍圮（tān）　猝（cù）然　熨帖（yù）　\"},{\"prefix\":\"C\",\"content\":\"菲（fěi）薄　陶潜（qiàn） 自咎（jiù）　离间（jiàn）　\"},{\"prefix\":\"D\",\"content\":\"当（dàng）做　戕（qiāng）灭　　聒（guō）噪　戏谑（xuè）\"}],\"correct\":\"A\",\"analyze\":\"略\",\"titleContent\":\"下列词语注音全都正确的一项是（　　）\"}', '2022-04-19 13:15:45'), (66, '{\"questionItemObjects\":[{\"prefix\":\"A\",\"content\":\"沐浴　逼迫　　狼狈　　簿西山\"},{\"prefix\":\"B\",\"content\":\"祈祷　安祥　　寥落　　委屈求全\"},{\"prefix\":\"C\",\"content\":\"社稷　擎天　　张皇　　平心而论\"},{\"prefix\":\"D\",\"content\":\"箴言　窒息　　譬如　　专心至志\"}],\"correct\":\"B\",\"analyze\":\"简单，请自行分析\",\"titleContent\":\"下列各组词语中，没有错别字的一组是（　　）　　\"}', '2022-04-19 13:16:43'), (67, '{\"questionItemObjects\":[{\"prefix\":\"A\",\"content\":\"面世　甄别 　　远景 　　或者\"},{\"prefix\":\"B\",\"content\":\"问世　遴选 　　愿景 　　甚至\"},{\"prefix\":\"C\",\"content\":\"问世　甄别 　　远景 　　或者\"},{\"prefix\":\"D\",\"content\":\"面世　遴选 　　愿景 　　甚至\"}],\"correct\":\"B\",\"analyze\":\"认真作答\",\"titleContent\":\"依次填入下列横线处的词语，最恰当的一组是（　　）\\n\\n（1）今天，武警森林部队自行研制的05系列灭火防护服，经过耐高温检验达到专家验收标准。新型防火“铠甲”的________，让战斗在扑火一线的武警森林官兵如虎添翼。\\n\\n（2）中宣部、中央文明办和受赠八省（区、市）从今年3月就开始精心________图书和光盘的种类，确定受赠对象和名单并组织采购和运输。\\n\\n（3）青年一代与国家社会的同步成长，这依然是一个让人平添责任感、紧迫感和使命感，需要付出巨大的心血和汗水才能实现的________，是无数仁人志士用心耕耘之后才能换来的结果。\\n\\n（4）但我们必须承认，我们和日本的科技水平整体差距还很大。要想赶超日本，我们还需要很多年，________几代人的努力。\"}', '2022-04-19 13:18:04'), (68, '{\"questionItemObjects\":[{\"prefix\":\"A\",\"content\":\"当今世界散打擂台上，没有谁能和柳海龙分庭抗礼。\"},{\"prefix\":\"B\",\"content\":\"听一则奇志、大兵的相声，可以使我们的烦恼涣然冰释。\"},{\"prefix\":\"C\",\"content\":\"这种创造性的艺术作品充分反映了战国时期劳动人民炉火纯青的聪明智慧\"},{\"prefix\":\"D\",\"content\":\"我们班众多的莘莘学子经过高中三年刻苦学习，都取得了理想的成绩，其中有十多位同学考入了重点大学\"}],\"correct\":\"A\",\"analyze\":\"一拳打飞\",\"titleContent\":\"下列句子中加点的成语，使用恰当的一项是\"}', '2022-04-19 13:19:04'), (69, '{\"questionItemObjects\":[{\"prefix\":\"A\",\"content\":\"新建京沪高速铁路将对京沪铁路运力长期严重紧张局面、促进我国经济社会发展尤其是东部地区发展具有重要意义。\"},{\"prefix\":\"B\",\"content\":\"这种以社区居民楼院为中心，沿街楼房一二层为商业服务用房，集餐忺、购物、文体、教育、卫生、家教中介、资源回收等八项服务功能于一体，将成为我省未来推广的重点。\"},{\"prefix\":\"C\",\"content\":\"组委会还将举办齐鲁（国际）原创动漫作品大赛，设立齐鲁动漫发展论坛，使山东乃至国内原创动漫成就得到最大程度的宣传与展示。\"},{\"prefix\":\"D\",\"content\":\"写考场作文，语言是很重要的，可审题也一样不可小视，准确地理解题意往往是一篇作文成败的关键\"}],\"correct\":\"D\",\"analyze\":\"自学\",\"titleContent\":\"下列各句中，没有语病的一句是\"}', '2022-04-19 13:19:48'), (70, '{\"questionItemObjects\":[],\"correct\":\"可用于描写某些人经过艰难困苦的生活折磨或经过长途跋涉之后的外貌\",\"analyze\":\"颜色：主要指面容。憔悴（qiǎo cuì 樵脆）：瘦弱萎靡的样子。形容：兼指面容及形体。枯槁（gǎo 搞）：枯瘦羸弱。这两句大意是：面容灰暗萎靡，形体干枯瘦弱。\\n\\n　　\\n这两句中，“颜色”与“形容”义近，“憔悴”与“枯槁”义近，两句连用，极言人面黄肌瘦、形销骨立，从面目到神情都疲惫不堪，萎靡不振。可用于描写某些人经过艰难困苦的生活折磨或经过长途跋涉之后的外貌。\",\"titleContent\":\"翻译：\\n颜色憔悴，形容枯槁。\"}', '2022-04-19 13:21:17'), (71, '{\"questionItemObjects\":[],\"correct\":\"是什么原因（让你）谨守美好的品德,而使自己被流放呢?\",\"analyze\":\"怀瑾握瑜 ( huái jǐn wò yú ) 解 释 瑾、瑜：美玉.比喻人具有纯洁优美的品德. 出 处 战国·楚·屈原《楚辞·九章·怀沙》：“怀瑾握瑜兮,穷不知所示.”\",\"titleContent\":\"请翻译以下文言文句子。\\n何故怀瑾握瑜而自令见放为？\"}', '2022-04-19 13:22:02'), (72, '{\"questionItemObjects\":[{\"prefix\":\"A\",\"content\":\"姜子牙\"},{\"prefix\":\"B\",\"content\":\"老子\"},{\"prefix\":\"C\",\"content\":\"岳飞\"},{\"prefix\":\"D\",\"content\":\"张仲景\"}],\"correct\":\"\",\"analyze\":\"都是的\",\"titleContent\":\"下列哪些名人是河南人？\"}', '2022-04-19 13:24:19'), (73, '{\"questionItemObjects\":[{\"prefix\":\"1\",\"content\":\"外无期功强近之亲\",\"score\":10,\"itemUuid\":\"c09628f9-2d28-4b01-96d7-314dacd54235\"},{\"prefix\":\"2\",\"content\":\"内无应门五尺之僮\",\"score\":20,\"itemUuid\":\"aac95642-b2e7-4a68-bf0d-3abf66e4807d\"}],\"correct\":\"\",\"analyze\":\"<p>臣密言：臣以险衅，夙遭闵凶。生孩六月，慈父见背；行年四岁，舅夺母志。祖母刘愍臣孤弱，躬亲抚养。臣少多疾病，九岁不行，零丁孤苦，至于成立。既无伯叔，终鲜兄弟，门衰祚薄，晚有儿息。外无期功强近之亲，内无应门五尺之僮，茕茕孑立，形影相吊。而刘夙婴疾病，常在床蓐，臣侍汤药，未曾废离。</p><p>　　逮奉圣朝，沐浴清化。前太守臣逵察臣孝廉；后刺史臣荣举臣秀才。臣以供养无主，辞不赴命。诏书特下，拜臣郎中，寻蒙国恩，除臣洗马。猥以微贱，当侍东宫，非臣陨首所能上报。臣具以表闻，辞不就职。诏书切峻，责臣逋慢；郡县逼迫，催臣上道；州司临门，急于星火。臣欲奉诏奔驰，则刘病日笃，欲苟顺私情，则告诉不许：臣之进退，实为狼狈。</p><p>　　伏惟圣朝以孝治天下，凡在故老，犹蒙矜育，况臣孤苦，特为尤甚。且臣少仕伪朝，历职郎署，本图宦达，不矜名节。今臣亡国贱俘，至微至陋，过蒙拔擢，宠命优渥，岂敢盘桓，有所希冀。但以刘日薄西山，气息奄奄，人命危浅，朝不虑夕。臣无祖母，无以至今日，祖母无臣，无以终余年。母、孙二人，更相为命，是以区区不能废远。</p><p>　　臣密今年四十有四，祖母今年九十有六，是臣尽节于陛下之日长，报养刘之日短也。乌鸟私情，愿乞终养。臣之辛苦，非独蜀之人士及二州牧伯所见明知，皇天后土实所共鉴。愿陛下矜愍愚诚，听臣微志，庶刘侥幸，保卒余年。臣生当陨首，死当结草。臣不胜犬马怖惧之情，谨拜表以闻。</p>\",\"titleContent\":\"<span class=\\\"gapfilling-span c09628f9-2d28-4b01-96d7-314dacd54235\\\">1</span>，<span class=\\\"gapfilling-span aac95642-b2e7-4a68-bf0d-3abf66e4807d\\\">2</span>，茕茕孑立，形影相吊。\"}', '2022-04-19 13:29:31'), (74, '{\"questionItemObjects\":[{\"prefix\":\"1\",\"content\":\"<span style=\\\"color: #333333; font-family: &quot;Helvetica Neue&quot;, Helvetica, Arial, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;WenQuanYi Micro Hei&quot;, sans-serif; font-size: 14px; text-indent: 28px; background-color: #FFFFFF;\\\">人生自古谁无死？</span>\",\"score\":20,\"itemUuid\":\"e3ce438e-4ca3-413c-bc2a-f300c49614ce\"},{\"prefix\":\"2\",\"content\":\"<span style=\\\"color: #333333; font-family: &quot;Helvetica Neue&quot;, Helvetica, Arial, &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;WenQuanYi Micro Hei&quot;, sans-serif; font-size: 14px; text-indent: 28px; background-color: #FFFFFF;\\\">留取丹心照汗青</span>\",\"score\":20,\"itemUuid\":\"43b6907b-4a02-4454-b920-09551e3ef4a1\"}],\"correct\":\"\",\"analyze\":\"<p>辛苦遭逢起一经，</p><p>干戈寥落四周星。</p><p>山河破碎风飘絮，</p><p>身世浮沉雨打萍。</p><p>惶恐滩头说惶恐，</p><p>零丁洋里叹零丁。</p><p>人生自古谁无死，</p><p>留取丹心照汗青。</p>\",\"titleContent\":\"<span style=\\\"font-family: 新宋体, Arial, sans-serif; font-size: 14px; text-indent: 28px; background-color: #F7F7FF;\\\">惶恐滩头说惶恐，零丁洋里叹零丁。<span class=\\\"gapfilling-span e3ce438e-4ca3-413c-bc2a-f300c49614ce\\\">1</span>，<span class=\\\"gapfilling-span 43b6907b-4a02-4454-b920-09551e3ef4a1\\\">2</span></span>\"}', '2022-04-19 13:31:32'), (75, '{\"questionItemObjects\":[{\"prefix\":\"A\",\"content\":\"是\"},{\"prefix\":\"B\",\"content\":\"否\"}],\"correct\":\"A\",\"analyze\":\"正像数学家华罗庚说过的，科学的灵感，决不是坐等可以等来的\",\"titleContent\":\"正像数学家华罗庚说过的，科学的灵感，决不是坐等可以等来的\"}', '2022-04-19 13:32:23'), (77, '[{\"name\":\"语文综合测评\",\"questionItems\":[{\"itemOrder\":1,\"id\":20},{\"itemOrder\":2,\"id\":22},{\"itemOrder\":3,\"id\":23},{\"itemOrder\":4,\"id\":26},{\"itemOrder\":5,\"id\":21},{\"itemOrder\":6,\"id\":25},{\"itemOrder\":7,\"id\":27},{\"itemOrder\":8,\"id\":30},{\"itemOrder\":9,\"id\":29},{\"itemOrder\":10,\"id\":28}]}]', '2022-04-19 13:34:19'), (78, '真不会', '2022-04-19 13:58:49'), (79, '长得丑', '2022-04-19 13:58:49'), (80, '[\"人生自古谁无死\",\"留取丹心照汗青\"]', '2022-04-19 13:58:49'), (81, '[\"阿巴\",\"啊八八八八\"]', '2022-04-19 13:58:49'), (88, NULL, '2022-05-12 17:03:45'), (89, NULL, '2022-05-12 17:03:45'), (90, '[]', '2022-05-12 17:03:45'), (91, '[]', '2022-05-12 17:03:45'), (92, '测试', '2022-05-12 17:04:36'), (93, '测试', '2022-05-12 17:04:36'), (94, '[\"测试\",\"测试\"]', '2022-05-12 17:04:36'), (95, '[\"测试\",\"测试\"]', '2022-05-12 17:04:36'), (96, '测试', '2022-05-12 17:08:55'), (97, '测试', '2022-05-12 17:08:55'), (98, '[\"测\",\"测试\"]', '2022-05-12 17:08:55'), (99, '[\"测试\",\"测试\"]', '2022-05-12 17:08:55');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_uuid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'uuid',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `age` int UNSIGNED NULL DEFAULT NULL COMMENT '年龄',
  `sex` int NULL DEFAULT NULL COMMENT '性别(0.女 1.男)',
  `birth_day` date NULL DEFAULT NULL COMMENT '出生日期',
  `user_level` int NULL DEFAULT NULL COMMENT '年级(1-12)',
  `phone` int UNSIGNED NULL DEFAULT NULL COMMENT '电话',
  `role_id` int UNSIGNED NULL DEFAULT NULL COMMENT '角色',
  `status` int(1) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '状态(0.有效 1.无效)',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像地址',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `last_active_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次活跃时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` (`id`, `user_uuid`, `user_name`, `password`, `real_name`, `age`, `sex`, `birth_day`, `user_level`, `phone`, `role_id`, `status`, `avatar`, `create_time`, `modify_time`, `last_active_time`) VALUES (1, '747a9b6b-b925-45b3-be60-d8ba2a451c58', 'admin', '$2a$10$BP4B6JhyreHPbFzCEuV7ceZTCy3qXoUnlGG46V6MOLrXXZnm.cTGu', '管理员', 1, 1, '2020-01-01', NULL, 120, 1, 1, 'https://tuchuangs.com/imgs/2022/04/12/9ecfba23e936b13d.jpg', '2022-01-17 10:27:04', '2022-02-08 10:40:43', '2022-07-19 22:56:36'), (33, '081af8fa-be48-4162-83f1-7cebf9481568', 'teacher', '$2a$10$R3yUW/KzRGVkALKF2Ubd7.LKVVqjYCjEDPHY0dDn6kvohtyL1DYSS', '张三', 16, 1, '2020-01-01', 1, 110, 2, 1, 'https://static01.imgkr.com/temp/23cf94d1cb6145298bc169c23c3c84c8.jpg', '2022-02-08 14:20:46', '2022-02-08 14:20:46', '2022-05-24 17:45:51'), (37, 'fbea4e78-705c-4586-97bc-dc018c349d23', 'student', '$2a$10$ri1RmazihYwBuzDf3gcTlea2PM8Wuv/YioCgzMmuUyGfdec3icnbK', '李四', 16, 1, '2020-01-05', 1, 110, 3, 1, 'https://static01.imgkr.com/temp/23cf94d1cb6145298bc169c23c3c84c8.jpg', '2022-02-08 14:20:47', '2022-05-01 10:59:16', '2022-05-24 15:24:09'), (51, 'f04f7492-fe1b-4c19-b292-127367a8be2f', '84618233', '$2a$10$QLS97covr89p0ex6Ps4EL.cr.xgVTVO/w/xdeiZPdTSLy7hEjIk.u', '张三', 16, 1, '2020-01-01', 1, 110, 3, 1, 'https://static01.imgkr.com/temp/23cf94d1cb6145298bc169c23c3c84c8.jpg', '2022-05-24 15:17:07', '2022-05-24 15:17:07', NULL), (52, '850475c7-3bdc-4779-95d0-31c529ab4f0e', '84618234', '$2a$10$aw2LLC49deoLjTqi5LMynu.ws71hkg9.EFtRURf732W06IzLjlU36', '李四', 16, 1, '2020-01-02', 2, 110, 3, 1, 'https://static01.imgkr.com/temp/23cf94d1cb6145298bc169c23c3c84c8.jpg', '2022-05-24 15:17:07', '2022-05-24 15:17:07', NULL), (53, '7582b8ae-f52f-4de4-9a9d-82116c8bf36f', '84618235', '$2a$10$8xFiZEdfKk1ISeAH0vQRmeykF6v3clnkFJ8CdBxoPSMC4EvENLZ1i', '王二麻子', 16, 1, '2020-01-03', 3, 110, 3, 1, 'https://static01.imgkr.com/temp/23cf94d1cb6145298bc169c23c3c84c8.jpg', '2022-05-24 15:17:08', '2022-05-24 15:17:08', NULL), (54, '9e2047c2-270b-4d5b-89f3-23f9dcfcfd1a', '84618236', '$2a$10$aIlpEuuDJ9VOHZNwAjc0g.Q3CGtc0Z6u9p3zKssvKJszsLdN8c4MG', '张三', 16, 1, '2020-01-04', 1, 110, 3, 1, 'https://static01.imgkr.com/temp/23cf94d1cb6145298bc169c23c3c84c8.jpg', '2022-05-24 15:17:08', '2022-05-24 15:17:08', NULL), (55, '83477ce1-af6a-4cc2-a4c1-a6478c2ac94d', '84618237', '$2a$10$IsYsuXGZ./yNBcsMAEfMPes36Zp4Km1HZUc7X5w2Tm7/bvA63HJkm', '李四', 16, 1, '2020-01-05', 2, 110, 3, 1, 'https://static01.imgkr.com/temp/23cf94d1cb6145298bc169c23c3c84c8.jpg', '2022-05-24 15:17:09', '2022-05-24 15:17:09', NULL), (56, 'dc0dc5cb-171a-41a9-867d-6a4e57bf6564', '84618238', '$2a$10$jbDYAsmjdH6QspI4fG6kXuc5mEl3L4cGzHD1KCEFKryMuHnBVhuvq', '王二麻子', 16, 1, '2020-01-06', 2, 110, 3, 1, 'https://static01.imgkr.com/temp/23cf94d1cb6145298bc169c23c3c84c8.jpg', '2022-05-24 15:17:09', '2022-05-24 15:17:09', NULL), (58, '3e3d09c3-6b20-4992-9afb-e7f67db8ab15', '84618240', '$2a$10$CzeG71nhxPKxjrZAHBP6iuloShXGmpeBQNf5lN6DC/vCQPTtG/lO6', '李四', 28, 2, '2020-01-08', 11, 110, 2, 1, 'https://static01.imgkr.com/temp/23cf94d1cb6145298bc169c23c3c84c8.jpg', '2022-05-24 15:17:10', '2022-05-24 15:17:10', NULL), (59, '2bfe66ab-e607-4449-8531-edaaea41fc5f', '84618241', '$2a$10$fOqM4452eVqBJpc5wbNrjezwHyKE.cjoivtBZbnyIu8QKeEunRhba', '王二麻子', 28, 2, '2020-01-09', 12, 110, 2, 1, 'https://static01.imgkr.com/temp/23cf94d1cb6145298bc169c23c3c84c8.jpg', '2022-05-24 15:17:10', '2022-05-24 15:17:10', NULL), (60, '69fe9851-9cfb-4f6f-b9a5-ffcea24bd604', '84618242', '$2a$10$fGl802pBd9SQu7Amh0X5gO1rc88Hve81i2iBbdkE8Ahy4wQjh0SRm', '张三', 28, 2, '2020-01-10', 9, 110, 2, 1, 'https://static01.imgkr.com/temp/23cf94d1cb6145298bc169c23c3c84c8.jpg', '2022-05-24 15:17:11', '2022-05-24 15:17:11', NULL);
COMMIT;

-- ----------------------------
-- Table structure for user_event_log
-- ----------------------------
DROP TABLE IF EXISTS `user_event_log`;
CREATE TABLE `user_event_log`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户日志表';

-- ----------------------------
-- Records of user_event_log
-- ----------------------------
BEGIN;
INSERT INTO `user_event_log` (`id`, `user_id`, `user_name`, `real_name`, `content`, `create_time`) VALUES (9, 37, '84618237', '李四', '84618237 提交试卷：一年级第一学期语文综合测评 得分：13 耗时：59 秒', '2022-04-19 13:58:49'), (10, 37, '84618237', '李四', '84618237 提交试卷：好生气 得分：7 耗时：1分 5秒', '2022-04-21 12:48:53'), (11, 37, '84618237', '李四', '84618237 提交试卷：好生气 得分：7 耗时：1分 5秒', '2022-04-21 12:49:12'), (12, 33, 'teacher', '张三', 'teacher 批改试卷：一年级第一学期语文综合测评 得分：19', '2022-04-23 13:42:20'), (13, 33, 'teacher', '张三', 'teacher 批改试卷：好生气 得分：7', '2022-04-23 13:43:02'), (14, 33, 'teacher', '张三', 'teacher 批改试卷：好生气 得分：7', '2022-04-23 13:43:10'), (15, 37, 'student', '李四', 'student 提交试卷：一年级第一学期语文综合测评 得分：0 耗时：47 秒', '2022-05-12 17:03:45'), (16, 37, 'student', '李四', 'student 提交试卷：一年级第一学期语文综合测评 得分：12 耗时：24 秒', '2022-05-12 17:04:36'), (17, 37, 'student', '李四', 'student 提交试卷：一年级第一学期语文综合测评 得分：7 耗时：24 秒', '2022-05-12 17:08:55'), (18, 33, 'teacher', '张三', 'teacher 批改试卷：一年级第一学期语文综合测评 得分：0', '2022-05-12 17:18:35'), (19, 33, 'teacher', '张三', 'teacher 批改试卷：一年级第一学期语文综合测评 得分：12', '2022-05-12 17:18:50'), (20, 33, 'teacher', '张三', 'teacher 批改试卷：一年级第一学期语文综合测评 得分：7', '2022-05-12 17:18:57'), (21, 33, 'teacher', '张三', 'teacher 批改试卷：一年级第一学期语文综合测评 得分：7', '2022-05-12 17:21:56'), (22, 33, 'teacher', '张三', 'teacher 批改试卷：一年级第一学期语文综合测评 得分：7', '2022-05-12 17:27:59'), (23, 33, 'teacher', '张三', 'teacher 批改试卷：一年级第一学期语文综合测评 得分：7', '2022-05-12 17:32:27');
COMMIT;

-- ----------------------------
-- Table structure for user_token
-- ----------------------------
DROP TABLE IF EXISTS `user_token`;
CREATE TABLE `user_token`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'token令牌',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '截止时间',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户令牌表';

-- ----------------------------
-- Records of user_token
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '视频名称',
  `level` int NULL DEFAULT NULL COMMENT '年级',
  `subject_id` int NULL DEFAULT NULL COMMENT '学科',
  `cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '封面',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源定位符',
  `path` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路径',
  `tags` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Records of video
-- ----------------------------
BEGIN;
INSERT INTO `video` (`id`, `name`, `level`, `subject_id`, `cover`, `url`, `path`, `tags`, `create_time`) VALUES (1, '语文1', 1, 2, 'https://static01.imgkr.com/temp/23cf94d1cb6145298bc169c23c3c84c8.jpg', 'http://localhost:8080/api/video/preview/2022-02-19_天堂之门.mp4', 'E:\\upload\\2022\\02\\19\\2022-02-19_天堂之门.mp4', '[基础, 古诗]', '2022-02-19 17:49:42'), (4, '语文2', 1, 2, 'https://static01.imgkr.com/temp/23cf94d1cb6145298bc169c23c3c84c8.jpg', 'http://localhost:8080/api/video/preview/2022-02-19_天堂之门.mp4', 'E:\\upload\\2022\\02\\19\\2022-02-19_天堂之门.mp4', '[阅读,选择]', '2022-02-19 21:29:54'), (5, '语文3', 1, 2, 'https://static01.imgkr.com/temp/23cf94d1cb6145298bc169c23c3c84c8.jpg', 'http://localhost:8080/api/video/preview/2022-02-19_天堂之门.mp4', 'E:\\upload\\2022\\02\\19\\2022-02-19_天堂之门.mp4', '[作文, 填空, 古诗讲解]', '2022-02-19 21:34:02'), (8, '语文4', 1, 2, 'https://static01.imgkr.com/temp/23cf94d1cb6145298bc169c23c3c84c8.jpg', 'http://localhost:8080/api/video/preview/2022-02-19_天堂之门.mp4', 'E:\\upload\\2022\\02\\19\\2022-02-19_天堂之门.mp4', '[期末试卷讲解课]', '2022-02-20 09:35:28');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
