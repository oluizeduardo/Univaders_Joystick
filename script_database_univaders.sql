
CREATE DATABASE "Univaders";


CREATE SEQUENCE seq_winner_id INCREMENT 1;


CREATE TABLE winners
(
  cod integer NOT NULL DEFAULT nextval('seq_winner_id'),
  score integer NOT NULL,
  name character varying(30) NOT NULL,
-- identification field can be the school's name, course name, the city, etc.
  identification character varying(50),
  date date,
  CONSTRAINT pk_winners PRIMARY KEY (cod)
);


