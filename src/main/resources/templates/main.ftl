<html>
<body>

<form method="post" action="/main">
    <h4>Привет ${user.name}</h4>
    <div>
        <div>
            <#if message??>
                ${message}
            </#if>
        </div>
        <div>Время захода на сайт: ${time}</div>
        <h5>Ваши данные:</h5>
        <input placeholder="${user.userName}" name="username"><br/>
        <small>Данное поле выводит ваш логин. Если вы ходите поменять его, сделайте это тут</small>
        <br/><br/>
        <input placeholder="${user.name}" name="name"><br/>
        <small>Данное поле выводит ваше имя. Если вы ходите поменять его, сделайте это тут</small>
        <br/><br/>
        <input placeholder="${user.secondName}" name="lastname"><br/>
        <small>Данное поле выводит вашу фамилию. Если вы ходите поменять ее, сделайте это тут</small>
        <br/><br/>
        <input placeholder="${user.password}" name="password"><br/>
        <small>Данное поле выводит ваш пароль. Если вы ходите поменять его, сделайте это тут</small>
        <br/><br/>
        <p>количество посещений = ${user.count}</p><br/>
        <input type="submit" value="Сохранить">

</form>

<form method="post" action="/addphoto"  enctype="multipart/form-data">
    <label>Вообще, можно еще загрузить ваше фото</label>
    <input type="file" name="file">
    <div>
            <#if user.fileName??>
                <img src="../img/${user.fileName}">
            </#if>
    </div>
    <input type="submit" value="Add">
</form>

</div>
</body>
</html>