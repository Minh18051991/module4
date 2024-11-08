<!DOCTYPE html>
<html>
<head>
    <title>Dictionary</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h1 class="mt-5">Dictionary Lookup</h1>
        <form action="/lookup" method="get" class="mt-3">
            <div class="form-group">
                <label for="word">Enter word:</label>
                <input type="text" id="word" name="word" class="form-control">
            </div>
            <button type="submit" class="btn btn-primary">Lookup</button>
        </form>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>