<?php
require_once "classes/User.php";

session_start();

$login = new User();

if (isset($_GET['logout'])) {
    unset($_SESSION);
    session_destroy();
    header('Location: login.php');
}

if (!$login->isloggedin()) {
    header('Location: login.php');

    if (!$_SESSION['user_id'] === 1)
        header('Location: index.php');
}
?>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Need for Beast</title>
        <link rel="stylesheet" type="text/css" href="css/index.css" />
        <script>
            function won(player) {
                if (player == "") {
                    return;
                } else {
                    if (window.XMLHttpRequest)
                        xmlhttp = new XMLHttpRequest();
                    else
                        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");

                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
                            document.getElementById("test").innerHTML = xmlhttp.responseText;

                    };

                    xmlhttp.open("GET", "winner.php?p=" + player, true);
                    xmlhttp.send();
                }
            }
        </script>
    </head>
    <body>
        <button><a href="index.php?logout" id="logout">Uitloggen</a></button>


        Wie heeft er gewonnen? <br /><br />
        <input type='button' value='Stop' onclick='won(-1)' />
        <input type='button' value='Start' onclick='won(5)' />
        <input type='button' value='1' onclick='won(1)' />
        <input type='button' value='2' onclick='won(2)' />
        <input type='button' value='3' onclick='won(3)' />
        <input type='button' value='4' onclick='won(4)' />
        <input type='button' value='Delete all bets' onclick='won(6)' />
        <input type='button' value='Make it rain' onclick='won(7)' />
        <div id='test'></div>

    </body>
</html>