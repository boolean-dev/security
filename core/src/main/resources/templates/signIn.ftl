<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录</title>
</head>
<body>
<h2>浏览器登录页</h2>
<h3>表单登录</h3>
<form action="/user/login" method="post">
    <table>
        <tr>
            <td>用户名:</td>
            <td><input type="text" name="username"></td>
        </tr>
        <tr>
            <td>密码:</td>
            <td><input type="password" name="password"></td>
        </tr>
        <tr>
            <td>图形验证码:</td>
            <td>
                <input type="text" name="imageCode">
                <img src="/user/login/code/image">
            </td>
        </tr>
        <tr>
            <td colspan="2"><button type="submit">登录</button></td>
        </tr>
    </table>
</form>


<h3>短信登录</h3>
<form action="/authentication/phone" method="post">
    <table>
        <tr>
            <td>手机号:</td>
            <td><input type="text" name="mobile" value="1857911****"></td>
        </tr>
        <tr>
            <td>短信验证码:</td>
            <td>
                <input type="text" name="smsCode">
                <a href="/user/login/code/sms?mobile=1857911****">发送验证码</a>
            </td>
        </tr>
        <tr>
            <td colspan="2"><button type="submit">登录</button></td>
        </tr>
    </table>
</form>
</body>

<script>
</script>
</html>