create table if not exists t_url_mapping
(
    id           int           NOT NULL AUTO_INCREMENT,
    short_url   char(6)       NOT NULL,
    origin_url   varchar(1024) NOT NULL,
    descrp       varchar(1024),
    created_time timestamp     NOT NULL,
    expired_time timestamp     NOT NULL,

    PRIMARY KEY id (id),
    UNIQUE KEY short_url (short_url),
    KEY expired_time (expired_time)
    )AUTO_INCREMENT=1;