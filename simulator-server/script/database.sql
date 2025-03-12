CREATE DATABASE `channel_mock` /*!40100 DEFAULT CHARACTER SET latin1 */;

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

