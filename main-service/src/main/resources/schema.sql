drop schema public cascade ;
create schema public;
CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name  VARCHAR(252)                            NOT NULL,
    email VARCHAR(320)                            NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT uq_user_email UNIQUE (email)
    );

CREATE TABLE IF NOT EXISTS category
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name  VARCHAR(252)                            NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id),
    CONSTRAINT uq_category_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS event
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY    NOT NULL,
    annotation VARCHAR(512)                       NOT NULL,
    category_id BIGINT                            NOT NULL,
    confirmedRequests BIGINT DEFAULT (0),
    create_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    description VARCHAR(512)                      NOT NULL,
    event_date TIMESTAMP WITHOUT TIME ZONE       NOT NULL,
    initiator_id BIGINT                           NOT NULL,
    location_lat double precision                 NOT NULL,
    location_lon double precision                 NOT NULL,
    paid  BOOLEAN                                 NOT NULL,
    participant_limit BIGINT                      NOT NULL,
    published_date TIMESTAMP WITHOUT TIME ZONE   NOT NULL,
    request_moderation BOOLEAN                 DEFAULT true,
    state VARCHAR(512)                            NOT NULL,
    title VARCHAR(512)                            NOT NULL,
    views BIGINT DEFAULT (0),
    CONSTRAINT pk_event PRIMARY KEY (id),
    CONSTRAINT fk_initiator FOREIGN KEY (initiator_id) REFERENCES users ON DELETE CASCADE,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category ON DELETE CASCADE
    );


CREATE TABLE IF NOT EXISTS compilation
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    pinned  BOOLEAN                                 NOT NULL,
    title  VARCHAR(512)                             NOT NULL,
    CONSTRAINT pk_compilation PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS compilation_event
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    compilation_id BIGINT NOT NULL,
    event_id BIGINT NOT NULL,
    CONSTRAINT pk_compilation_event PRIMARY KEY (id),
    CONSTRAINT fk_compilation FOREIGN KEY ( compilation_id) REFERENCES  compilation ON DELETE CASCADE,
    CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES event ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS request
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    create_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    event_id BIGINT                               NOT NULL,
    requester_id BIGINT                           NOT NULL,
    status VARCHAR(512)                            NOT NULL,
    CONSTRAINT pk_request PRIMARY KEY (id),
    CONSTRAINT fk_requester FOREIGN KEY (requester_id) REFERENCES users ON DELETE CASCADE,
    CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES event ON DELETE CASCADE,
    CONSTRAINT uq_reuest UNIQUE (event_id, requester_id)
);






