/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 100128
 Source Host           : localhost:3306
 Source Schema         : cafehouse

 Target Server Type    : MySQL
 Target Server Version : 100128
 File Encoding         : 65001

 Date: 21/01/2018 23:36:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for asci
-- ----------------------------
DROP TABLE IF EXISTS `asci`;
CREATE TABLE `asci`  (
  `ascid` int(11) NOT NULL AUTO_INCREMENT,
  `aadi` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `asoyadi` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `asubeid` int(11) NULL DEFAULT NULL,
  `adurum` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `asifre` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ascid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_turkish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for garson
-- ----------------------------
DROP TABLE IF EXISTS `garson`;
CREATE TABLE `garson`  (
  `garsonid` int(11) NOT NULL AUTO_INCREMENT,
  `gadi` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `gsoyadi` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `gsubeid` int(11) NULL DEFAULT NULL,
  `gdurum` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `gsifre` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`garsonid`) USING BTREE,
  FULLTEXT INDEX `asa`(`gadi`, `gsoyadi`)
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_turkish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of garson
-- ----------------------------
INSERT INTO `garson` VALUES (1, 'Ali', 'Bilmem', 1, 'pasif', NULL);
INSERT INTO `garson` VALUES (2, 'a', 'b', 0, 'pasif', NULL);
INSERT INTO `garson` VALUES (3, 'x', 'x', 0, 'pasif', NULL);
INSERT INTO `garson` VALUES (4, 'a', 'b', 0, 'aktif', '1');
INSERT INTO `garson` VALUES (5, 's', 's', 0, 'pasif', '123');
INSERT INTO `garson` VALUES (6, 'ayşe', 'gül', 0, 'aktif', '123');

-- ----------------------------
-- Table structure for gorev
-- ----------------------------
DROP TABLE IF EXISTS `gorev`;
CREATE TABLE `gorev`  (
  `gorevid` int(11) NOT NULL AUTO_INCREMENT,
  `gorevadi` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`gorevid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_turkish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of gorev
-- ----------------------------
INSERT INTO `gorev` VALUES (1, 'garson');
INSERT INTO `gorev` VALUES (2, 'asci');
INSERT INTO `gorev` VALUES (3, 'mudur');

-- ----------------------------
-- Table structure for kategori
-- ----------------------------
DROP TABLE IF EXISTS `kategori`;
CREATE TABLE `kategori`  (
  `katid` int(11) NOT NULL AUTO_INCREMENT,
  `kadi` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`katid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_turkish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of kategori
-- ----------------------------
INSERT INTO `kategori` VALUES (1, 'seçiniz');
INSERT INTO `kategori` VALUES (2, 'Çorba');
INSERT INTO `kategori` VALUES (3, 'Et Yemekleri');
INSERT INTO `kategori` VALUES (4, 'Tavuk Yemekleri');
INSERT INTO `kategori` VALUES (5, 'Balık Çeşitleri');
INSERT INTO `kategori` VALUES (6, 'Hamur İşleri');
INSERT INTO `kategori` VALUES (7, 'Kahvaltı Çeşitleri');
INSERT INTO `kategori` VALUES (8, 'Soğuk İçecekler');
INSERT INTO `kategori` VALUES (9, 'Sıcak İçecekler');
INSERT INTO `kategori` VALUES (10, 'Tatlılar');

-- ----------------------------
-- Table structure for kullanicilar
-- ----------------------------
DROP TABLE IF EXISTS `kullanicilar`;
CREATE TABLE `kullanicilar`  (
  `sifreid` int(11) NOT NULL AUTO_INCREMENT,
  `kTC` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `kullaniciAdi` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `kullaniciSoyadi` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `ktel` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `kmail` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `kadres` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `kgorevid` int(11) NULL DEFAULT NULL,
  `ksifre` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `kresim` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `kdurum` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sifreid`) USING BTREE,
  FULLTEXT INDEX `ara`(`kullaniciAdi`, `kullaniciSoyadi`)
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_turkish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of kullanicilar
-- ----------------------------
INSERT INTO `kullanicilar` VALUES (1, '', 'ali', 'bilmem', '', NULL, NULL, 3, '7', NULL, 'mudur');
INSERT INTO `kullanicilar` VALUES (2, '', 'ali ', 'adalı', '234234', 'asdfgh', 'asdfg', 1, '852', NULL, 'aktif');
INSERT INTO `kullanicilar` VALUES (3, '', 'gül', 'soylu', '', NULL, NULL, 2, '852', NULL, 'pasif');
INSERT INTO `kullanicilar` VALUES (4, '20156847799', 'merve', 'yıldız', '5034758745', 'merve@gmail.com', 'İstanbul', 1, '852', '', 'aktif');
INSERT INTO `kullanicilar` VALUES (5, '11', 'elif', 'haklı', '5656', 'ölk', 'şşll', 1, 'lll', 'şşş', 'pasif');
INSERT INTO `kullanicilar` VALUES (6, '12312312', 'lale', 'kingçi', '456123423', 'lkkh', 'lkj', 1, '852', '', 'aktif');
INSERT INTO `kullanicilar` VALUES (7, '20284962488', 'elif', 'aydoğann', '5015894245', 'elif@hotmail.com', 'güleryüz sok / ÜMRANİYE', 1, '852', '', 'pasif');
INSERT INTO `kullanicilar` VALUES (8, '20186457732', 'nil', 'mutlu', '5026989574', 'nil@gmail.com', 'Beşiktaş', 2, '852', 'C:\\Users\\elifs\\Desktop\\CafeHouse\\src\\rsm\\vesikalık_4.jpg', 'aktif');

-- ----------------------------
-- Table structure for masa
-- ----------------------------
DROP TABLE IF EXISTS `masa`;
CREATE TABLE `masa`  (
  `masaid` int(11) NOT NULL AUTO_INCREMENT,
  `madi` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `masaaktif` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `msubeid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`masaid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_turkish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of masa
-- ----------------------------
INSERT INTO `masa` VALUES (1, 'masa1', '0', 1);
INSERT INTO `masa` VALUES (2, 'masa2', '1', 1);
INSERT INTO `masa` VALUES (3, 'masa3', '0', 1);
INSERT INTO `masa` VALUES (4, 'masa4', '0', 1);
INSERT INTO `masa` VALUES (5, 'masa5', '0', 1);
INSERT INTO `masa` VALUES (6, 'masa6', '0', 1);
INSERT INTO `masa` VALUES (7, 'masa7', '0', 1);
INSERT INTO `masa` VALUES (8, 'masa8', '0', 1);

-- ----------------------------
-- Table structure for menuadi
-- ----------------------------
DROP TABLE IF EXISTS `menuadi`;
CREATE TABLE `menuadi`  (
  `menuid` int(11) NOT NULL AUTO_INCREMENT,
  `menuadi` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `menubaslangic` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `menubitis` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `menufiyat` float(32, 0) NULL DEFAULT NULL,
  `menumaliyet` float(32, 0) NULL DEFAULT NULL,
  `madet` int(11) NULL DEFAULT NULL,
  `menuresim` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`menuid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_turkish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of menuadi
-- ----------------------------
INSERT INTO `menuadi` VALUES (1, 'seçiniz', NULL, NULL, 0, 0, 0, NULL);
INSERT INTO `menuadi` VALUES (2, 'Hamburger Menü', '2018-01-10 21:49:30', '2018-02-02 21:49:35', 15, 10, 0, 'img/hamburgermenu.png');
INSERT INTO `menuadi` VALUES (3, 'Kebap Menü', '2018-01-10 21:50:08', '2018-01-18 21:50:14', 25, 15, 0, 'img/5080a92b6183707b0ecf81ca0c56b615.png');
INSERT INTO `menuadi` VALUES (4, 'dürüm menü', ' 2018-01-05 00:00:00', '2018-01-15 00:00:00', 17, 10, 0, 'img/menu_9.jpg');
INSERT INTO `menuadi` VALUES (5, 'pizza+cola', '2018-01-29 00:00:00', '2018-02-25 00:00:00', 12, 8, 0, 'C:\\\\Users\\\\elifs\\\\Desktop\\\\CafeHouse\\\\src\\\\rsm\\\\803ace870f3e6677d043883994c8d6e5--pizza-coupons-food-coupons.jpg');

-- ----------------------------
-- Table structure for menuicerigi
-- ----------------------------
DROP TABLE IF EXISTS `menuicerigi`;
CREATE TABLE `menuicerigi`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menid` int(11) NULL DEFAULT NULL,
  `yemekid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_turkish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for mudur
-- ----------------------------
DROP TABLE IF EXISTS `mudur`;
CREATE TABLE `mudur`  (
  `mudurid` int(11) NOT NULL AUTO_INCREMENT,
  `madi` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `msoyadi` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `msubeid` int(11) NULL DEFAULT NULL,
  `msifre` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`mudurid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_turkish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of mudur
-- ----------------------------
INSERT INTO `mudur` VALUES (1, 'Hatice', 'Yeşilkaya', 1, NULL);
INSERT INTO `mudur` VALUES (2, 'Fatma', 'Şimşek', 2, NULL);

-- ----------------------------
-- Table structure for patron
-- ----------------------------
DROP TABLE IF EXISTS `patron`;
CREATE TABLE `patron`  (
  `patronid` int(11) NOT NULL AUTO_INCREMENT,
  `padi` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `psoyadi` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `pmail` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NOT NULL,
  `psifre` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`patronid`) USING BTREE,
  UNIQUE INDEX `benzersiz_mail`(`pmail`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_turkish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for siparis
-- ----------------------------
DROP TABLE IF EXISTS `siparis`;
CREATE TABLE `siparis`  (
  `sipid` int(11) NOT NULL AUTO_INCREMENT,
  `subeid` int(11) NULL DEFAULT NULL,
  `smasaid` int(11) NULL DEFAULT NULL,
  `sgarsonid` int(11) NULL DEFAULT NULL,
  `syemekid` int(11) NULL DEFAULT NULL,
  `smenuid` int(11) NULL DEFAULT NULL,
  `sadet` int(11) NULL DEFAULT NULL,
  `madet` int(11) NULL DEFAULT NULL,
  `sdurum` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `shazir` int(11) NULL DEFAULT NULL,
  `starih` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`sipid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8 COLLATE = utf8_turkish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of siparis
-- ----------------------------
INSERT INTO `siparis` VALUES (48, 1, 2, 1, 67, 4, 2, 3, 'pasif', 0, '2018-01-16 14:38:45');
INSERT INTO `siparis` VALUES (49, 1, 2, 1, 1, 2, 0, 2, 'aktif', 1, '2018-01-16 17:02:54');
INSERT INTO `siparis` VALUES (50, 1, 4, 1, 24, 1, 2, 0, 'aktif', 2, '2018-01-16 17:03:10');
INSERT INTO `siparis` VALUES (51, 1, 2, 1, 57, 2, 2, 2, 'aktif', 1, '2018-01-17 16:05:01');
INSERT INTO `siparis` VALUES (52, 1, 2, 1, 7, 4, 1, 4, 'aktif', 1, '2018-01-17 17:38:24');
INSERT INTO `siparis` VALUES (53, 1, 2, 2, 83, 2, 2, 3, 'pasif', 1, '2018-01-17 22:36:48');
INSERT INTO `siparis` VALUES (55, 1, 2, 6, 38, 5, 2, 2, 'pasif', 1, '2018-01-19 23:23:55');
INSERT INTO `siparis` VALUES (56, 1, 7, 6, 4, 2, 1, 1, 'pasif', 1, '2018-01-19 23:32:45');
INSERT INTO `siparis` VALUES (57, 1, 3, 5, 71, 4, 2, 2, 'pasif', 1, '2018-01-19 23:33:01');
INSERT INTO `siparis` VALUES (58, 1, 5, 5, 67, 2, 1, 1, 'aktif', 1, '2018-01-20 00:04:53');
INSERT INTO `siparis` VALUES (59, 1, 5, 2, 86, 2, 2, 2, 'aktif', 0, '2018-01-21 13:27:39');
INSERT INTO `siparis` VALUES (60, 1, 5, 4, 67, 4, 2, 2, 'aktif', 0, '2018-01-21 13:27:57');
INSERT INTO `siparis` VALUES (61, 1, 5, 5, 85, 5, 2, 1, 'aktif', 0, '2018-01-21 13:28:35');

-- ----------------------------
-- Table structure for sube
-- ----------------------------
DROP TABLE IF EXISTS `sube`;
CREATE TABLE `sube`  (
  `subeid` int(11) NOT NULL AUTO_INCREMENT,
  `sadi` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `sulke` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `sil` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `silceid` int(11) NULL DEFAULT NULL,
  `sadres` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `smasasayisi` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`subeid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_turkish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sube
-- ----------------------------
INSERT INTO `sube` VALUES (1, 'cafeHouse', NULL, NULL, 2, 'Beşiktaş', 20);
INSERT INTO `sube` VALUES (2, 'cafeHouse', NULL, NULL, 3, 'Uskudar', 30);

-- ----------------------------
-- Table structure for yemek
-- ----------------------------
DROP TABLE IF EXISTS `yemek`;
CREATE TABLE `yemek`  (
  `yemekid` int(11) NOT NULL AUTO_INCREMENT,
  `yadi` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `ykatid` int(11) NULL DEFAULT NULL,
  `maliyet` float(32, 0) NULL DEFAULT NULL,
  `satisFiyati` float(32, 0) NULL DEFAULT NULL,
  `aciklama` varchar(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  `resim` varchar(500) CHARACTER SET utf8 COLLATE utf8_turkish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`yemekid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 87 CHARACTER SET = utf8 COLLATE = utf8_turkish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of yemek
-- ----------------------------
INSERT INTO `yemek` VALUES (1, 'seçiniz', 0, 0, 0, '0', NULL);
INSERT INTO `yemek` VALUES (2, 'Ezogelin Çorbası', 2, 2, 5, 'sıcak içecek', NULL);
INSERT INTO `yemek` VALUES (3, 'Domates Çorbası', 2, 2, 4, 'sıcak içecek', NULL);
INSERT INTO `yemek` VALUES (4, 'Mercimek Çorbası', 2, 2, 5, 'sıcak içecek', NULL);
INSERT INTO `yemek` VALUES (5, 'İşkembe', 2, 3, 8, 'sıcak içecek', NULL);
INSERT INTO `yemek` VALUES (6, 'Kuzu Haşlama', 3, 10, 16, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (7, 'Kavurma', 3, 10, 14, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (8, 'Çoban Kavurma', 3, 10, 14, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (9, 'Dana Rosto', 3, 10, 14, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (10, 'Kuzu Tandır', 3, 10, 18, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (11, 'Fırında Kuzu Kızartma', 3, 10, 16, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (12, 'Et Sote', 3, 10, 14, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (13, 'Tas Kebabı', 3, 10, 14, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (14, 'Testi Kebabı', 3, 10, 15, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (15, 'Kuzu KaburgA', 3, 10, 15, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (16, 'Hünkar Beğendi', 3, 10, 16, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (17, 'Rosto Köfte', 3, 5, 11, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (18, 'İzmir Köfte', 3, 5, 11, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (19, 'Sebzeli Köfte', 3, 5, 11, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (20, 'Kadın Budu Köfte', 3, 5, 11, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (21, 'Ekşili Köfte', 3, 5, 11, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (22, 'Arnavut Ciğeri', 3, 5, 13, 'ciğer', NULL);
INSERT INTO `yemek` VALUES (23, 'Tavuk Sote', 4, 3, 10, 'tavuk yemeği', NULL);
INSERT INTO `yemek` VALUES (24, 'Tavuk Pirzola', 4, 3, 10, 'tavuk yemeği', NULL);
INSERT INTO `yemek` VALUES (25, 'Tavuk Sac Tava', 4, 3, 10, 'tavuk yemeği', NULL);
INSERT INTO `yemek` VALUES (26, 'Kuru Fasulye', 3, 2, 8, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (27, 'Patlıcanlı Fırın Kebabı', 3, 5, 12, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (28, 'Kağıt Kebabı', 3, 3, 11, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (29, 'Orman Kebabı', 3, 5, 13, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (30, 'İslim Kebabı', 3, 5, 14, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (31, 'Güveç', 3, 5, 13, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (32, 'Karnıyarık', 3, 3, 10, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (33, 'Kiremit Köfte', 3, 5, 16, 'et yemeği', NULL);
INSERT INTO `yemek` VALUES (34, 'İskender', 11, 5, 16, 'döner', NULL);
INSERT INTO `yemek` VALUES (35, 'Pilavüstü Döner', 11, 5, 16, 'döner', NULL);
INSERT INTO `yemek` VALUES (36, 'Dürüm Döner', 11, 3, 11, 'döner', NULL);
INSERT INTO `yemek` VALUES (37, 'Ala Balık Izgara', 5, 5, 20, 'balık', NULL);
INSERT INTO `yemek` VALUES (38, 'Levrek Izgara', 5, 5, 20, 'balık', NULL);
INSERT INTO `yemek` VALUES (39, 'Çipura Izgara', 5, 5, 20, 'balık', NULL);
INSERT INTO `yemek` VALUES (40, 'Somon Izgara', 5, 5, 20, 'balık', NULL);
INSERT INTO `yemek` VALUES (41, 'Hamsi Tava', 5, 5, 20, 'balık', NULL);
INSERT INTO `yemek` VALUES (42, 'Mezgit Tava', 5, 5, 20, 'balık', NULL);
INSERT INTO `yemek` VALUES (43, 'Kavurmalı Pide', 12, 5, 16, 'pide çeşitleri', NULL);
INSERT INTO `yemek` VALUES (44, 'Kuşbaşılı Pide', 12, 5, 16, 'pide çeşitleri', NULL);
INSERT INTO `yemek` VALUES (45, 'Karışık Pide', 12, 5, 17, 'pide çeşitleri', NULL);
INSERT INTO `yemek` VALUES (46, 'Kıymalı Pide', 12, 5, 13, 'pide çeşitleri', NULL);
INSERT INTO `yemek` VALUES (47, 'Sucuklu Pide', 12, 5, 14, 'pide çeşitleri', NULL);
INSERT INTO `yemek` VALUES (48, 'Kaşarlı Pide', 12, 5, 12, 'pide çeşitleri', NULL);
INSERT INTO `yemek` VALUES (49, 'Lahmacun', 12, 1, 3, 'pide çeşitleri', NULL);
INSERT INTO `yemek` VALUES (50, 'Serpme Kahvaltı', 7, 5, 18, 'kahvaltı', 'src/rsm/serpme.png');
INSERT INTO `yemek` VALUES (51, 'Sahanda Yumurta', 7, 1, 6, 'kahvaltı', NULL);
INSERT INTO `yemek` VALUES (52, 'Sucuklu Yumurta', 7, 1, 8, 'kahvaltı', NULL);
INSERT INTO `yemek` VALUES (53, 'Kavurmalı Yumurta', 7, 2, 9, 'kahvaltı', NULL);
INSERT INTO `yemek` VALUES (54, 'Pastırmalı Yumurta', 7, 3, 9, 'kahvaltı', NULL);
INSERT INTO `yemek` VALUES (55, 'Menemen', 7, 1, 6, 'kahvaltı', NULL);
INSERT INTO `yemek` VALUES (56, 'Kaşarlı Menemen', 7, 1, 7, 'kahvaltı', NULL);
INSERT INTO `yemek` VALUES (57, 'Paçanga Böreği', 6, 2, 7, 'hamur işleri', NULL);
INSERT INTO `yemek` VALUES (58, 'Sigara Böreği', 6, 1, 6, 'hamur işleri', NULL);
INSERT INTO `yemek` VALUES (59, 'Patates Kızartması', 7, 1, 6, 'kahvaltı', NULL);
INSERT INTO `yemek` VALUES (60, 'Muhlama', 7, 2, 10, 'kahvaltı', NULL);
INSERT INTO `yemek` VALUES (61, 'Çoban Salata', 13, 1, 8, 'salatalar', NULL);
INSERT INTO `yemek` VALUES (62, 'Kaşık Salata', 13, 1, 8, 'salatalar', NULL);
INSERT INTO `yemek` VALUES (63, 'Mevsim Salata', 13, 1, 8, 'salatalar', NULL);
INSERT INTO `yemek` VALUES (64, 'Sezar Salata ', 13, 3, 15, 'salatalar', NULL);
INSERT INTO `yemek` VALUES (65, 'Su Böreği', 6, 2, 8, 'hamur işleri', NULL);
INSERT INTO `yemek` VALUES (66, 'Sigara Böreği', 6, 2, 8, 'hamur işleri', NULL);
INSERT INTO `yemek` VALUES (67, 'Çay', 9, 1, 3, 'sıcak içecekler', 'src/img/tea-100x100.png');
INSERT INTO `yemek` VALUES (68, 'Türk Kahvesi', 9, 1, 8, 'sıcak içecekler', NULL);
INSERT INTO `yemek` VALUES (69, 'Gazlı İçecekler', 8, 1, 3, 'soğuk içecekler', NULL);
INSERT INTO `yemek` VALUES (70, 'Maden Suyu', 8, 1, 2, 'soğuk içecekler', NULL);
INSERT INTO `yemek` VALUES (71, 'Şalgam', 8, 1, 2, 'soğuk içecekler', NULL);
INSERT INTO `yemek` VALUES (72, 'Ayran', 8, 1, 3, 'soğuk içecekler', NULL);
INSERT INTO `yemek` VALUES (73, 'Cevizli Burma Kadayıf', 10, 3, 16, 'tatlılar', NULL);
INSERT INTO `yemek` VALUES (74, 'Fıstıklı Burma Kadayıf', 10, 4, 18, 'tatlılar', NULL);
INSERT INTO `yemek` VALUES (75, 'Cevizli Baklava', 10, 3, 15, 'tatlılar', NULL);
INSERT INTO `yemek` VALUES (76, 'Fıstıklı Baklava', 10, 4, 18, 'tatlılar', NULL);
INSERT INTO `yemek` VALUES (77, 'Künefe', 10, 2, 12, 'tatlılar', NULL);
INSERT INTO `yemek` VALUES (78, 'Supangle', 10, 2, 8, 'tatlılar', NULL);
INSERT INTO `yemek` VALUES (79, 'Profiterol', 10, 2, 8, 'tatlılar', NULL);
INSERT INTO `yemek` VALUES (80, 'Fırın Sütlaç', 10, 2, 8, 'tatlılar', NULL);
INSERT INTO `yemek` VALUES (81, 'Kazandibi', 10, 2, 8, 'tatlılar', NULL);
INSERT INTO `yemek` VALUES (82, 'Güllaç', 10, 2, 8, 'tatlılar', NULL);
INSERT INTO `yemek` VALUES (83, 'osmanlı şerbeti', 8, 4, 8, 'soguk icecek', 'C:\\\\Users\\\\elifs\\\\Desktop\\\\CafeHouse\\\\src\\\\rsm\\\\RamazanOsmanlı-Şerbeti-Tarifleri-100x100.jpg');
INSERT INTO `yemek` VALUES (84, 'osmanlı şerbeti', 8, 4, 8, 'soguk icecek', 'C:\\\\Users\\\\elifs\\\\Desktop\\\\CafeHouse\\\\src\\\\rsm\\\\RamazanOsmanlı-Şerbeti-Tarifleri-100x100.jpg');
INSERT INTO `yemek` VALUES (85, 'latte', 9, 5, 12, 'sıcak içeçek', 'C:\\\\Users\\\\elifs\\\\Desktop\\\\CafeHouse\\\\src\\\\rsm\\\\ci-coffee_latte.jpg');
INSERT INTO `yemek` VALUES (86, 'aşure', 10, 5, 9, 'bol  çeşitli', 'C:\\\\Users\\\\elifs\\\\Desktop\\\\CafeHouse\\\\src\\\\rsm\\\\kuru-meyveli-asure-100x100.png');

-- ----------------------------
-- Procedure structure for adisyon
-- ----------------------------
DROP PROCEDURE IF EXISTS `adisyon`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `adisyon`(IN `masaid` int(11))
BEGIN
	select m.masaid,madi,y.yadi,sadet,y.satisFiyati,ma.menuadi,s.madet,ma.menufiyat from 
siparis as s 
inner join menuadi as ma
on s.smenuid=menuid
inner join yemek as y
on s.syemekid=y.yemekid
inner join masa as m
on s.smasaid=m.masaid  and m.masaid=masaid where s.sdurum = 'aktif' and s.shazir=2 GROUP BY masaid;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for adisyonGetir
-- ----------------------------
DROP PROCEDURE IF EXISTS `adisyonGetir`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `adisyonGetir`(IN `smasaid` int(11))
BEGIN
	select smasaid,sgarsonid,syemekid,sadet from siparis where siparis.smasaid=smasaid;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for adisyonTakip
-- ----------------------------
DROP PROCEDURE IF EXISTS `adisyonTakip`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `adisyonTakip`()
BEGIN
	select m.masaid,madi,y.yadi,sadet,y.satisFiyati,ma.menuadi,ma.madet,ma.menufiyat from 
siparis as s 
inner join menuadi as ma
on s.smenuid=menuid
inner join yemek as y
on s.syemekid=y.yemekid
inner join masa as m
on s.smasaid=m.masaid  and m.masaid=masaid where s.sdurum = 'aktif';

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for arama
-- ----------------------------
DROP PROCEDURE IF EXISTS `arama`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `arama`(IN `ara` varchar(255))
BEGIN
	SELECT * FROM kullanicilar
	where
	match(kullaniciAdi,kullaniciSoyadi)
	against(ara in boolean MODE);

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for asciLabelYazdir
-- ----------------------------
DROP PROCEDURE IF EXISTS `asciLabelYazdir`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `asciLabelYazdir`(IN `secim` int(11))
BEGIN
select m.masaid,m.madi,y.yadi,s.sadet,me.menuadi,s.madet,y.resim,me.menuresim from siparis as s
inner join masa as m
on s.smasaid=m.masaid
inner join menuadi as me
on s.smenuid=me.menuid
inner join yemek as y
on s.syemekid=y.yemekid where s.sdurum='aktif' and m.masaid=secim;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for asciMenuGetir
-- ----------------------------
DROP PROCEDURE IF EXISTS `asciMenuGetir`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `asciMenuGetir`()
BEGIN
  select * FROM menuadi;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for asciPanel
-- ----------------------------
DROP PROCEDURE IF EXISTS `asciPanel`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `asciPanel`()
BEGIN
	select m.masaid,madi,y.yadi,s.sadet,ma.menuadi,s.madet from 
siparis as s 
inner join menuadi as ma
on s.smenuid=menuid
inner join yemek as y
on s.syemekid=y.yemekid
inner join masa as m
on s.smasaid=m.masaid  where s.sdurum='aktif' and s.shazir=0 GROUP BY  m.masaid;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for asciSiparisGoruntu
-- ----------------------------
DROP PROCEDURE IF EXISTS `asciSiparisGoruntu`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `asciSiparisGoruntu`(IN `secim` int(11))
BEGIN
	Select *from siparis as s  LEFT JOIN menuadi as m on m.menuid = s.smenuid  LEFT JOIN yemek as y on y.yemekid = s.syemekid where s.smasaid=secim and (s.syemekid>1 or s.smenuid>1) and s.sdurum = 'aktif';

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for asciSşparisHazir
-- ----------------------------
DROP PROCEDURE IF EXISTS `asciSşparisHazir`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `asciSşparisHazir`(IN `secim` int(11))
BEGIN
update siparis SET siparis.shazir=1 WHERE siparis.smasaid=secim;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for asciYemekGetir
-- ----------------------------
DROP PROCEDURE IF EXISTS `asciYemekGetir`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `asciYemekGetir`()
BEGIN
	select * from yemek;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for bul
-- ----------------------------
DROP PROCEDURE IF EXISTS `bul`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `bul`(IN `ara` varchar(255))
BEGIN
	SELECT pid,tc,ad,soyad from garson
WHERE garson.gadi
LIKE CONCAT('%',ara,'%')
or garson.gsoyadi LIKE CONCAT('%',ara,'%');

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for durumGuncelle
-- ----------------------------
DROP PROCEDURE IF EXISTS `durumGuncelle`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `durumGuncelle`(IN `masaid` int(11))
BEGIN
	update siparis set siparis.sdurum ='pasif' where smasaid=masaid;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for garsonHaziriGordu
-- ----------------------------
DROP PROCEDURE IF EXISTS `garsonHaziriGordu`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `garsonHaziriGordu`(IN `alindi` int(11))
BEGIN
	update siparis set siparis.shazir=2 where siparis.smasaid=alindi;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for garsonHazirSiparisler
-- ----------------------------
DROP PROCEDURE IF EXISTS `garsonHazirSiparisler`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `garsonHazirSiparisler`()
BEGIN
	select m.masaid,m.madi,k.kullaniciAdi,y.yadi,s.sadet,ma.menuadi,s.madet from 
siparis as s 
inner join menuadi as ma
on s.smenuid=menuid
inner join yemek as y
on s.syemekid=y.yemekid
inner join masa as m
on s.smasaid=m.masaid inner join kullanicilar as k
on k.kgorevid=s.sgarsonid
where s.sdurum='aktif' and s.shazir=1 GROUP BY  m.masaid;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for gorevGetir
-- ----------------------------
DROP PROCEDURE IF EXISTS `gorevGetir`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `gorevGetir`()
BEGIN
	select * from gorev;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for kampanyaDoldur
-- ----------------------------
DROP PROCEDURE IF EXISTS `kampanyaDoldur`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `kampanyaDoldur`()
BEGIN
	select * FROM menuadi;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for kampanyaGetir
-- ----------------------------
DROP PROCEDURE IF EXISTS `kampanyaGetir`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `kampanyaGetir`()
BEGIN
	select menuid,menuadi,menubaslangic,menubitis,menufiyat FROM menuadi;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for kampanyaliMenuOlustur
-- ----------------------------
DROP PROCEDURE IF EXISTS `kampanyaliMenuOlustur`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `kampanyaliMenuOlustur`(IN `menuadi` varchar(255),IN `menubaslangic` varchar(255),IN `menubitis` varchar(255),IN `menufiyat` float(32),IN `menumaliyet` float(32),IN `madet` int(11),IN `menuresim` varchar(255))
BEGIN
insert into menuadi values (null,menuadi,menubaslangic,menubitis,menufiyat,menumaliyet,madet,menuresim);

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for kampanyaliMenuTabloDoldur
-- ----------------------------
DROP PROCEDURE IF EXISTS `kampanyaliMenuTabloDoldur`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `kampanyaliMenuTabloDoldur`()
BEGIN
	select menuid,menuadi,menubaslangic,menubitis,menufiyat,menumaliyet from menuadi;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for kategoriGetir
-- ----------------------------
DROP PROCEDURE IF EXISTS `kategoriGetir`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `kategoriGetir`()
BEGIN
	select * from kategori;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for login
-- ----------------------------
DROP PROCEDURE IF EXISTS `login`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `login`(IN `kullanici` varchar(255),IN `sifre` varchar(255))
BEGIN
	SELECT kgorevid,kullaniciAdi FROM kullanicilar as k
  WHERE k.kullaniciAdi LIKE CONCAT('%',kullanici,'%') or k.ksifre LIKE CONCAT('%',sifre,'%');

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for loginGirisi
-- ----------------------------
DROP PROCEDURE IF EXISTS `loginGirisi`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `loginGirisi`(IN `kullaniciadi`varchar(255),IN `kullanicisifre`varchar(255))
BEGIN
	 select * from kullanicilar as k 
	inner join gorev as g on g.gorevid=k.kgorevid
	where k.kullaniciAdi=kullaniciadi and k.ksifre=kullanicisifre;


END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for masaEkle
-- ----------------------------
DROP PROCEDURE IF EXISTS `masaEkle`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `masaEkle`(IN `masaadi` varchar(255))
BEGIN
	
insert into masa values(null,masaadi,null,null);
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for masaGetir
-- ----------------------------
DROP PROCEDURE IF EXISTS `masaGetir`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `masaGetir`()
BEGIN
	select * from masa;
	

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for mPersonelEkle
-- ----------------------------
DROP PROCEDURE IF EXISTS `mPersonelEkle`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `mPersonelEkle`(IN `ad` varchar(255),IN `soyad` varchar(255),IN `sube` varchar(255),IN `durum` varchar(255),IN `sifre` varchar(255))
BEGIN
	insert into kullanicilar values(null,kTC,kullaniciAdi,kullaniciSoyadi,ktel,kmail,kadres,kgorevid,ksifre,kresim,kdurum);

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for mudurSifre
-- ----------------------------
DROP PROCEDURE IF EXISTS `mudurSifre`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `mudurSifre`(IN `kullaniciadi` varchar(255),IN `sifre` varchar(255),IN `yeniSifre` varchar(255))
BEGIN
		update kullanicilar set kullanicilar.ksifre=yeniSifre where kullaniciAdi=kullaniciadi and ksifre=sifre;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for NewProc
-- ----------------------------
DROP PROCEDURE IF EXISTS `NewProc`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `NewProc`(IN `yemekadi` varchar(255),IN `yemekkategori` int(11),IN `maliyet` float(32),IN `satisfiyati` float(32),IN `aciklama` varchar(255),IN `resim` varchar(255))
BEGIN
insert into yemek values(null,yemekadi,yemekkategori,maliyet,satisfiyati,aciklama,resim);

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for personelAyrilan
-- ----------------------------
DROP PROCEDURE IF EXISTS `personelAyrilan`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `personelAyrilan`()
BEGIN
	select sifreid,kullaniciAdi,kullaniciSoyadi from kullanicilar where kdurum='pasif';


END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for personelData
-- ----------------------------
DROP PROCEDURE IF EXISTS `personelData`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `personelData`()
BEGIN
select sifreid,kullaniciAdi,kullaniciSoyadi from kullanicilar where kdurum='aktif';

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for personelDuzenle
-- ----------------------------
DROP PROCEDURE IF EXISTS `personelDuzenle`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `personelDuzenle`(IN `mid` int(11),IN `kTC` varchar(255),IN `kullaniciAdi` varchar(255),IN `kullaniciSoyadi` varchar(255),IN `ktel` varchar(255),IN `kmail` varchar(255),IN `kadres` varchar(255),IN `kgorevid` int(11),IN `ksifre` varchar(255),IN `kresim` varchar(255),IN `kdurum` varchar(255))
BEGIN
	update kullanicilar set kTC=kTC,kullaniciAdi=kullaniciAdi,kullaniciSoyadi=kullaniciSoyadi,ktel=ktel,kmail=kmail,kadres=kadres,
	kgorevid=kgorevid,ksifre=ksifre,kresim=kresim,kdurum=kdurum where kullanicilar.sifreid=mid;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for personelEkle
-- ----------------------------
DROP PROCEDURE IF EXISTS `personelEkle`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `personelEkle`(IN `kTC` varchar(255),IN `kullaniciAdi` varchar(255),IN `kullaniciSoyadi` varchar(255),IN `ktel` varchar(255),IN `kmail` varchar(255),IN `kadres` varchar(255),IN `kgorevid` int(11),IN `ksifre` varchar(255),IN `kresim` varchar(255),IN `kdurum` varchar(255))
BEGIN
	
	insert into kullanicilar values(null,kTC,kullaniciAdi,kullaniciSoyadi,ktel,kmail,kadres,kgorevid,ksifre,kresim,kdurum);

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for personelGetir
-- ----------------------------
DROP PROCEDURE IF EXISTS `personelGetir`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `personelGetir`(IN `mid` int(11))
BEGIN
	select * from kullanicilar where kdurum='aktif' and kullanicilar.sifreid=mid;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for personelSil
-- ----------------------------
DROP PROCEDURE IF EXISTS `personelSil`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `personelSil`(IN `mid` int(11))
BEGIN
UPDATE kullanicilar SET kullanicilar.kdurum='pasif' WHERE kullanicilar.sifreid=mid;


END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for siparisDoldur
-- ----------------------------
DROP PROCEDURE IF EXISTS `siparisDoldur`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `siparisDoldur`()
BEGIN
	select * from siparis as s WHERE s.sdurum='aktif';

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for siparisEkle
-- ----------------------------
DROP PROCEDURE IF EXISTS `siparisEkle`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `siparisEkle`(IN `subeid` int(11),IN `smasaid` int(11),IN `sgarsonid` int(11),IN `syemekid` int(11),IN `smenuid` int(11),IN `sadet` int(11),IN `madet` int(11),IN `sdurum` varchar(255),IN `shazir` int(11),IN `starih` datetime(0))
BEGIN
insert into siparis values(null,subeid,smasaid,sgarsonid,syemekid,smenuid,sadet,madet,sdurum,shazir,starih);

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for siparisGetir
-- ----------------------------
DROP PROCEDURE IF EXISTS `siparisGetir`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `siparisGetir`()
BEGIN
select s.sipid,m.madi,y.yadi,me.menuadi,me.menubaslangic,me.menubitis,me.menufiyat,y.satisFiyati from siparis as s
inner join masa as m
on s.smasaid=m.masaid
inner join menuadi as me
on s.smenuid=me.menuid
inner join yemek as y
on s.syemekid=y.yemekid where shazir=0;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for siparisIcerik
-- ----------------------------
DROP PROCEDURE IF EXISTS `siparisIcerik`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `siparisIcerik`(IN `secim` int(11))
BEGIN
	select m.masaid,madi,y.yadi,s.sadet,y.resim,ma.menuadi,ma.madet,ma.menuresim from 
siparis as s 
inner join menuadi as ma

inner join yemek as y
on s.syemekid=y.yemekid
inner join masa as m
on s.smasaid=m.masaid and m.masaid=secim ;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for siparisIptal
-- ----------------------------
DROP PROCEDURE IF EXISTS `siparisIptal`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `siparisIptal`(IN `sipid` int(11))
BEGIN
	delete from siparis where siparis.smasaid=sipid;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for siparisRaporlama
-- ----------------------------
DROP PROCEDURE IF EXISTS `siparisRaporlama`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `siparisRaporlama`(IN `baslangic` varchar(255),IN `bitis` varchar(255))
BEGIN
select s.sipid,m.madi,y.yadi,me.menuadi,me.menufiyat,y.satisfiyati from siparis as s
	inner join masa as m
	on s.smasaid=m.masaid
	inner join menuadi as me
	on s.smenuid=me.menuid
	inner join yemek as y
	on s.syemekid=y.yemekid  WHERE starih BETWEEN baslangic AND bitis;



END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for siparisRaporlamaTabloDoldur
-- ----------------------------
DROP PROCEDURE IF EXISTS `siparisRaporlamaTabloDoldur`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `siparisRaporlamaTabloDoldur`(IN `baslangic` varchar(255),IN `bitis` varchar(255))
BEGIN
	select s.sipid,m.madi,y.yadi,y.maliyet,s.sadet,y.satisfiyati,me.menuadi,me.menumaliyet,s.madet,me.menufiyat from siparis as s
	inner join masa as m
	on s.smasaid=m.masaid
	inner join menuadi as me
	on s.smenuid=me.menuid
	inner join yemek as y
	on s.syemekid=y.yemekid WHERE starih BETWEEN baslangic AND bitis ORDER BY s.sipid ASC;



END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for siparisTasima
-- ----------------------------
DROP PROCEDURE IF EXISTS `siparisTasima`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `siparisTasima`(IN `tasinacakmasa` int(11),IN `yenimasa` int(11))
BEGIN
	update siparis set siparis.smasaid=yenimasa where smasaid=tasinacakmasa;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for yemekAciklama
-- ----------------------------
DROP PROCEDURE IF EXISTS `yemekAciklama`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `yemekAciklama`()
BEGIN
select yemekid,yadi,satisFiyati,aciklama from yemek;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for yemekEkle
-- ----------------------------
DROP PROCEDURE IF EXISTS `yemekEkle`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `yemekEkle`(IN `yemekadi` varchar(255),IN `yemekkategori` int(11),IN `maliyet` float(32),IN `satisfiyati` float(32),IN `aciklama` varchar(255),IN `resim` varchar(255))
BEGIN
insert into yemek values(null,yemekadi,yemekkategori,maliyet,satisfiyati,aciklama,resim);

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for yemekGetir
-- ----------------------------
DROP PROCEDURE IF EXISTS `yemekGetir`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `yemekGetir`(IN `ykatid` int(11))
BEGIN
	select * from
	yemek WHERE yemek.ykatid=ykatid;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for yemekTabloDoldur
-- ----------------------------
DROP PROCEDURE IF EXISTS `yemekTabloDoldur`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `yemekTabloDoldur`()
BEGIN
	select yemekid,yadi,ykatid,maliyet,satisFiyati,aciklama from yemek;

END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
