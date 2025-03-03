CREATE TABLE `cache_rule`
(
    `id`                            varchar(255) NOT NULL,
    `mock_rule_id`                  varchar(255) DEFAULT NULL,
    `cache_option`                  varchar(255) DEFAULT NULL,
    `req_cache_rule`                varchar(255) DEFAULT NULL,
    `cache_body`                    text,
    `cache_body_match_rule`         varchar(255) DEFAULT NULL,
    `cache_time`                    bigint(20) DEFAULT NULL,
    `match_error_response_template` text,
    `null_response_template`        text,
    `response_template`             text,
    `match_status_code`             varchar(255) DEFAULT NULL,
    `null_match_status_code`        varchar(255) DEFAULT NULL,
    `match_error_status_code`       varchar(255) DEFAULT NULL,
    `created`                       datetime     DEFAULT NULL,
    `updated`                       datetime     DEFAULT NULL,
    `remark`                        text,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `mock_rule`
(
    `id`                varchar(255) NOT NULL,
    `channel_id`        varchar(255) DEFAULT NULL,
    `path`              varchar(255) DEFAULT NULL,
    `request_method`    varchar(20)  DEFAULT NULL,
    `content_type`      varchar(255) DEFAULT NULL,
    `template_code`     varchar(255) DEFAULT NULL,
    `req_reg_rule`      varchar(255) DEFAULT NULL,
    `req_json_path`     varchar(255) DEFAULT NULL,
    `req_match_rule`    varchar(255) DEFAULT NULL,
    `status_code`       varchar(20)  DEFAULT NULL,
    `response_template` text,
    `created`           datetime     DEFAULT NULL,
    `updated`           datetime     DEFAULT NULL,
    `remark`            text,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
