---------------------------------SEQUENCES---------------------------------
create sequence "topics_id_seq";
create sequence "posts_id_seq";
----------------------------------TABLES----------------------------------
create table TOPICS
(
    ID            int                      not null default NEXTVAL('topics_id_seq') primary key,
    NAME          VARCHAR(100)             not null,
    DESCRIPTION   VARCHAR(300)             not null,
    CREATION_DATE TIMESTAMP WITH TIME ZONE not null default CURRENT_TIMESTAMP,
    UPDATE_DATE   TIMESTAMP WITH TIME ZONE not null default CURRENT_TIMESTAMP
);

CREATE TABLE POSTS
(
    ID            INT                               DEFAULT NEXTVAL('posts_id_seq') PRIMARY KEY NOT NULL,
    NAME          VARCHAR(100)             NOT NULL UNIQUE,
    CONTENT       TEXT                     NOT NULL,
    STATUS        VARCHAR(100)             NOT NULL,
    TOPIC_ID      INT                      NOT NULL,
    CREATION_DATE TIMESTAMP WITH TIME ZONE not null default CURRENT_TIMESTAMP,
    UPDATE_DATE TIMESTAMP WITH TIME ZONE not null default CURRENT_TIMESTAMP,

    FOREIGN KEY (TOPIC_ID) REFERENCES TOPICS (ID) on delete no action
);
