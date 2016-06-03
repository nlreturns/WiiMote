<?php
require_once "classes/Database.php";
require_once "classes/User.php";
require_once "classes/Bet.php";
require_once "classes/Check.php";
?>


<!DOCTYPE html>
<html>
    <head>
    </head>
    <body>
        <?php
        session_start();

        $db = new Database();
        $user = new User();
        $bet = new Bet();
        $check = new Check();

        $user_id = $_SESSION['user_id'];
        $user->setUserId($user_id);
        $user->viewUser();

        $user_score = $user->getUserScore();

        $player = $_GET['p'];
        $value = intval($_GET['v']);

        $bet->setUserId($user_id);
        
        
        $check->viewWinner();

        if ($check->getWinner() == 0 || $check->getWinner() == 1 || $check->getWinner() == 2 || $check->getWinner() == 3 || $check->getWinner() == 4 ) {
            echo "Race is al bezig!";
        } else {
            if (!$bet->userBet()) {
                if ($user->viewUser()) {
                    if ($value > $user->getUserScore()) {
                        echo "Niet genoeg geld!";
                    } elseif ($value < 0) {
                        echo "Echt dikke cheaters jullie";
                    } elseif ($value == 0){
                        echo "Niet echt nuttig om 0 in te zetten.";
                    } else {
                        $user->setUserScore($user_score - $value);
                        $user->editUser();
                        echo "Je hebt " . $value . " ingezet op Speler " . $player;
                        // zet de bet in de database
                        $bet->setUserId($user_id);
                        if ($bet->userBet()) {
                            $bet->deleteBet();
                        }
                        $bet->setBetPlayer($player);
                        $bet->setBetValue($value);
                        $bet->addBet();
                    }
                } else {
                    echo "Er is iets fout gegaan met uw account. ";
                    ?>
                    <a href="login.php">Log hier opnieuw in</a>
                    <?php
                }
            } else {
                echo "Je hebt al " . $bet->getBetValue() . " ingezet op " . $bet->getBetPlayer();
            }
        }
        ?>
    </body>
</html>