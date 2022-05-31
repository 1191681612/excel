/*
 Navicat Premium Data Transfer

 Source Server         : postgres
 Source Server Type    : PostgreSQL
 Source Server Version : 140003
 Source Host           : localhost:5432
 Source Catalog        : test
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 140003
 File Encoding         : 65001

 Date: 31/05/2022 06:46:33
*/


-- ----------------------------
-- Table structure for mm_scrap
-- ----------------------------
DROP TABLE IF EXISTS "public"."mm_scrap";
CREATE TABLE "public"."mm_scrap" (
  "id" uuid NOT NULL DEFAULT uuid_generate_v4(),
  "year" int2,
  "month" int2,
  "pu" varchar(4) COLLATE "pg_catalog"."default",
  "model" varchar(10) COLLATE "pg_catalog"."default",
  "production_name" varchar(10) COLLATE "pg_catalog"."default",
  "input_qty" int4,
  "scrap_qty" int4,
  "scrap_rate" numeric,
  "scrap_total_amount" numeric,
  "scrap_average_amount" numeric,
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."mm_scrap"."year" IS '年';
COMMENT ON COLUMN "public"."mm_scrap"."month" IS '月';
COMMENT ON COLUMN "public"."mm_scrap"."pu" IS 'pu';
COMMENT ON COLUMN "public"."mm_scrap"."model" IS 'model';
COMMENT ON COLUMN "public"."mm_scrap"."production_name" IS '产品名字';
COMMENT ON COLUMN "public"."mm_scrap"."input_qty" IS '投入数量';
COMMENT ON COLUMN "public"."mm_scrap"."scrap_qty" IS '报废数量';
COMMENT ON COLUMN "public"."mm_scrap"."scrap_rate" IS '报废率';
COMMENT ON COLUMN "public"."mm_scrap"."scrap_total_amount" IS '报废总金额';
COMMENT ON COLUMN "public"."mm_scrap"."scrap_average_amount" IS '每台平均金额';
COMMENT ON COLUMN "public"."mm_scrap"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."mm_scrap"."update_time" IS '修改时间';

-- ----------------------------
-- Records of mm_scrap
-- ----------------------------
INSERT INTO "public"."mm_scrap" VALUES ('a3769400-9384-4c0b-bccb-5a37d702d2b3', 2022, 4, 'PU3', 'FA', 'TOP', 525924, 633, 0.1204, 65869.92, 0.27, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('2371049d-519d-4865-8a63-782c2f50ca77', 2022, 4, 'PU7', 'SMT', '31MB', 404689, 0, 0, 10385.06, 0.04, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('edc8bdd3-35ba-4000-98ba-05412363ecd6', 2022, 4, 'PU7', 'FA', 'KB', 386400, 0, 0, 4400.59, 0.04, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('9e8fabdf-6e20-4bc9-a5db-68a5c5f3f4d0', 2022, 4, 'PU4', 'SMT', '31MB', 475279, 18, 0.0038, 6709.07, 0.03, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('310dd5db-c97c-4484-80b0-93c679d3ae61', 2022, 4, 'PU4', 'FA', '31MB', 255522, 1, 0.0004, 1802.11, 0.03, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('fb77f863-3beb-4eca-a7db-11d193f76001', 2022, 4, 'PU5', 'SMT', 'CPU', 1360481, 0, 0, 167638.97, 0.15, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('d4643039-bb9d-4f5d-a3d1-20d831391f73', 2022, 4, 'PU5', 'FA', 'TOP', 1623554, 2687, 0.1655, 81382.8, 0.15, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('80ace8f2-6e5e-44cc-8e30-ba673a64824e', 2022, 4, 'PU7', 'FA', 'COVER', 386400, 0, 0, 4400.59, 0.04, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('120f6a7a-f0c6-410d-ac6b-839af91829ab', 2022, 4, 'PU4', 'SMT', 'CPU', 475279, 25, 0.0053, 6709.07, 0.03, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('a7434370-eb9d-4d83-ae31-4a869fe6bf3a', 2022, 4, 'PU4', 'FA', 'BEZEL', 255522, 96, 0.0376, 1802.11, 0.03, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('d4dbd735-75a0-4ef6-abdb-de66a6a4f97e', 2022, 4, 'PU4', 'FA', 'MASTER', 255522, 0, 0, 1802.11, 0.03, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('f7ff07f9-92c4-441c-8bf6-84257a2e2065', 2022, 4, 'PU5', 'SMT', '31MB', 1360481, 0, 0, 167638.97, 0.15, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('052a8b92-cc85-4b2a-9630-e53410352696', 2022, 4, 'PU3', 'FA', 'KB', 525924, 0, 0, 65869.92, 0.27, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('77a338ea-3d8c-4e2e-957e-1ec09c0ab66a', 2022, 4, 'PU5', 'FA', 'MASTER', 1623554, 145, 0.0089, 81382.8, 0.15, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('548f32ef-a78a-465d-b63c-17574e389aea', 2022, 4, 'PU7', 'FA', 'TOP', 386400, 779, 0.2016, 4400.59, 0.04, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('807f6f9c-2153-461c-9b57-b442cab27f54', 2022, 4, 'PU4', 'FA', 'TOP', 255522, 315, 0.1233, 1802.11, 0.03, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('ee476376-eb6a-4f1b-8c36-36069b5c3b1c', 2022, 4, 'PU5', 'FA', 'BEZEL', 1623554, 185, 0.0114, 81382.8, 0.15, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('d496a8dc-0110-4ab3-8633-19475b6df76e', 2022, 4, 'PU3', 'FA', 'MASTER', 525924, 285, 0.0542, 65869.92, 0.27, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('13c1f609-9c68-43c6-95f8-05a656610814', 2022, 4, 'PU7', 'FA', '99MB', 386400, 230, 0.0595, 4400.59, 0.04, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('64bc4b92-4f58-49c0-b052-373f6501ce55', 2022, 4, 'PU7', 'SMT', 'CPU', 404689, 0, 0, 10385.06, 0.04, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('6de8add4-2431-40e6-b71d-bf6c943146bc', 2022, 4, 'PU5', 'FA', '99MB', 1623554, 2291, 0.1411, 81382.8, 0.15, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('90681341-a686-456c-bbdc-c2328c9f17c5', 2022, 4, 'PU3', 'SMT', '31MB', 670132, 28, 0.0042, 76590.29, 0.27, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('648fa166-6fb9-4a21-99e3-17be5a09f95a', 2022, 4, 'PU5', 'SMT', '99MB', 1360481, 5807, 0.4268, 167638.97, 0.15, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('76746e19-2991-4804-80f4-f3ea1fc306a2', 2022, 4, 'PU4', 'FA', 'KB', 255522, 0, 0, 1802.11, 0.03, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('415eba4b-e356-48bf-9a3c-317365e7f351', 2022, 4, 'PU5', 'FA', '31MB', 1623554, 0, 0, 81382.8, 0.15, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('dbe4fc44-b69a-4aad-b1e4-6a2497d5f0bf', 2022, 4, 'PU7', 'FA', '31MB', 386400, 0, 0, 4400.59, 0.04, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('fcb5c4a3-6b4a-4e51-9f75-8b327cb13f33', 2022, 4, 'PU3', 'SMT', '99MB', 670132, 2249, 0.3356, 76590.29, 0.27, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('558d184a-9cef-4759-b8f4-2955f668eb67', 2022, 4, 'PU5', 'FA', 'COVER', 1623554, 2916, 0.1796, 81382.8, 0.15, '2022-05-19 15:15:53.52', '2022-05-19 15:15:53.52');
INSERT INTO "public"."mm_scrap" VALUES ('33184ef1-3399-4a0e-b539-0b82814539cc', 2022, 4, 'PU5', 'CAMERA', 'CAMERA', 2730682, 5530, 0.2025, 29242.18, 0.01, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('97f6bef5-e4c3-449e-9d1e-e4c8cafe637d', 2022, 4, 'PU4', 'SMT', '99MB', 475279, 158, 0.0332, 6709.07, 0.03, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('b3af44d8-9b51-4203-a36d-0b09a922bafb', 2022, 4, 'PU7', 'FA', 'BASE', 386400, 0, 0, 4400.59, 0.04, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('c7c16849-296a-4ec2-b92b-2c58f86724de', 2022, 4, 'PU4', 'FA', 'COVER', 255522, 347, 0.1358, 1802.11, 0.03, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('055a764b-bc0c-4389-8d39-fccd2fb16a00', 2022, 4, 'PU7', 'FA', 'BEZEL', 386400, 0, 0, 4400.59, 0.04, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('ed065ffd-5a06-4fd5-8672-58cee5802351', 2022, 4, 'PU5', 'FA', 'KB', 1623554, 1307, 0.0805, 81382.8, 0.15, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('0b5aafb6-cf3e-4573-bbbd-c046cffce8f2', 2022, 4, 'PU3', 'SMT', 'CPU', 670132, 14, 0.0021, 76590.29, 0.27, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('89689911-3063-490d-a2ce-1949a5337755', 2022, 4, 'PU3', 'FA', '31MB', 525924, 0, 0, 65869.92, 0.27, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('051dfce5-e606-4301-95e9-6f3053d5c2fb', 2022, 4, 'PU3', 'FA', 'BASE', 525924, 650, 0.1236, 65869.92, 0.27, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('c2c4ed69-163c-45c0-bdbd-ead78880393e', 2022, 4, 'PU3', 'FA', '99MB', 525924, 907, 0.1725, 65869.92, 0.27, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('13921f7c-49fc-437b-8cb6-2d5ad633732e', 2022, 4, 'PU4', 'FA', 'BASE', 255522, 288, 0.1127, 1802.11, 0.03, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('9e69c361-d240-4d71-8964-c727a9e50a42', 2022, 4, 'PU7', 'SMT', '99MB', 404689, 660, 0.1631, 10385.06, 0.04, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('fb87e963-b94d-416d-b494-5800461eca61', 2022, 4, 'PU7', 'FA', 'MASTER', 386400, 0, 0, 4400.59, 0.04, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('16bd2879-de39-4e8b-98ed-e3e166609a35', 2022, 4, 'PU3', 'FA', 'BEZEL', 525924, 751, 0.1428, 65869.92, 0.27, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('817cd86a-0591-41cd-9f61-86eb129c06c5', 2022, 4, 'PU4', 'FA', '99MB', 255522, 20, 0.0078, 1802.11, 0.03, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('02a351cf-8401-468f-9f73-634b383af9f5', 2022, 4, 'PU5', 'FA', 'BASE', 1623554, 2507, 0.1544, 81382.8, 0.15, '2022-05-19 15:15:53.55', '2022-05-19 15:15:53.55');
INSERT INTO "public"."mm_scrap" VALUES ('0e454151-c3d0-4f72-bd3a-dd6184950c87', 2022, 4, 'PU3', 'FA', 'COVER', 525924, 1477, 0.2808, 65869.92, 0.27, '2022-05-19 15:15:53.533', '2022-05-19 15:15:53.533');

-- ----------------------------
-- Primary Key structure for table mm_scrap
-- ----------------------------
ALTER TABLE "public"."mm_scrap" ADD CONSTRAINT "mm_scrap_pk" PRIMARY KEY ("id");
