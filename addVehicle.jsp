<%@ page import="java.io.*, java.net.*, java.util.Base64" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String user = request.getParameter("username");
    String pass = request.getParameter("password");
    String number = request.getParameter("number");
    String type = request.getParameter("type");
    String cost = request.getParameter("cost");

    String json = String.format("{\"number\":%s,\"type\":\"%s\",\"cost\":%s}", number, type, cost);
    String auth = Base64.getEncoder().encodeToString((user + ":" + pass).getBytes("UTF-8"));

    URL url = new URL("http://localhost:8080/VehicleDBv3/vehicles/");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setDoOutput(true);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Authorization", "Basic " + auth);
    conn.setRequestProperty("Content-Type", "application/json");

    OutputStream os = conn.getOutputStream();
    os.write(json.getBytes("UTF-8"));
    os.flush();
    os.close();

    BufferedReader in;
    int statusCode = conn.getResponseCode();

    if (statusCode >= 200 && statusCode < 300) {
        in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
    } else {
        in = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
    }

    String inputLine;
    StringBuilder result = new StringBuilder();
    while ((inputLine = in.readLine()) != null) result.append(inputLine);
    in.close();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Vehicles List</title>
    <style>
        body {
            background-color: #1E1F22;
            color: #fff;
            font-family: sans-serif;
            padding: 3rem;
        }

        h2 {
            color: #fff;
            font-size: 2.5rem;
            margin-bottom: 2rem;
        }


        form {
            margin-top: 3rem;
        }

        button {
            background-color: #2D2D30;
            color: white;
            padding: 1.2rem 2.5rem;
            font-size: 1.5rem;
            border: none;
            border-radius: 1rem;
            cursor: pointer;
        }

        button:hover {
            background-color: #444;
        }
    </style>
</head>
<body>
    <h2>Vehicle <%= json %> Added</h2>

    <form action="dashboard.jsp">
        <button type="submit"> Back to Dashboard</button>
    </form>
</body>
</html>
