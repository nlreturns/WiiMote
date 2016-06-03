<?php

require_once "classes/Bet.php";
require_once "classes/User.php";

$bet = new Bet();
$user = new User();
$data = $bet->viewAllBets();


if ($data) {
    foreach ($data as $bet) {
        $user->setUserId($bet['user_id']);
        $user->viewUser();
        
        echo ucfirst($user->getUserName()) . " heeft " . $bet['bet_value'] . " ingezet op Speler " . $bet['bet_player'] . "<br/>";
    }
} else {
    echo "Nog geen bets geplaatst";
}