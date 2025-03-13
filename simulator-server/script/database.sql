CREATE DATABASE `channel_mock` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE channel_mock;

/* create tables */;

CREATE TABLE `hit_rule` (
                            `id` int(11) NOT NULL,
                            `channel_id` varchar(255) DEFAULT NULL,
                            `path` varchar(255) DEFAULT NULL,
                            `request_method` varchar(20) DEFAULT NULL,
                            `content_type` varchar(255) DEFAULT NULL,
                            `path_variable_rule` text,
                            `action_type` varchar(20) DEFAULT NULL,
                            `action_id` int(11) DEFAULT NULL,
                            `response_code` varchar(20) DEFAULT NULL,
                            `response` text,
                            `created_at` datetime DEFAULT NULL,
                            `updated_at` datetime DEFAULT NULL,
                            `description` text,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `delete_action` (
                                 `id` int(11) NOT NULL,
                                 `delete_key_script` text,
                                 `delete_condition` text,
                                 `response_code` varchar(20) DEFAULT NULL,
                                 `response` text,
                                 `created_at` datetime DEFAULT NULL,
                                 `updated_at` datetime DEFAULT NULL,
                                 `description` text,
                                 `not_found_response` text,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `query_cache_action` (
                                      `id` int(11) NOT NULL,
                                      `query_key_script` text,
                                      `response_code` varchar(20) DEFAULT NULL,
                                      `created_at` datetime DEFAULT NULL,
                                      `updated_at` datetime DEFAULT NULL,
                                      `description` text,
                                      `not_found_response` text,
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `save_action` (
                               `id` int(11) NOT NULL,
                               `generate_key_script` text,
                               `cache_ttl` int(11) DEFAULT NULL,
                               `created_at` datetime DEFAULT NULL,
                               `updated_at` datetime DEFAULT NULL,
                               `description` text,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `update_action` (
                                 `id` int(11) NOT NULL,
                                 `update_key_script` text,
                                 `update_rule` text,
                                 `update_condition` text,
                                 `response_code` varchar(20) DEFAULT NULL,
                                 `response` text,
                                 `created_at` datetime DEFAULT NULL,
                                 `updated_at` datetime DEFAULT NULL,
                                 `description` text,
                                 `not_found_response` text,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/* create demo rules in table hit_rule*/

-- 插入第一条数据
INSERT INTO `channel_mock`.`hit_rule`
(`id`,
 `channel_id`,
 `path`,
 `request_method`,
 `content_type`,
 `path_variable_rule`,
 `action_type`,
 `action_id`,
 `response_code`,
 `response`,
 `created_at`,
 `updated_at`,
 `description`)
VALUES
    (85,
     'stripe',
     '/payments/.*',
     'DELETE',
     'application/json',
     '{"regexp": "/payments/.*","keys": ["paymentOrderId"]}',
     'delete_cache',
     851,
     200,
     '',
     NULL,
     NULL,
     '');

-- 插入第二条数据
INSERT INTO `channel_mock`.`hit_rule`
(`id`,
 `channel_id`,
 `path`,
 `request_method`,
 `content_type`,
 `path_variable_rule`,
 `action_type`,
 `action_id`,
 `response_code`,
 `response`,
 `created_at`,
 `updated_at`,
 `description`)
VALUES
    (86,
     'stripe',
     '/payments/capture',
     'POST',
     'application/json',
     '',
     'update_cache',
     861,
     200,
     '{"paymentOrderId": "$body.paymentOrderId","action": "capture","result": "succeeded"}',
     NULL,
     NULL,
     '');

-- 插入第三条数据
INSERT INTO `channel_mock`.`hit_rule`
(`id`,
 `channel_id`,
 `path`,
 `request_method`,
 `content_type`,
 `path_variable_rule`,
 `action_type`,
 `action_id`,
 `response_code`,
 `response`,
 `created_at`,
 `updated_at`,
 `description`)
VALUES
    (87,
     'stripe',
     '/payments/.*',
     'GET',
     'application/json',
     '{"regexp": "/payments/.*","keys": ["paymentOrderId"]}',
     'query_cache',
     871,
     200,
     '{   "payment_order_id": "$id",   "token": "$params.token",   "merchant_id": "$params.merchantId",   "amount": $body.amount,   "currency": "$body.currency",   "approved": true,   "status": "Authorized",   "auth_code": "770687",   "response_code": "10000",   "response_summary": "Approved",   "3ds": {     "downgraded": true,     "enrolled": "N"   }  }',
     NULL,
     NULL,
     '');

-- 插入第四条数据
INSERT INTO `channel_mock`.`hit_rule`
(`id`,
 `channel_id`,
 `path`,
 `request_method`,
 `content_type`,
 `path_variable_rule`,
 `action_type`,
 `action_id`,
 `response_code`,
 `response`,
 `created_at`,
 `updated_at`,
 `description`)
VALUES
    (88,
     'stripe',
     '/payments/merchantId/.*/token/.*',
     'POST',
     'application/json',
     '{"regexp": "/payments/merchantId/.*/token/.*","keys": ["merchantId", "token"]}',
     'assemble_and_cache',
     881,
     200,
     '{   "payment_order_id": "$id",   "token": "$params.token",   "merchant_id": "$params.merchantId",   "amount": $body.amount,   "currency": "$body.currency",   "approved": true,   "status": "Authorized",   "auth_code": "770687",   "response_code": "10000",   "response_summary": "Approved",   "3ds": {     "downgraded": true,     "enrolled": "N"   }  }',
     NULL,
     NULL,
     '');

/* create demo rules in table delete_action*/
INSERT INTO `channel_mock`.`delete_action`
(`id`,
 `delete_key_script`,
 `delete_condition`,
 `response_code`,
 `response`,
 `created_at`,
 `updated_at`,
 `description`,
 `not_found_response`)
VALUES
    (851,
     'def rule_script() { return context.getAt("params").getAt("paymentOrderId") }',
     '',
     200,
     '{"paymentOrderId": "$params.paymentOrderId","action":"delete", "status":"succeeded"}',
     NULL,
     NULL,
     '',
     '$params.paymentOrderId not found');


/* create demo rules in table query_cache_action*/
INSERT INTO `channel_mock`.`query_cache_action`
(`id`,
 `query_key_script`,
 `response_code`,
 `created_at`,
 `updated_at`,
 `description`,
 `not_found_response`)
VALUES
    (871,
     'def rule_script() { return context.getAt("params").getAt("paymentOrderId")}',
     200,
     NULL,
     NULL,
     '',
     '$params.paymentOrderId not found');

/* create demo rules in table save_action*/
INSERT INTO `channel_mock`.`save_action`
(`id`,
 `generate_key_script`,
 `cache_ttl`,
 `created_at`,
 `updated_at`,
 `description`)
VALUES
    (881,
     'import java.util.UUID \ndef rule_script() {  return context.getAt(\"headers\").getAt(\"channel_id\") + \"_pay_\" + UUID.randomUUID().toString().replaceAll(\"-\", \"\").substring(0, 16)  } ',
     1,
     NULL,
     NULL,
     NULL);

/* create demo rules in table update_action*/
INSERT INTO `channel_mock`.`update_action`
(`id`,
 `update_key_script`,
 `update_rule`,
 `update_condition`,
 `response_code`,
 `response`,
 `created_at`,
 `updated_at`,
 `description`,
 `not_found_response`)
VALUES
    (861,
     'def rule_script() { return context.getAt(\"body\").getAt(\"paymentOrderId\") } ',
     '{\"targetNode\":\"status\", \"targetValue\":\"Captured\"}',
     NULL,
     200,
     '{\"paymentOrderId\": \"$body.paymentOrderId\",\"action\": \"capture\",\"result\": \"succeeded\"}',
     NULL,
     NULL,
     NULL,
     '$body.paymentOrderId not found');