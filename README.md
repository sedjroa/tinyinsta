# TinyInstagram — Spring Boot + PostgreSQL + Alpine.js

This repo is configured for PostgreSQL.

# on codespace

If error with java version...
`sdk use java 17.0.15-ms`

`mvn package`

`docker compose up -d`

## Init DB
```sql
CREATE DATABASE tinyinsta;
CREATE USER tinyinsta WITH PASSWORD 'secret';
GRANT ALL PRIVILEGES ON DATABASE tinyinsta TO tinyinsta;
-- Optionally:
-- GRANT ALL ON SCHEMA public TO tinyinsta;
-- ALTER DATABASE tinyinsta OWNER TO tinyinsta;
```
