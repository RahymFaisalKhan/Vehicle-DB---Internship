# VehicleDB - Internship
Project made for an software engineering Internship at Bank of Punjab. It is a simple vehicle database management system with a REST API and a web-based frontend (made to look nice via css).

# Features

Authentication – Secure login for API access <br>
CRD operations - Add, Delete, View Vehicle options <br>
API & Web Frontend – Complete stack built with JSP and Java servlet <br>
Postman Tested – All endpoints verified with Postman collections <br>

# Project Structure

├── Frontend <br>
├───── dashboard.jsp           # Web dashboard with authentication and navigation <br>
├───── addVehicle.jsp          # JSP page to add new vehicles <br>
├───── deleteVehicle.jsp       # JSP page to delete vehicles <br>
├───── viewVehicles.jsp        # JSP page to list vehicles <br>
├── Vehicle_DB_API.java     # Core Java API logic (servlets for vehicle operations) <br>
├── DB_interactive_console.class # used as a .jar import in API implementation

# Prerequisites

Java 8+ <br>
Tomcat or any compatible servlet container <br>
MySQL/PostgreSQL database (or any JDBC-compatible DB) <br>

# API Endpoints

GET /vehicles <br>
POST /vehicles <br>
DELETE /vehicles/:number <br>
All endpoints require basic authentication. <br>

# Screenshots

![image](https://github.com/user-attachments/assets/80ad38bd-f05e-4a61-a626-d0aff152d8a9)
![image](https://github.com/user-attachments/assets/72c6787e-91a2-4422-8655-80134608b21d)

