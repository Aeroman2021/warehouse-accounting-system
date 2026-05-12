/*
 Navicat Premium Data Transfer

 Source Server         : spring
 Source Server Type    : MySQL
 Source Server Version : 80036 (8.0.36)
 Source Host           : localhost:3306
 Source Schema         : inventory-accounting

 Target Server Type    : MySQL
 Target Server Version : 80036 (8.0.36)
 File Encoding         : 65001

 Date: 12/05/2026 22:56:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for accounts
-- ----------------------------
DROP TABLE IF EXISTS `accounts`;
CREATE TABLE `accounts`  (
                             `id` BIGINT NOT NULL AUTO_INCREMENT,
                             `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                             `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                             `account_type` enum('ASSET','LIABILITY','EQUITY','REVENUE','EXPENSE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                             `active` bit(1) NOT NULL,
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for journal_entries
-- ----------------------------
DROP TABLE IF EXISTS `journal_entries`;
CREATE TABLE `journal_entries`  (
                                    `id` BIGINT NOT NULL AUTO_INCREMENT,
                                    `reference_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                    `entry_status` enum('DRAFT','POSTED','CANCELED') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    `referenced_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for journal_items
-- ----------------------------
DROP TABLE IF EXISTS `journal_items`;
CREATE TABLE `journal_items`  (
                                  `id` BIGINT NOT NULL AUTO_INCREMENT,
                                  `account_id` BIGINT NULL DEFAULT NULL,
                                  `journal_entry_id` BIGINT NULL DEFAULT NULL,
                                  `debit` decimal(38, 2) NULL DEFAULT NULL,
                                  `credit` decimal(38, 2) NULL DEFAULT NULL,
                                  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                  PRIMARY KEY (`id`) USING BTREE,
                                  INDEX `journal_items_account_fk`(`account_id` ASC) USING BTREE,
                                  INDEX `journal_items_journal_entry_fk`(`journal_entry_id` ASC) USING BTREE,
                                  CONSTRAINT `journal_items_account_fk` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                  CONSTRAINT `journal_items_journal_entry_fk` FOREIGN KEY (`journal_entry_id`) REFERENCES `journal_entries` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;



-- ----------------------------
-- Table structure for parties
-- ----------------------------
DROP TABLE IF EXISTS `parties`;
CREATE TABLE `parties`  (
                            `id` BIGINT NOT NULL AUTO_INCREMENT,
                            `parent_id` BIGINT NULL DEFAULT NULL,
                            `party_role` enum('RECEIVER','SUPPLIER') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                            `party_type` enum('PERSON','COMPANY','DEPARTMENT') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                            `economic_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                            `national_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE,
                            INDEX `party_party_fk`(`parent_id` ASC) USING BTREE,
                            CONSTRAINT `party_party_fk` FOREIGN KEY (`parent_id`) REFERENCES `parties` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products`  (
                             `id` BIGINT NOT NULL AUTO_INCREMENT,
                             `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                             `sku` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;



-- ----------------------------
-- Table structure for stock_movements
-- ----------------------------
DROP TABLE IF EXISTS `stock_movements`;
CREATE TABLE `stock_movements`  (
                                    `id` BIGINT NOT NULL AUTO_INCREMENT,
                                    `stock_id` BIGINT NULL DEFAULT NULL,
                                    `party_id` BIGINT NULL DEFAULT NULL,
                                    `quantity` int NULL DEFAULT NULL,
                                    `unit_price` decimal(38, 2) NULL DEFAULT NULL,
                                    `total_price` decimal(38, 2) NULL DEFAULT NULL,
                                    `movement_type` enum('PURCHASE','SALE','RETURN','ADJUSTMENT','TRANSFER') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                    `reference_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    PRIMARY KEY (`id`) USING BTREE,
                                    INDEX `stock_movements_stock_idx`(`stock_id` ASC) USING BTREE,
                                    INDEX `stock_movements_party_fk`(`party_id` ASC) USING BTREE,
                                    CONSTRAINT `stock_movements_party_fk` FOREIGN KEY (`party_id`) REFERENCES `parties` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                    CONSTRAINT `stock_movements_stock_fk` FOREIGN KEY (`stock_id`) REFERENCES `stocks` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for stocks
-- ----------------------------
DROP TABLE IF EXISTS `stocks`;
CREATE TABLE `stocks`  (
                           `id` BIGINT NOT NULL AUTO_INCREMENT,
                           `product_id` BIGINT NULL DEFAULT NULL,
                           `warehouse_id` BIGINT NULL DEFAULT NULL,
                           `quantity` int NULL DEFAULT NULL,
                           `average_cost` decimal(38, 2) NULL DEFAULT NULL,
                           PRIMARY KEY (`id`) USING BTREE,
                           INDEX `stock_product_idx`(`product_id` ASC) USING BTREE,
                           INDEX `stock_warehouse_idx`(`warehouse_id` ASC) USING BTREE,
                           CONSTRAINT `stock_product_fk` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                           CONSTRAINT `stock_warehouse_fk` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouses` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for warehouses
-- ----------------------------
DROP TABLE IF EXISTS `warehouses`;
CREATE TABLE `warehouses`  (
                               `id` BIGINT NOT NULL AUTO_INCREMENT,
                               `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


SET FOREIGN_KEY_CHECKS = 1;
