# TinyInstagram — Spring Boot + PostgreSQL + Alpine.js

This repo is configured for PostgreSQL.

## Init DB
```sql
CREATE DATABASE tinyinsta;
CREATE USER tinyinsta WITH PASSWORD 'secret';
GRANT ALL PRIVILEGES ON DATABASE tinyinsta TO tinyinsta;
-- Optionally:
-- GRANT ALL ON SCHEMA public TO tinyinsta;
-- ALTER DATABASE tinyinsta OWNER TO tinyinsta;
```
