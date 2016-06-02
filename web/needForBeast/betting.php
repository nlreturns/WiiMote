<?php
require_once "Database.php";
require_once "User.php";
?>


<!DOCTYPE html>
<html>
    <head>
    </head>
    <body>
        <!--
        - check gebruiker bestaat
        - check gebruiker heeft genoeg money
        - haal geld van gebruiker zijn account
        - sla sessie op van zijn bet, $_SESSION['bet'] = {amount,horse};
        
        - values die ik moet krijgen; user_id(session), bet amount($_GET['v']
          user_score(session), horse($_GET['p']
        
        -->
        <?php
        session_start();

        $user_id = $_SESSION['user_id'];
        $user_score = $_SESSION['user_score'];

        $player = $_GET['p'];
        $value = intval($_GET['v']);

        $db = new Database();
        $user = new User();

        $user->setUserId($user_id);
        if (!isset($_SESSION['bet'])) {
            if ($user->viewUser()) {
                if ($value > $user_score) {
                    echo "Niet genoeg geld!";
                } else {
                    $user->setUserScore($user_score - $value);
                    $user->editUser();
                    $_SESSION['bet'] = [$value, $player];
                    $_SESSION['score'] = $user->getUserScore();
                    echo "Je hebt " . $value . " ingezet op " . $player;
                    //@TODO database bet zetten(?)
                }
            } else {
                echo "Er is iets fout gegaan met uw account. ";
                ?>
                <a href="login.php">Log hier opnieuw in</a>
                <?php
            }
        } else {
            echo "Je hebt al " . $_SESSION['bet'][0] . " ingezet op " . $_SESSION['bet'][1];
        }
        ?>
    </body>
</html>