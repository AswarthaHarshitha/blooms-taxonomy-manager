# Bloom's Taxonomy Level Manager

A Java Swing desktop application for maintaining the Bloom's Taxonomy levels used in Outcome-Based Education (OBE) at SRM University AP. Faculty sign in and manage a catalogue of Bloom's levels — code, level name and description — stored in SQLite, with create, update, delete and search operations from a single form-and-table screen.

## Features

- Login screen backed by a `users` table (parameterised queries)
- Add, update, delete and search Bloom's levels by code
- Table view of all stored levels
- SQLite persistence through JDBC

## Tech Stack

Java 17 · Swing · SQLite · sqlite-jdbc (`lib/sqlite-jdbc-3.49.1.0.jar`)

## Project Structure

```
BLOOMSLEVEL.java   entry point, LoginFrame and BloomLevelFrame (package bloomslevel)
schema.sql         database schema
lib/               SQLite JDBC driver
docs/              project report (PDF) and presentation (PPTX)
```

## Running

```bash
# 1. Create the database and a login
sqlite3 javaapp.db < schema.sql
sqlite3 javaapp.db "INSERT INTO users VALUES ('<username>', '<password>');"

# 2. Compile and run (use ; instead of : as the classpath separator on Windows)
javac -cp lib/sqlite-jdbc-3.49.1.0.jar -d out BLOOMSLEVEL.java
java -cp out:lib/sqlite-jdbc-3.49.1.0.jar bloomslevel.BLOOMSLEVEL
```

The app opens `javaapp.db` in the working directory; pass `-Ddb.path=/path/to/file.db` to use another location. Passwords are stored as plain text, so use this as a local academic tool only.
