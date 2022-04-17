CREATE TABLE IF NOT EXISTS account (
  id SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL,
  email VARCHAR NOT NULL UNIQUE,
  phone VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS ticket (
    id SERIAL PRIMARY KEY,
    session_id INT NOT NULL,
    line INT NOT NULL,
    cell INT NOT NULL,
    account_id INT NOT NULL REFERENCES account(id)
);

ALTER TABLE ticket
ADD CONSTRAINT unique_ticket UNIQUE (session_id, line, cell);