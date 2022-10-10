CREATE TABLE IF NOT EXISTS user_friends
(
    subscription_id BIGINT NOT NULL REFERENCES person,
    subscriber_id   BIGINT NOT NULL REFERENCES person,
    primary key (subscription_id, subscriber_id)

);
