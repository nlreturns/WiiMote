<?php
require_once "classes/User.php";
require_once "classes/Bet.php";
require_once "classes/Check.php";

$check = new Check();

$data = $check->viewWinner();

$winner = $data['name'];

if($winner == -1){
    echo "Game is nog niet begonnen";
}elseif($winner == 0){
    echo "Game is bezig";
}elseif($winner == 6){
    echo "Game is nog niet begonnen";
}elseif($winner == 7){
    echo "Iedereen heeft 1000 punten gekregen";
}else{
    switch($winner){
        case 1:
            echo "1 wint";
            break;
        case 2:
            echo "2 wint";
            break;
        case 3:
            echo "3 wint";
            break;
        case 4:
            echo "4 wint";
            break;
    }
}

// doo stuff;