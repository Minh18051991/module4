<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Currency Converter</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2 class="mt-5">Currency Converter</h2>
    <form action="convert" method="post" class="mt-3">
        <div class="form-group">
            <label for="rate">Exchange Rate (USD to VND):</label>
            <input type="text" id="rate" name="rate" class="form-control" required>
        </div>
        <div class="form-group">
            <label for="usd">Amount in USD:</label>
            <input type="text" id="usd" name="usd" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary">Convert</button>
    </form>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>