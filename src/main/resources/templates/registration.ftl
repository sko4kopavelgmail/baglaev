<html>
<body>
<h4>Страница регистрации</h4>
<form method="post" action="/registration">
    <input type="text" placeholder="Username" name="username"/><br/>
    <input type="password" placeholder="Password" name="password"/><br/>
    <input type="text" placeholder="First name" name="name"/><br/>
    <input type="text" placeholder="Last Name" name="secondName"/><br/>
    <input type="submit" value="save">
    <h5>${message!}</h5>
</form>
</body>
</html>