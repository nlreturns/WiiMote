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
                viewBets();
                checkWon();
                updateTop();
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

            function updateTop() {
                if (window.XMLHttpRequest)
                    xmlhttp6 = new XMLHttpRequest();
                else
                    xmlhttp6 = new ActiveXObject("Microsoft.XMLHTTP");

                xmlhttp6.onreadystatechange = function() {
                    if (xmlhttp6.readyState == 4 && xmlhttp6.status == 200)
                        document.getElementById("list").innerHTML = xmlhttp6.responseText;

                };

                xmlhttp6.open("GET", "top.php", true);
                xmlhttp6.send();

            }

            function viewBets() {
                if (window.XMLHttpRequest)
                    xmlhttp2 = new XMLHttpRequest();
                else
                    xmlhttp2 = new ActiveXObject("Microsoft.XMLHTTP");

                xmlhttp2.onreadystatechange = function() {
                    if (xmlhttp2.readyState == 4 && xmlhttp2.status == 200)
                        document.getElementById("bets").innerHTML = xmlhttp2.responseText;

                };

                xmlhttp2.open("GET", "bets.php", true);
                xmlhttp2.send();
            }

            function checkWon() {
                if (window.XMLHttpRequest)
                    xmlhttp4 = new XMLHttpRequest();
                else
                    xmlhttp4 = new ActiveXObject("Microsoft.XMLHTTP");

                xmlhttp4.onreadystatechange = function() {
                    if (xmlhttp4.readyState == 4 && xmlhttp4.status == 200)
                        document.getElementById("won").innerHTML = xmlhttp4.responseText;

                };

                xmlhttp4.open("GET", "won.php", true);
                xmlhttp4.send();
            }

            function sendForm(player, value) {

                if (player == "" || value == "") {
                    document.getElementById("betting").innerHTML = "";
                    return;
                } else {
                    if (window.XMLHttpRequest)
                        xmlhttp3 = new XMLHttpRequest();
                    else
                        xmlhttp3 = new ActiveXObject("Microsoft.XMLHTTP");

                    xmlhttp3.onreadystatechange = function() {
                        if (xmlhttp3.readyState == 4 && xmlhttp3.status == 200)
                            document.getElementById("betting").innerHTML = xmlhttp3.responseText;

                    };

                    xmlhttp3.open("GET", "betting.php?p=" + player + "&v=" + value, true);
                    xmlhttp3.send();
                }
            }


        </script>
    </head>
    <body>
        <?php echo "Welkom terug " . $_SESSION['user_name']; ?>
        <button><a href="index.php?logout" id="logout">Uitloggen</a></button>
        <form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
            <input type="hidden" name="cmd" value="_s-xclick">
            <input type="hidden" name="hosted_button_id" value="TT67PMB4E7S22">
            <input type="image" src="https://www.paypalobjects.com/nl_NL/NL/i/btn/btn_donate_LG.gif" border="0" name="submit" alt="PayPal, de veilige en complete manier van online betalen.">
            <img alt="" border="0" src="https://www.paypalobjects.com/nl_NL/i/scr/pixel.gif" width="1" height="1">
        </form>
        <form>
            <div id="upLeft">
                <h1>Speler 1 </h1>
                Bet here: <input type="text" placeholder="100" name="p1" /> <input id="betButton" value="inzetten" text="bet" type="button" onclick="sendForm('1', this.form.p1.value)" />
            </div>
            <div id="upRight">
                <h1>Speler 2</h1>
                Bet here: <input type="text" placeholder="100" name="p2" /> <input id="betButton" value="inzetten" text="bet" type="button" onclick="sendForm('2', this.form.p2.value)" />
            </div>
            <div id="downLeft">
                <h1>Speler 3</h1>
                Bet here: <input type="text" placeholder="100" name="p3" /> <input id="betButton" value="inzetten" text="bet" type="button" onclick="sendForm('3', this.form.p3.value)" />
            </div>
            <div id="downRight">
                <h1>Speler 4</h1>
                Bet here: <input type="text" placeholder="100" name="p4" /> <input id="betButton" value="inzetten" text="bet" type="button" onclick="sendForm('4', this.form.p4.value)" />
            </div>
        </form>
        <div id="bets">
            Hier komen de bet's te staan...
        </div>
        <div id="list">
            Topscores komen hier te staan
        </div>
        <div id="score">
            Punten aantal word geteld...
        </div>
        <div id="betting">
            Hier komt je huidige bet te staan...
        </div>
        <div id="won">
            Hier komt de race vooruitgang te staan...
        </div>
        <div></div>
    </body>
</html>
