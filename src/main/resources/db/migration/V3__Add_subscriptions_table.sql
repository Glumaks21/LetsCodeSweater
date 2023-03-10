CREATE TABLE user_subscriptions(
    channel_id bigint not null ,
    subscriber_id bigint not null ,
    PRIMARY KEY (channel_id, subscriber_id),
    CONSTRAINT chanel_id_constraint FOREIGN KEY (channel_id) REFERENCES my_user(id),
    CONSTRAINT subscriber_id_constraint FOREIGN KEY (subscriber_id) REFERENCES my_user(id)
)