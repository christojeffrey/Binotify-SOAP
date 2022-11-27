CREATE TABLE `Subscription` (
  `creator_id` int NOT NULL,
  `subscriber_id` int NOT NULL,
  `status` enum('PENDING', 'ACCEPTED', 'REJECTED') default 'PENDING' NOT NULL,
  PRIMARY KEY(`creator_id`, `subscriber_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Logging` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(256) NOT NULL,
  `IP` varchar(16) NOT NULL,
  `endpoint` varchar(256) NOT NULL,
  `requested_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO Subscription (creator_id, subscriber_id, status) VALUES (1, 2, 'PENDING');
INSERT INTO Subscription (creator_id, subscriber_id, status) VALUES (2, 2, 'ACCEPTED');
INSERT INTO Logging (description, IP, endpoint) VALUES ('test', '192.0.0.61', 'api/hello');

