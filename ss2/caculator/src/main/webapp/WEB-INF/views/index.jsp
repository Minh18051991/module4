<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Simple Calculator</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>
<div class="container">
    <h1 class="text-center">Simple Calculator</h1>
    <form action="caculate" method="post" class="form-group">
        <div class="form-group">
            <input type="number" name="num1" step="any" class="form-control" required>
        </div>
        <div class="form-group">
            <input type="number" name="num2" step="any" class="form-control" required>
        </div>
        <div class="form-group text-center">
            <div class="row">
                <div class="col-4">
                    <button type="submit" name="operator" value="+" class="btn btn-primary btn-block">+</button>
                </div>
                <div class="col-4">
                    <button type="submit" name="operator" value="-" class="btn btn-primary btn-block">-</button>
                </div>
                <div class="col-4">
                    <button type="submit" name="operator" value="*" class="btn btn-primary btn-block">*</button>
                </div>
            </div>
            <div class="row mt-2">
                <div class="col-4">
                    <button type="submit" name="operator" value="/" class="btn btn-primary btn-block">/</button>
                </div>
                <div class="col-4">
                    <button type="submit" name="operator" value="%" class="btn btn-primary btn-block">%</button>
                </div>
                <div class="col-4">
                    <button type="submit" name="operator" value="^" class="btn btn-primary btn-block">^</button>
                </div>
            </div>
        </div>
    </form>
    <c:if test="${not empty result}">
        <h2>Result: ${result}</h2>
    </c:if>
    <c:if test="${not empty error}">
        <h2 class="text-danger">Error: ${error}</h2>
    </c:if>
</div>
</body>
</html>