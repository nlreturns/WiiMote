<?php

require_once "classes/Check.php";
require_once "classes/Bet.php";
require_once "classes/User.php";

$check = new Check();
$bet = new Bet();
$user = new User();

$winner = $_GET['p'];

if ($_GET['p'] == "5") {
    $winner = 0;
}

echo $winner;

$check->setWinner($winner);
$check->editWinner();

$bets = $bet->viewAllBets();

if ($winner != -1) {
    if ($bets) {
        foreach ($bets as $bid) {
            if ($bid['bet_player'] == $winner) {
                $user->setUserId($bid['user_id']);
                $user->viewUser();
                $user->setUserScore($user->getUserScore() + ($bid['bet_value'] * 2));
                $user->editUser();
            }
        }
    }
} else {
    // new race starting
    if ($bets) {
        foreach ($bets as $bid) {
            // refund everyone's bet
            $user->setUserId($bid['user_id']);
            $user->viewUser();
            $user->setUserScore($user->getUserScore() + $bid['bet_value']);
            $user->editUser();
            // delete bet
            $bet->setBetId($bid['bet_id']);
            $bet->deleteBet();
        }
    }
}

if ($winner == 6) {
    if ($bets) {
        foreach ($bets as $bid) {
            $bet->setBetId($bid['bet_id']);
            $bet->deleteBet();
        }
    }
}

if ($winner == 7) {
    $allusers = $user->viewAllUsers();
    if ($allusers) {
        foreach ($allusers as $gebruiker) {
            $user->setUserId($gebruiker['user_id']);
            $user->viewUser();
            $newscore = (int)$user->getUserScore() + 1000;
            $user->setUserScore($newscore);
            $user->editUser();
        }
    }
}