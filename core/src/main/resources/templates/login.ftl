<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>index</title>
</head>
<body>
<#--<#if param?? && param.error??>
    <div>
        Invalid username and password.
    </div>
</#if>
<#if param.logout?? && param.logout??>
    <div>
        You have been logged out.
    </div>
</#if>-->

<form action="login" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <div><input type="submit" value="Sign In"/></div>
</form>
</body>
</html>