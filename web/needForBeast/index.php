<?php
require_once "User.php";

session_start();

$login = new User();

if (isset($_GET['logout'])) {
    unset($_SESSION);
    session_destroy();
    header('Location: login.php');
}

if (!$login->isloggedin()) {
    header('Location: login.php');
}
if (isset($_SESSION['user_id'])) {
    if ($_SESSION['user_id'] == "1")
        header('Location: admin.php');
}
?>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Need for Beast</title>
        <link rel="stylesheet" type="text/css" href="css/index.css" />
        <script>

            updateScore();

            setInterval(function() {
                updateScore();
            }, 1000);

            function updateScore() {
                if (window.XMLHttpRequest)
                    xmlhttp = new XMLHttpRequest();
                else
                    xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");

                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
                        document.getElementById("score").innerHTML = xmlhttp.responseText;

                };

                xmlhttp.open("GET", "score.php", true);
                xmlhttp.send();

            }

            /**
             function getCookie(name) {
             var dc = document.cookie;
             var prefix = name + "=";
             var begin = dc.indexOf("; " + prefix);
             if (begin == -1) {
             begin = dc.indexOf(prefix);
             if (begin != 0)
             return null;
             }
             else{
             begin += 2;
             var end = document.cookie.indexOf(";", begin);
             if (end == -1) {
             end = dc.length;
             }
             }
             return unescape(dc.substring(begin + prefix.length, end));
             }
             /*
             var cookie = getCookie("id");
             if(cookie == null)
             window.location.href = "login.php";//*/


            // set koekjes
            document.cookie =
                    'id=<?php echo $_SESSION['user_id'] ?>; expires=Fri, 3 Aug 2020 20:47:11 UTC; path=/';

            document.cookie =
                    'score=<?php echo $_SESSION['user_score'] ?>; expires=Fri, 3 Aug 2020 20:47:11 UTC; path=/';


            function sendForm(player, value) {

                if (player == "" || value == "") {
                    document.getElementById("betting").innerHTML = "";
                    return;
                } else {
                    if (window.XMLHttpRequest)
                        xmlhttp = new XMLHttpRequest();
                    else
                        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");

                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
                            document.getElementById("betting").innerHTML = xmlhttp.responseText;

                    };

                    xmlhttp.open("GET", "betting.php?p=" + player + "&v=" + value, true);
                    xmlhttp.send();
                }
            }


        </script>
    </head>
    <body>
        <button><a href="index.php?logout" id="logout">Uitloggen</a></button>
        <form>
            <div id="upLeft">
                <h1>Speler 1 </h1>
                Bet here: <input type="text" placeholder="100" name="p1" /> <input text="bet" type="button" onclick="sendForm('player 1', this.form.p1.value)" />
            </div>
            <div id="upRight">
                <h1>Speler 2</h1>
                Bet here: <input type="text" placeholder="100" name="p2" />
            </div>
            <div id="downLeft">
                <h1>Speler 3</h1>
                Bet here: <input type="text" placeholder="100" name="p3" />
            </div>
            <div id="downRight">
                <h1>Speler 4</h1>
                Bet here: <input type="text" placeholder="100" name="p4" />
            </div>
        </form>
        <div id="score">Punten aantal word geteld.....</div>
        <div id="betting"><?php
            if (isset($_SESSION['bet'])) {
                if ($_SESSION['bet']) {
                    echo "Je hebt " . $_SESSION['bet'][0] . " ingezet op " . $_SESSION['bet'][1];
                } else {
                    echo "Hier komt je huidige bet te staan..";
                }
            }else{
                echo "Hier komt je huidige bet te staan..";
            }
            ?>
        </div>
    </body>
</html>
