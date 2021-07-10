SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `order_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '订单编号',
  `product_id` bigint(20) NULL DEFAULT 0 COMMENT '商品id',
  `pay_count` int(11) NULL DEFAULT NULL COMMENT '购买数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '模拟订单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------

-- ----------------------------
-- Table structure for tx_log
-- ----------------------------
DROP TABLE IF EXISTS `tx_log`;
CREATE TABLE `tx_log`  (
  `tx_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分布式事务全局序列号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`tx_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '事务记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tx_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
