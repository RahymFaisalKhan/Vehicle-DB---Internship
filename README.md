# Vehicle-DB---Internship
Project made for an software engineering Internship at Bank of Punjab. It is a simple vehicle database management system with a REST API and a web-based frontend (made to look nice via css).

# Features

Authentication – Secure login for API access
CRD operations - Add, Delete, View Vehicle options
API & Web Frontend – Complete stack built with JSP and Java servlet
Postman Tested – All endpoints verified with Postman collections

# Project Structure

├── Frontend
├───── dashboard.jsp           # Web dashboard with authentication and navigation
├───── addVehicle.jsp          # JSP page to add new vehicles
├───── deleteVehicle.jsp       # JSP page to delete vehicles
├───── viewVehicles.jsp        # JSP page to list vehicles
├── Vehicle_DB_API.java     # Core Java API logic (servlets for vehicle operations)
├── DB_interactive_console.class # used as a .jar import in API implementation

# Prerequisites

Java 8+
Tomcat or any compatible servlet container
MySQL/PostgreSQL database (or any JDBC-compatible DB)

# API Endpoints

GET /vehicles
POST /vehicles
DELETE /vehicles/:number
All endpoints require basic authentication.

# Screenshots

![image](https://github.com/user-attachments/assets/80ad38bd-f05e-4a61-a626-d0aff152d8a9)
![image](https://github.com/user-attachments/assets/72c6787e-91a2-4422-8655-80134608b21d)

