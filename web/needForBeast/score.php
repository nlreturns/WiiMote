<?php
require_once "Database.php";
require_once "User.php";

session_start();

$user = new User();
$user->setUserId($_SESSION['user_id']);
$user->viewUser();
$db = new Database();

echo "Huidig puntenaantal: " . $user->getUserScore();
