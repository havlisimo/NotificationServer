create table test (
  test_id        BIGSERIAL PRIMARY KEY                                   NOT NULL,
  test_attribute TEXT                                                    NOT NULL
);

INSERT INTO test ( test_attribute)
VALUES ('AHOJ'),
  ('BLE'),
  ('AHOJ'),
  ('fuj'),
  ('test'),
  ('test'),
  ('test');
