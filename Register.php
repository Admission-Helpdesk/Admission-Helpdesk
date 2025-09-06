<?php
session_start();

// Database config
$host = "localhost";
$db = "helpdesk_db";
$user = "root";
$pass = "";

$conn = new mysqli($host, $user, $pass, $db);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$success = $error = "";

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $username = trim($_POST["username"]);
    $email = trim($_POST["email"]);
    $password = $_POST["password"];
    $confirm = $_POST["confirm_password"];

    if (empty($username) || empty($email) || empty($password) || empty($confirm)) {
        $error = "All fields are required.";
    } elseif (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        $error = "Invalid email format.";
    } elseif ($password !== $confirm) {
        $error = "Passwords do not match.";
    } else {
        $stmt = $conn->prepare("SELECT id FROM users WHERE username = ? OR email = ?");
        $stmt->bind_param("ss", $username, $email);
        $stmt->execute();
        $stmt->store_result();

        if ($stmt->num_rows > 0) {
            $error = "Username or email already exists.";
        } else {
            $hashed_password = password_hash($password, PASSWORD_DEFAULT);
            $stmt = $conn->prepare("INSERT INTO users (username, email, password) VALUES (?, ?, ?)");
            $stmt->bind_param("sss", $username, $email, $hashed_password);

            if ($stmt->execute()) {
                $success = "Registration successful! You can <a href='login.html'>login now</a>.";
            } else {
                $error = "Registration failed. Please try again.";
            }
        }

        $stmt->close();
    }

    $conn->close();
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Register - Admissions Help Desk</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
  <style>
    html, body {
      height: 100%;
      margin: 0;
      background: url('https://images.unsplash.com/photo-1588072432836-e10032774350?auto=format&fit=crop&w=1470&q=80') no-repeat center center fixed;
      background-size: cover;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      position: relative;
    }
    body::before {
      content: "";
      position: fixed;
      top: 0; left: 0; right: 0; bottom: 0;
      background: rgba(0,0,0,0.6);
      z-index: 0;
    }
    .register-container {
      z-index: 1;
      position: relative;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100%;
    }
    .register-form {
      background: rgba(255, 255, 255, 0.95);
      padding: 2rem;
      border-radius: 12px;
      box-shadow: 0 8px 20px rgba(0,0,0,0.3);
      width: 100%;
      max-width: 450px;
    }
    .form-title {
      font-weight: 700;
      color: #0056b3;
    }
    .btn-success {
      background-color: #28a745;
      border: none;
    }
    .btn-success:hover {
      background-color: #218838;
    }
    .alert a {
      color: #004085;
      text-decoration: underline;
    }
  </style>
</head>
<body>
<div class="register-container">
  <form class="register-form" method="POST" action="">
    <h3 class="form-title text-center mb-4">Create an Account</h3>

    <?php if ($error): ?>
      <div class="alert alert-danger"><?php echo $error; ?></div>
    <?php elseif ($success): ?>
      <div class="alert alert-success"><?php echo $success; ?></div>
    <?php endif; ?>

    <!-- Username -->
    <div class="mb-3">
      <label for="username" class="form-label">Username</label>
      <input type="text" name="username" id="username" class="form-control" required>
    </div>

    <!-- Email -->
    <div class="mb-3">
      <label for="email" class="form-label">Email address</label>
      <input type="email" name="email" id="email" class="form-control" required>
    </div>

    <!-- Password -->
    <div class="mb-3">
      <label for="password" class="form-label">Password</label>
      <input type="password" name="password" id="password" class="form-control" required>
    </div>

    <!-- Confirm Password -->
    <div class="mb-3">
      <label for="confirm_password" class="form-label">Confirm Password</label>
      <input type="password" name="confirm_password" id="confirm_password" class="form-control" required>
    </div>

    <!-- Register Button -->
    <div class="d-grid mb-3">
      <button type="submit" class="btn btn-success">Register</button>
    </div>

    <div class="text-center">
      <small>Already have an account? <a href="login.html">Login here</a></small>
    </div>
  </form>
</div>
</body>
</html>
