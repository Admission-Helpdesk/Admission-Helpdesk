<?php
session_start();

$host = "localhost";
$db = "helpdesk_db";
$user = "root";
$pass = "";

$correctCollegeCode = "lbscek@123";

$conn = new mysqli($host, $user, $pass, $db);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $username = trim($_POST["username"]);
    $password = $_POST["password"];
    $user_type = $_POST["user_type"] ?? 'student';

    // College code check
    if ($user_type === "college") {
        $college_code = $_POST["college_code"] ?? '';
        if ($college_code !== $correctCollegeCode) {
            $error = "Invalid college code.";
            showError($error);
        }
    }

    // Fetch user
    $stmt = $conn->prepare("SELECT id, username, password FROM users WHERE username = ? OR email = ?");
    $stmt->bind_param("ss", $username, $username);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows === 1) {
        $row = $result->fetch_assoc();
        if (password_verify($password, $row['password'])) {
            $_SESSION["user_id"] = $row['id'];
            $_SESSION["username"] = $row['username'];
            $_SESSION["user_type"] = $user_type;

            // Redirect to specific pages
            if ($user_type === 'student') {
                header("Location: student.html");
            } else {
                header("Location: college.html");
            }
            exit;
        } else {
            $error = "Invalid password.";
        }
    } else {
        $error = "User not found.";
    }

    $stmt->close();
    $conn->close();

    showError($error);
}

function showError($message) {
    echo "<!DOCTYPE html>
    <html><head><title>Login Error</title></head><body>
    <h2>Login Failed</h2>
    <p>$message</p>
    <p><a href='login.html'>Back to Login</a></p>
    </body></html>";
    exit;
}
?>
