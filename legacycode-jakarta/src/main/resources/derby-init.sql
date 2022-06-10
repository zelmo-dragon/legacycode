CREATE TABLE gender(id VARCHAR(36) NOT NULL)
ALTER TABLE gender ADD version BIGINT NOT NULL DEFAULT 0
ALTER TABLE gender ADD name VARCHAR(255) DEFAULT '<empty>'
ALTER TABLE gender ADD code VARCHAR(10) DEFAULT '<empty>'
ALTER TABLE gender ADD description VARCHAR(255)

ALTER TABLE gender ADD PRIMARY KEY(id)
ALTER TABLE gender ADD UNIQUE(name)
ALTER TABLE gender ADD UNIQUE(code)


CREATE TABLE customer(id VARCHAR(36) NOT NULL, gender_id VARCHAR(36) NOT NULL)
ALTER TABLE customer ADD version BIGINT NOT NULL  DEFAULT 0
ALTER TABLE customer ADD given_name VARCHAR(255) NOT NULL DEFAULT '<empty>'
ALTER TABLE customer ADD family_name VARCHAR(255) NOT NULL DEFAULT '<empty>'
ALTER TABLE customer ADD email VARCHAR(255) NOT NULL DEFAULT '<empty>'
ALTER TABLE customer ADD phone_number VARCHAR(255)

ALTER TABLE customer ADD PRIMARY KEY(id)
ALTER TABLE customer ADD FOREIGN KEY(gender_id) REFERENCES gender(id)
ALTER TABLE customer ADD UNIQUE(email)

INSERT INTO gender(id, version, name, code, description) VALUES ('09ee5d9d-bf9b-4b5d-aad0-19117eb8da34', 1, 'Male', 'M', 'A male humain')
INSERT INTO gender(id, version, name, code, description) VALUES ('337ac663-48da-4a97-ad55-062a2c2ebb6d', 1, 'Female', 'F', 'A female humain')