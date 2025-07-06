<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Vehicle DB Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">   <%-- Bootstrap is used to make page look good via Containers/ Form control and other things--%>.    
    <style>
body {
    background-color: #1E1F22;
    color: #000;
}

.card {
    background-color: #1A1A1A;
    border: none;
    box-shadow: 0 4px 12px rgba(0,0,0,0.5);
    color: #000; 
}

.form-control {
    background-color: #3B3B3B;
    border: 1px solid #555;
    color: #fff;
}

.form-control::placeholder { 
    color: #bbb;
} <%-- Placeholder text in input boxes --%>

.form-control:focus {
    background-color: #3B3B3B;
    color: #fff;
    border-color: #777;
    box-shadow: none;
}

.btn-primary {
/*     background-color: #2D2D30;
 */    border-color: #2D2D30;
}

.btn-success {
     background-color: #2E7D32;
     border-color: #2E7D32;
}


.btn, .form-control {
    border-radius: 0.5rem;
}

h1, h2, h3, h4, h5, h6 {
    color: #fff;
}

    </style>
	<script>
        function validateAddVehicleForm() 
        {
            const numberInput = document.querySelector('form[action="addVehicle.jsp"] input[name="number"]');
            const typeInput = document.querySelector('form[action="addVehicle.jsp"] input[name="type"]');
            const costInput = document.querySelector('form[action="addVehicle.jsp"] input[name="cost"]');
            
            // Validate vehicle number (7 digits)
            if (numberInput.value.length > 7) 
            {
                alert("Vehicle number cannot exceed 7 digits!");
                numberInput.focus();
                return false;
            }
            
            if (costInput .value.length > 7)
            {
                alert("Vehicle number cannot exceed 7 digits!");
                costInput.focus();
                return false;	
            }
            
            // Validate vehicle type (15 characters)
            if (typeInput.value.length > 15) 
            {
                alert("Vehicle type cannot exceed 15 characters!");
                typeInput.focus();
                return false;
            }
            
            return true;
        }
        
        function validateDeleteVehicleForm() {
            const numberInput = document.querySelector('form[action="deleteVehicle.jsp"] input[name="number"]');
            
            // Validate vehicle number (7 digits)
            if (numberInput.value.length > 7) {
                alert("Vehicle number cannot exceed 7 digits!");
                numberInput.focus();
                return false;
            }
            
            return true;
        }
        

</script>
</head>
<body class="bg-dark">
<div class="container mt-5">
    <div class="card shadow p-4">
        <h2 class="text-center mb-4">Vehicle DB API Dashboard</h2>

        <form action="viewVehicles.jsp" method="get" class="mb-4">
            <h5>Authentication</h5>
            <div class="row g-2 mb-3">
                <div class="col">
                    <input type="text" name="username" placeholder="Username" class="form-control" value="myuser" required />
                </div>
                <div class="col">
                    <input type="password" name="password" placeholder="Password" class="form-control" value="mypassword" required />
                </div>
            </div>
            <button class="btn btn-primary w-100" type="submit">View All Vehicles</button>
        </form>

		<!-- Add Vehicle Form with validation -->
        <form action="addVehicle.jsp" method="post" class="mb-4" onsubmit="return validateAddVehicleForm()">
            <h5>Add Vehicle</h5>
            <input type="hidden" name="username" value="myuser" />
            <input type="hidden" name="password" value="mypassword" />
            <input type="number" name="number" placeholder="Number (max 7 digits)" class="form-control mb-2" 
                   required min="0" max="9999999" />
            <input type="text" name="type" placeholder="Type (max 15 chars)" class="form-control mb-2" 
                   required maxlength="15" />
            <input type="number" name="cost" placeholder="Cost (max 7 digits)" class="form-control mb-2" 
            required min="0" max="9999999"  />
            <button class="btn btn-success w-100" type="submit">Add Vehicle</button>
        </form>
        

        <!-- Delete Vehicle Form with validation -->
        <form action="deleteVehicle.jsp" method="delete" onsubmit="return validateDeleteVehicleForm()">
            <h5>Delete Vehicle</h5>
            <input type="hidden" name="username" value="myuser" />
            <input type="hidden" name="password" value="mypassword" />
            <input type="number" name="number" placeholder="Vehicle Number (max 7 digits)" class="form-control mb-2" 
                   required min="0" max="9999999" />
            <button class="btn btn-danger w-100" type="submit">Delete Vehicle</button>
        </form>
    </div>
</div>
</body>
</html>
