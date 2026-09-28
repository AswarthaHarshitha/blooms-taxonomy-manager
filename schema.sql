CREATE TABLE IF NOT EXISTS users (
    uname TEXT PRIMARY KEY,
    pwd   TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS blooms_level (
    ID                INTEGER PRIMARY KEY AUTOINCREMENT,
    bloom_code        TEXT,
    bloom_level       TEXT,
    bloom_description TEXT
);
