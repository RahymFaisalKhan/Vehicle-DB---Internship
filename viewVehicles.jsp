<%@ page import="java.net.*, java.io.*, org.json.*, java.util.*, java.util.Base64" %>
<%
    String user = request.getParameter("username");
    String pass = request.getParameter("password");

    String auth = Base64.getEncoder().encodeToString((user + ":" + pass).getBytes("UTF-8"));

    URL url = new URL("http://localhost:8080/VehicleDBv3/vehicles/");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestProperty("Authorization", "Basic " + auth);

    int code = conn.getResponseCode();
    StringBuilder result = new StringBuilder();

    if (code == 200) 
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) result.append(line);
        in.close();
    } 
    else 
    {
        result.append("{ \"error\": \"Failed with HTTP code " + code + "\" }");
    }
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

        pre {
            background-color: #1A1A1A;
            color: #00FF00;
            padding: 3rem;
            font-size: 0.7rem;
            border-radius: 10px;
            max-height: 300px;
            overflow-y: auto;
            white-space: pre-wrap;
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
        
		.table-container {
		    max-height: 400px;
		    overflow-y: auto;
		    margin-top: 2rem;
		    border: 1px solid #444;
		    border-radius: 0.5rem;
		}
		
		table {
		    width: 100%;
		    border-collapse: collapse;
		    color: #fff;
		    font-size: 0.9rem;
		}
		
		th, td {
		    padding: 0.75rem;
		    text-align: left;
		    border-bottom: 1px solid #333;
		}
		
		th {
		    background-color: #2D2D30;
		    position: sticky;
		    top: 0;
		}
		
		tr:hover {
		    background-color: #333;
		}

    </style>
</head>
<body>
    <h2>Vehicles List</h2>
    
    
<div class="table-container"> 
    <table>
        <thead>
            <tr>
                <th>Number</th>
                <th>Type</th>
                <th>Cost</th>
            </tr>
        </thead>
        <tbody>
<%
    String json = result.toString().trim();

    try {
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            JSONObject vehicle = array.getJSONObject(i);

            int number = vehicle.getInt("number");
            String type = vehicle.getString("type");
            int cost = vehicle.getInt("cost");
%>
            <tr>
                <td><%= number %></td>
                <td><%= type %></td>
                <td><%= cost %></td>
            </tr>
<%
        }
    } catch (JSONException e) {
%>
        <tr><td colspan="3">Invalid JSON: <%= e.getMessage() %></td></tr>
<%
    }
%>

        </tbody>
    </table>
</div>

    <form action="dashboard.jsp">
        <button type="submit"> Back to Dashboard</button>
    </form>
</body>
</html>
