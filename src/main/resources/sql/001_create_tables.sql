CREATE TABLE rebus (
    id uuid NOT NULL PRIMARY KEY,
    nr     int     NOT NULL,
    hint   varchar NOT NULL,
    answer varchar NOT NULL
);

CREATE TABLE teams (
    id uuid NOT NULL PRIMARY KEY,
    name varchar NOT NULL,
    secret varchar NOT NULL
);

CREATE TABLE unlocks (
    id uuid NOT NULL PRIMARY KEY,
    teams_id uuid NOT NULL REFERENCES teams(id),
    rebus_id uuid NOT NULL REFERENCES rebus(id),
    type varchar NOT NULL
);
