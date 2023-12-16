#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
	CREATE DATABASE nasastealer;
	GRANT ALL PRIVILEGES ON DATABASE nasastealer TO po-user;
EOSQL