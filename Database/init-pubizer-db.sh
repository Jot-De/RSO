set -e
echo xd
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
	CREATE USER pubizr_user WITH ENCRYPTED PASSWORD 'Bla123_user';
	CREATE DATABASE pubizr;
EOSQL

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "pubizr" <<-EOSQL
	CREATE SCHEMA public;
	GRANT ALL PRIVILEGES ON DATABASE pubizr TO pubizr_user;
EOSQL


