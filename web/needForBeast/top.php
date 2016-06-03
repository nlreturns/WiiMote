<?php
require_once "classes/User.php";

$user = new User();

//top 5 example ;
//$users = $user->viewAllUsers2(0,5);
$users = $user->viewAllUsers2(0,10);

$i = 1;
echo "<table>";
foreach($users as $gebruiker){
    $user->setUserId($gebruiker['user_id']);
    $user->viewUser();
    echo "<tr><td>".$i.". </td><td>" . ucfirst($user->getUserName()) . "</td><td>"
            . " - </td><td>" . $user->getUserScore() . "</td></tr>";
    $i++;
}