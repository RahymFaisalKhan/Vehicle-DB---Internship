![image](https://github.com/user-attachments/assets/ba9136a1-a249-46e3-a8f6-d30b6aceae5c)# Vehicle-DB---Internship
Project made for an software engineering Internship at Bank of Punjab

Vehicle DB API Dashboard

This project is a simple vehicle database management system with a REST API and a web-based frontend. It was developed during my internship to demonstrate API construction, frontend integration, and basic CRUD operations.

Features

Authentication – Secure login for API access
CRD operations - Add, Delete, View Vehicle options
API & Web Frontend – Complete stack built with JSP and Java servlet
Postman Tested – All endpoints verified with Postman collections

🗂️ Project Structure

├── addVehicle.jsp          # JSP page to add new vehicles
├── deleteVehicle.jsp       # JSP page to delete vehicles
├── viewVehicles.jsp        # JSP page to list vehicles
├── dashboard.jsp           # Web dashboard with authentication and navigation
├── Vehicle_DB_API.java     # Core Java API logic (servlets for vehicle operations)
├── DB_interactive_console.class # used as a .jar import in API implementation

Prerequisites

Java 8+
Tomcat or any compatible servlet container
MySQL/PostgreSQL database (or any JDBC-compatible DB)

API Endpoints

GET /vehicles
POST /vehicles
DELETE /vehicles/:number
All endpoints require basic authentication.

Screenshots

![image](https://github.com/user-attachments/assets/80ad38bd-f05e-4a61-a626-d0aff152d8a9)
