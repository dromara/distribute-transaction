SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` bigint(20) NOT NULL,
  `order_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_info
-- ----------------------------

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info`  (
  `id` bigint(20) NOT NULL,
  `product_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `product_price` decimal(10, 2) NULL DEFAULT NULL,
  `stock_count` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_info
-- ----------------------------
INSERT INTO `product_info` VALUES (1, '笔记本电脑', 10000.00, 100);

SET FOREIGN_KEY_CHECKS = 1;
