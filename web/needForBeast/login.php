
<?php
require_once "User.php";

session_start();

$login = new User;

if($login->isloggedin())
    header('Location: index.php');


if (isset($_POST["username"]) && isset($_POST["password"])) {
    $login->setUserName($_POST["username"]);
    $login->setUserPassword($_POST['password']);
    $login->login();
    header('Location: index.php');
}


?>

<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/index.css"/>
    </head>
    <body>

        <!--
        <form method="POST">
            Username: <input type="text" name="username" required /> <br />
            Password: <input type="password" name="password" required /> <br />
            <input type="submit" />
        </form>
        -->
        <form name="login-form" class="login-form" method="POST">

                <div class="header">
                    <h1>Welkom</h1>
                    <span>Log hier in om verder te gaan</span>
                </div>

                <div class="content">
                    <input name="username" type="text" class="input username" placeholder="Username" />
                    <div class="user-icon"></div>
                    <input name="password" type="password" class="input password" placeholder="Password" />
                    <div class="pass-icon"></div>		
                </div>

                <div class="footer">
                    <input type="submit" class="button" value="Login" />
                </div>

            </form>

    </body>
</html>