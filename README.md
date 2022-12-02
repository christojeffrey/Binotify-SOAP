# description

a soap webservice created using jax-ws. this service is used to manage subscription to premium artists.

# database schema

it's inisde data folder

```
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
```

## we use postman to test the api. we put the collection in the repository

## work distribution

| task                                       | NIM      |
| ------------------------------------------ | -------- |
| setup structure                            | 13520045 |
| endpoint update subscription request       | 13520055 |
| endpoint check subscription request status | 13520055 |
| endpoint new subscription                  | 13520055 |
| implement logging                          | 13520156 |
| implement API key                          | 13520156 |
| endpoint get list request subscription     | 13520045 |
| checkup                                    | 13520055 |

# setup repo and running

go to Binotify-Config repository. the that repo will setup every other repo.
