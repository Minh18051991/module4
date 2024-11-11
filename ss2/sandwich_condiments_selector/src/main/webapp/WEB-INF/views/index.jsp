<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sandwich Condiments</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <h1 class="mt-5 text-center">Select Condiments for Your Sandwich</h1>
                <form action="save" method="post" class="mt-3">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="condiment" value="Lettuce" id="lettuce">
                        <label class="form-check-label" for="lettuce">Lettuce</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="condiment" value="Tomato" id="tomato">
                        <label class="form-check-label" for="tomato">Tomato</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="condiment" value="Mustard" id="mustard">
                        <label class="form-check-label" for="mustard">Mustard</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="condiment" value="Ketchup" id="ketchup">
                        <label class="form-check-label" for="ketchup">Ketchup</label>
                    </div>
                    <button type="submit" class="btn btn-primary mt-3">Submit</button>
                </form>
            </div>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>