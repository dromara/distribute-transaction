SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for stock
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock`  (
  `id` bigint(11) NOT NULL COMMENT '主键id',
  `product_id` bigint(20) NULL DEFAULT 0 COMMENT '商品id',
  `total_count` int(11) NULL DEFAULT 0 COMMENT '商品总库存',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '模拟库存' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stock
-- ----------------------------
INSERT INTO `stock` VALUES (1, 1001, 10000);

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
