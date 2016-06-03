<?php
require_once "classes/Database.php";
require_once "classes/User.php";

session_start();

$user = new User();
$user->setUserId($_SESSION['user_id']);
$user->viewUser();
$db = new Database();

echo "Uw huidig puntenaantal: " . $user->getUserScore();
