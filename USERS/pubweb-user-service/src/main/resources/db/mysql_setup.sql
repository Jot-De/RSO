CREATE SCHEMA pubizr;

CREATE ROLE IF NOT EXISTS 'writer_role', 'reader_role', 'ddl_role';
GRANT SELECT ON pubizr.* TO 'reader_role';
GRANT INSERT, UPDATE, DELETE ON pubizr.* TO 'writer_role';
GRANT CREATE, ALTER, DROP ON pubizr.* TO 'ddl_role';

CREATE USER 'pubizr_user'@'%' IDENTIFIED BY 'Bla123_user';

GRANT 'reader_role' TO 'pubizr_user';
GRANT 'writer_role' TO 'pubizr_user';
GRANT 'ddl_role' TO 'pubizr_user';

SET DEFAULT ROLE 'reader_role', 'writer_role', 'ddl_role' TO 'pubizr_user';

FLUSH PRIVILEGES;