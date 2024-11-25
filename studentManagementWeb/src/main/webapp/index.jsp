

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>
    <script>
        // Redirect to another page after a delay
        setTimeout(function() {
            window.location.href = 'dashboard';
        }, 2000); // 2000 milliseconds (2 seconds) delay
    </script>
</head>
<body>
    <h1>Welcome</h1>
    <span>Please wait while we redirect you to the app! </span>
</body>
</html>

