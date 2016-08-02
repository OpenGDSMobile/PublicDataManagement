CREATE DATABASE opengdsmobiledata;

CREATE EXTENSION postgis;


CREATE TABLE "OPENDATACOLLECT"
(
  "PROVIDER" character varying(50),
  "URL" character varying(100),
  "EP" character varying(200),
  "TIME" integer,
  "STATUS" boolean,
  "COMMENT" character varying(500),
  "KEYS" character varying(100),
  "NAME" character varying(50) NOT NULL,
  CONSTRAINT "NAME" PRIMARY KEY ("NAME")
);