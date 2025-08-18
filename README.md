# Bloom's Taxonomy Level Management System

![Java](https://img.shields.io/badge/Java-17-blue)
![Swing](https://img.shields.io/badge/Java_Swing-GUI-orange)
![SQLite](https://img.shields.io/badge/SQLite-Database-green)

A comprehensive Java Swing application for managing Bloom's Taxonomy levels with secure user authentication, developed as part of SRM University-AP's Outcome-Based Education (OBE) implementation.

## Features

- 🔒 Secure user authentication system
- 📝 Complete CRUD operations for Bloom's Taxonomy levels:
  - Create: Add new Bloom's levels
  - Read: View existing levels in tabular format
  - Update: Modify existing entries
  - Delete: Remove unwanted records
- 🔍 Search functionality for quick access
- 🎨 User-friendly GUI with modern styling
- 🗃️ SQLite database integration for persistent storage
- 📊 Table view with zebra striping and custom styling

## Database Schema

The application uses two main tables:

```sql
-- User table for authentication
CREATE TABLE users (
    uname TEXT PRIMARY KEY,
    pwd TEXT NOT NULL
);

-- Bloom's level management table
CREATE TABLE blooms_level (
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    bloom_code TEXT,
    bloom_level TEXT,
    bloom_description TEXT
);

**Prerequisites**
Java JDK 17 or later

SQLite JDBC driver (sqlite-jdbc-<version>.jar)

SQLite database file (javaapp.db)

Installation & Usage
1.Clone the repository:
git clone https://github.com/yourusername/blooms-level-manager.git
cd blooms-level-manager
2.Set up the database:

Create a SQLite database file named javaapp.db
Execute the schema provided above

3.Add SQLite JDBC driver:
Download the latest SQLite JDBC driver
Place the JAR file in the project's lib directory

4.Compile and run:
javac -cp .;lib/sqlite-jdbc-<version>.jar BLOOMSLEVEL.java
java -cp .;lib/sqlite-jdbc-<version>.jar bloomslevel.BLOOMSLEVEL

**Project Structure**
blooms-level-manager/
│
├── src/
│   └── bloomslevel/
│       └── BLOOMSLEVEL.java       # Main application file
│
├── lib/
│   └── sqlite-jdbc-<version>.jar  # SQLite JDBC driver
│
├── docs/
│   ├── java.pdf                   # Project documentation
│   └── javaa.pptx                 # Project presentation
│
├── database/
│   └── javaapp.db                 # SQLite database file
│
└── README.md                      # This file




