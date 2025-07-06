import java.io.*;
import java.sql.*;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import vehicle_db.DB_interactive_console;

@WebServlet("/vehicles/*") 
@MultipartConfig // allows us to handle form requests
public class Vehicle_DB_API extends HttpServlet 
{
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Vehicles";
    private static final String USERNAME = "rahyms_acc";
    private static final String PASSWORD = "rahym@2006";

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
//            throws IOException 
//    {
//        response.setContentType("application/json");
//        PrintWriter out = response.getWriter();
//
//        try (Connection conn = getConnection()) 
//        {
//            if (!checkBasicAuth(request)) 
//            {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                String errorJson = getErrorJson("Unauthorized");
//                out.println(errorJson);
//                writeToDesktop("unauthorized.json", errorJson);
//                return;
//            }
//
//            String pathInfo = request.getPathInfo();
//            String outputJson;
//
//            if (pathInfo == null || pathInfo.equals("/")) 
//            {
//                try 
//                {
//                    outputJson = getAllVehiclesAsJson(conn);
//                    out.println(outputJson);
//                    writeToDesktop("all_vehicles.json", outputJson);
//                } 
//                catch (Exception e) 
//                {
//                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//                    outputJson = getErrorJson("Error fetching all vehicles");
//                    out.println(outputJson);
//                    writeToDesktop("error_all_vehicles.json", outputJson);
//                    e.printStackTrace();
//                }
//            } 
//            else 
//            {
//                String[] parts = pathInfo.split("/");
//                if (parts.length == 2) 
//                {
//                    String numberStr = parts[1].trim();
//                    if (numberStr.equals("*")) 
//                    {
//                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                        outputJson = getErrorJson("Wildcard '*' is not allowed in GET by ID.");
//                        out.println(outputJson);
//                        writeToDesktop("error_wildcard.json", outputJson);
//                        return;
//                    }
//
//                    try 
//                    {
//                        int vehicleNumber = Integer.parseInt(numberStr);
//                        String vehicleJson = getVehicleAsJson(conn, vehicleNumber);
//                        if (vehicleJson != null) 
//                        {
//                            out.println(vehicleJson);
//                            writeToDesktop("vehicle_" + vehicleNumber + ".json", vehicleJson);
//                        } 
//                        else 
//                        {
//                            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                            outputJson = getErrorJson("Vehicle not found");
//                            out.println(outputJson);
//                            writeToDesktop("error_vehicle_not_found.json", outputJson);
//                        }
//                    } 
//                    catch (NumberFormatException | SQLException e) 
//                    {
//                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                        outputJson = getErrorJson("Invalid vehicle number: " + numberStr);
//                        out.println(outputJson);
//                        writeToDesktop("error_invalid_number.json", outputJson);
//                        e.printStackTrace();
//                    }
//                }
//            }
//        } 
//        catch (ClassNotFoundException | SQLException e1) 
//        {
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            String errorJson = getErrorJson("Database connection error: " + e1.getMessage());
//            out.println(errorJson);
//            writeToDesktop("error_connection.json", errorJson);
//            e1.printStackTrace();
//        }
//    }

    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException 
    {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        try (Connection conn = getConnection()) 
        {
            if (!checkBasicAuth(request)) 
            {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setHeader("WWW-Authenticate", "Basic realm=\"Vehicles API\"");
                response.getWriter().println(getErrorJson("Unauthorized"));
                return;
            }

            String pathInfo = request.getPathInfo();

            if (pathInfo == null || pathInfo.equals("/")) 
            {
                try 
                {
                    out.println(getAllVehiclesAsJson(conn));
                } 
                catch (Exception e) 
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } 
            else 
            {
                String[] parts = pathInfo.split("/");
                if (parts.length == 2) 
                {
                    String numberStr = parts[1].trim();
                    if (numberStr.equals("*")) 
                    {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.println(getErrorJson("Wildcard '*' is not allowed in GET by ID."));
                        return;
                    }
                    try 
                    {
                        int vehicleNumber = Integer.parseInt(numberStr);
                        String vehicleJson = getVehicleAsJson(conn, vehicleNumber);
                        if (vehicleJson != null) 
                        {
                            out.println(vehicleJson);
                        } 
                        else 
                        {
                            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                            out.println(getErrorJson("Vehicle not found"));
                        }
                    } 
                    catch (NumberFormatException | SQLException e)
                    {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.println(getErrorJson("Invalid vehicle number: " + numberStr));
                    }
                }
            }
        } 
        catch (ClassNotFoundException | SQLException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
//            throws IOException 
//    {
//        String pathInfo = request.getPathInfo();
//        if (pathInfo == null || pathInfo.equals("/")) {
//            response.setContentType("application/json");
//            PrintWriter out = response.getWriter();
//
//            try (Connection conn = getConnection()) {
//                StringBuilder sb = new StringBuilder();
//                try (BufferedReader reader = request.getReader()) {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        sb.append(line);
//                    }
//                }
//
//                String json = sb.toString();
//                int number = extractIntValue(json, "number");
//                String type = extractStringValue(json, "type");
//                int cost = extractIntValue(json, "cost");
//
//                DB_interactive_console.insert_entry(conn, number, type, cost);
//
//                response.setStatus(HttpServletResponse.SC_CREATED);
//                out.println(getSuccessJson("Vehicle created successfully"));
//            } catch (Exception e) {
//                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//                out.println(getErrorJson(e.getMessage()));
//                e.printStackTrace();
//            }
//        } else {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            response.getWriter().println(getErrorJson("Invalid endpoint: " + pathInfo));
//        }
//    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException 
    {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try (Connection conn = getConnection()) 
        {
            InputStream fileContent = null;
            Workbook workbook = null;

            // Check if sourcePath parameter is provided
            String filePath = request.getParameter("sourcePath");

                // Upload via Postman/file upload
            Part filePart = request.getPart("file");

            String filename = filePart.getSubmittedFileName();
            fileContent = filePart.getInputStream();

            if (filename.endsWith(".xls")) 
            {
                workbook = new HSSFWorkbook(fileContent);
            } 

            try 
            {
                Sheet sheet = workbook.getSheetAt(0);
                for (Row row : sheet) 
                {
                    if (row.getRowNum() == 0) 
                    	continue; // Skip header

                    int number = (int) row.getCell(0).getNumericCellValue();
                    
                    if (number < 0 || number > 9999999)
                    {
                    	System.out.println("Row " + (row.getRowNum()+1) + " Skipped due to invalid Vehicle Number");
                    	continue;
                    }
                    
                    String type = row.getCell(1).getStringCellValue();
                    if (type.length() > 15 )
                    {
                    	System.out.println("Row " + row.getRowNum() + " Skipped due to invalid Vehicle Type");
                    	continue;
                    }
                    
                    int cost = (int) row.getCell(2).getNumericCellValue();
                    if (number < 0 || number > 9999999)
                    {
                    	System.out.println("Row " + row.getRowNum() + " Skipped due to invalid Vehicle Cost");
                    	continue;
                    }

                    DB_interactive_console.insert_entry(conn, number, type, cost);
                }

                out.println(getSuccessJson("Excel file processed successfully."));
            } 
            catch (Exception e) 
            {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println(getErrorJson("Error processing Excel file: " + e.getMessage()));
                e.printStackTrace();
            } 
            finally 
            {
                workbook.close();
                fileContent.close();
            }
        } 
        catch (Exception e) 
        {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println(getErrorJson("Server error: " + e.getMessage()));
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws IOException 
    {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        try (Connection conn = getConnection()) 
        {
            String pathInfo = request.getPathInfo();
            String[] parts = pathInfo.split("/");
            
            if (parts.length == 2) 
            {
                String numberStr = parts[1].trim();
                if (numberStr.equals("*")) 
                {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.println(getErrorJson("Wildcard '*' is not allowed in PUT."));
                    return;
                }
                try {
                    int vehicleNumber = Integer.parseInt(numberStr);
                    
                    StringBuilder sb = new StringBuilder();
                    try (BufferedReader reader = request.getReader()) 
                    {
                        String line;
                        while ((line = reader.readLine()) != null) 
                        {
                            sb.append(line);
                        }
                    }
                    
                    String json = sb.toString();
                    String type = extractStringValue(json, "type");
                    int cost = extractIntValue(json, "cost");
                    
                    DB_interactive_console.update_entry(conn, vehicleNumber, type, cost);
                    out.println(getSuccessJson("Vehicle updated successfully"));
                } catch (NumberFormatException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.println(getErrorJson("Invalid vehicle number: " + numberStr));
                }
            }
        } 
        catch (Exception e) 
        {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println(getErrorJson(e.getMessage()));
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws IOException 
    {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        try (Connection conn = getConnection()) 
        {
            String pathInfo = request.getPathInfo();
            String[] parts = pathInfo.split("/");
            
            if (parts.length == 2) 
            {
                String numberStr = parts[1].trim();
                if (numberStr.equals("*")) 
                {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.println(getErrorJson("Wildcard '*' is not allowed in DELETE by ID."));
                    return;
                }
                try 
                {
                    int vehicleNumber = Integer.parseInt(numberStr);
                    int[] numbers = {vehicleNumber};
                    
                    DB_interactive_console.delete_entries(conn, numbers);
                    out.println(getSuccessJson("Vehicle deleted successfully"));
                } 
                catch (NumberFormatException e) 
                {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.println(getErrorJson("Invalid vehicle number: " + numberStr));
                }
            } 
            else if (request.getParameter("numbers") != null) 
            {
                String[] numberStrings = request.getParameter("numbers").split(",");
                int[] numbers = new int[numberStrings.length];
                try {
                    for (int i = 0; i < numberStrings.length; i++) 
                    	{
                        String numStr = numberStrings[i].trim();
                        if (numStr.equals("*")) 
                        {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            out.println(getErrorJson("Wildcard '*' is not allowed in bulk delete."));
                            return;
                        }
                        numbers[i] = Integer.parseInt(numStr);
                    }
                    
                    DB_interactive_console.delete_entries(conn, numbers);
                    out.println(getSuccessJson("Vehicles deleted successfully"));
                } 
                catch (NumberFormatException e) 
                {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.println(getErrorJson("Invalid number in list: " + e.getMessage()));
                }
            }
        } 
        catch (Exception e) 
        {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println(getErrorJson(e.getMessage()));
            e.printStackTrace();
        }
    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response) 
            throws IOException 
    {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        try (Connection conn = getConnection()) 
        {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = request.getReader()) 
            {
                String line;
                while ((line = reader.readLine()) != null) 
                {
                    sb.append(line);
                }
            }
            
            String json = sb.toString();
            int from = extractIntValue(json, "from");
            int to = extractIntValue(json, "to");
            
            DB_interactive_console.random_replacement(conn, from, to);
            out.println(getSuccessJson("Random replacement completed"));
        } 
        catch (Exception e) 
        {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println(getErrorJson(e.getMessage()));
            e.printStackTrace();
        }
    }

    private String getAllVehiclesAsJson(Connection conn) throws SQLException 
    {
        StringBuilder json = new StringBuilder("[");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Vehicle_Tables");
        boolean first = true;
        
        while (rs.next()) 
        {
            if (!first) 
            {
                json.append(",");
            }
            json.append("{")
                .append("\"number\":").append(rs.getInt("Vehicle Number")).append(",")
                .append("\"type\":\"").append(escapeJson(rs.getString("Vehicle Type"))).append("\",")
                .append("\"cost\":").append(rs.getInt("Vehicle Cost"))
                .append("}");
            first = false;
        }
        json.append("]");
        return json.toString();
    }

    private String getVehicleAsJson(Connection conn, int number) throws SQLException 
    {
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM Vehicle_Tables WHERE Vehicle Number = ?");
        stmt.setInt(1, number);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) 
        {
            return "{"
                + "\"number\":" + rs.getInt("Vehicle Number") + ","
                + "\"type\":\"" + escapeJson(rs.getString("Vehicle Type")) + "\","
                + "\"cost\":" + rs.getInt("Vehicle Cost")
                + "}";
        }
        return null;
    }

    private String getSuccessJson(String message) 
    {
        return "{\"status\":\"success\",\"message\":\"" + escapeJson(message) + "\"}";
    }

    private String getErrorJson(String error) 
    {
        return "{\"status\":\"error\",\"message\":\"" + escapeJson(error) + "\"}";
    }

    private String escapeJson(String input) 
    {
        if (input == null) return "";
        return input.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException 
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    private int extractIntValue(String json, String key) 
    {
        String pattern = "\"" + key + "\":\\s*(\\d+)";
        java.util.regex.Pattern r = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = r.matcher(json);
        if (m.find()) 
        {
            return Integer.parseInt(m.group(1));
        }
        throw new IllegalArgumentException("Missing or invalid " + key);
    }

    private String extractStringValue(String json, String key) 
    {
        String pattern = "\"" + key + "\":\\s*\"([^\"]*)\"";
        java.util.regex.Pattern r = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = r.matcher(json);
        if (m.find()) 
        {
            return m.group(1);
        }
        throw new IllegalArgumentException("Missing or invalid " + key);
    }

    private boolean checkBasicAuth(HttpServletRequest request) 
    {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Basic ")) 
        {
            String base64Credentials = authHeader.substring("Basic ".length());
            byte[] decodedBytes = java.util.Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(decodedBytes);

            String[] values = credentials.split(":", 2);
            String username = values[0];
            String password = values[1];

            return username.equals("myuser") && password.equals("mypassword");
        }
        return false;
    }
    
    private void writeToDesktop(String filename, String content) {
        String userHome = System.getProperty("user.home");
        String desktopPath = userHome + "/Desktop/" + filename;

        try (FileWriter writer = new FileWriter(desktopPath)) {
            writer.write(content);
            System.out.println("JSON saved to: " + desktopPath);
        } catch (IOException e) {
            System.err.println("Failed to write to Desktop: " + e.getMessage());
            e.printStackTrace();
        }
    }


}