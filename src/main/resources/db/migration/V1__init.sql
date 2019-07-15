CREATE TABLE `building`
(
    `building_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `finished_at` datetime     DEFAULT NULL,
    `hp`          int(11)    NOT NULL,
    `level`       int(11)    NOT NULL,
    `started_at`  datetime     DEFAULT NULL,
    `type`        varchar(255) DEFAULT NULL,
    `kingdom_id`  bigint(20)   DEFAULT NULL,
    PRIMARY KEY (`building_id`),
    KEY `FK6s58l4oxb2hntomgyhe5byvpa` (`kingdom_id`)
);

CREATE TABLE `kingdom`
(
    `kingdom_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `locationx`  int(11)    NOT NULL,
    `locationy`  int(11)    NOT NULL,
    `name`       varchar(255) DEFAULT NULL,
    PRIMARY KEY (`kingdom_id`)
);

CREATE TABLE `supply`
(
    `supply_id`  bigint(20) NOT NULL AUTO_INCREMENT,
    `amount`     int(11)    NOT NULL,
    `generation` int(11)    NOT NULL,
    `type`       varchar(255) DEFAULT NULL,
    `update_at`  datetime     DEFAULT NULL,
    `kingdom_id` bigint(20)   DEFAULT NULL,
    PRIMARY KEY (`supply_id`),
    KEY `FK1wbdtrdmxd5pssev2tlay2jqv` (`kingdom_id`)
);

CREATE TABLE `troop`
(
    `troop_id`    bigint(20) NOT NULL AUTO_INCREMENT,
    `attack`      int(11)    NOT NULL,
    `defense`     int(11)    NOT NULL,
    `finished_at` datetime   DEFAULT NULL,
    `hp`          int(11)    NOT NULL,
    `level`       int(11)    NOT NULL,
    `started_at`  datetime   DEFAULT NULL,
    `kingdom_id`  bigint(20) DEFAULT NULL,
    PRIMARY KEY (`troop_id`),
    KEY `FK9c9dtfl3ehguq5d38u40c4c5w` (`kingdom_id`)
);

CREATE TABLE `user`
(
    `user_id`    bigint(20)   NOT NULL AUTO_INCREMENT,
    `password`   varchar(255) NOT NULL,
    `username`   varchar(255) NOT NULL,
    `kingdom_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`user_id`),
    KEY `FKpmg5qf7i5a9x16ks0qgj4w9tn` (`kingdom_id`)
);

CREATE TABLE `user_roles`
(
    `user_user_id` bigint(20) NOT NULL,
    `roles`        int(11) DEFAULT NULL,
    KEY `FKkv46dn3qakjvsk7ra33nd5sns` (`user_user_id`)
);