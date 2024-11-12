<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Mail Configuration</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-control {
            width: 100%;
        }
        .btn {
            margin-top: 10px;
        }
        .flash-message {
            margin-bottom: 20px;
            padding: 10px;
            border: 1px solid #d4edda;
            background-color: #d4edda;
            color: #155724;
        }
    </style>
</head>
<body class="container">

<h2>Settings</h2>

<c:if test="${not empty message}">
    <div class="flash-message">${message}</div>
</c:if>

<form:form action="updateConfig" method="post" modelAttribute="emailConfig" class="form-horizontal">

    <div class="form-group">
        <label for="language">Languages</label>
        <form:select path="language" class="form-control">
            <form:options items="${languages}"/>
        </form:select>
    </div>

    <div class="form-group">
        <label for="pageSize">Page Size:</label>
        <div>
            Show
            <form:select path="pageSize" class="form-control d-inline-block w-auto">
                <form:options items="${pageSizes}"/>
            </form:select>
            emails per page
        </div>
    </div>

    <div class="form-group form-check">
        <label class="form-check-label">
            <form:checkbox path="spamsFilter" class="form-check-input"/>
            Enable spams filter
        </label>
    </div>

    <div class="form-group">
        <label for="signature">Signature:</label>
        <form:textarea path="signature" rows="4" placeholder="Your signature here..." class="form-control"/>
    </div>

    <div class="form-group">
        <button type="submit" class="btn btn-primary">Update</button>
        <button type="button" class="btn btn-secondary">Cancel</button>
    </div>
</form:form>

<h3>Current Configuration:</h3>
<p>Language: <c:out value="${emailConfig.language}"/></p>
<p>Page Size: <c:out value="${emailConfig.pageSize}"/> emails per page</p>
<p>Spams Filter: <c:out value="${emailConfig.spamsFilter ? 'Enabled' : 'Disabled'}"/></p>
<p>Signature: <pre><c:out value="${emailConfig.signature}"/></pre></p>

</body>
</html>