-- ----------------------------
-- Records of accounts
-- ----------------------------
INSERT INTO `accounts` VALUES (1, '1000', 'Cash', 'ASSET', b'1');
INSERT INTO `accounts` VALUES (2, '1200', 'Account Receivable', 'ASSET', b'1');
INSERT INTO `accounts` VALUES (3, '5100', 'COGS', 'EXPENSE', b'1');
INSERT INTO `accounts` VALUES (4, '1300', 'Inventory Asset', 'ASSET', b'1');
INSERT INTO `accounts` VALUES (5, '4100', 'Sale Revenue', 'REVENUE', b'1');

-- ----------------------------
-- Records of parties
-- ----------------------------
INSERT INTO `parties` VALUES (1, NULL, 'RECEIVER', 'COMPANY', '1254789', NULL);
INSERT INTO `parties` VALUES (2, 1, 'RECEIVER', 'DEPARTMENT', NULL, NULL);

-- ----------------------------
-- Records of products
-- ----------------------------
INSERT INTO `products` VALUES (2, 'monitor-SAMSUNG', 'gh-15698');


-- ----------------------------
-- Records of warehouses
-- ----------------------------
INSERT INTO `warehouses` VALUES (1, 'Zafar-warehouse');

-- ----------------------------
-- Records of stocks
-- ----------------------------
INSERT INTO `stocks` VALUES (1, 2, 1, 3, 1000000.00);


