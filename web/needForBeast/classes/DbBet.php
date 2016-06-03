<?php

require_once "Database.php";

/**
 * @access public
 * @author janwillem
 */
class DbBet extends Database {

    /**
     * @AttributeType Scansysteem.Database
     */
    private $db;

    /**
     * @access public
     */
    public function __construct() {
        $this->db = new Database();
    }

    /**
     * @access public
     * @param int $user_id
     * @param int $bet_value
     * @param int $bet_player
     * @ParamType $user_id int
     * @ParamType $bet_value int
     * @ParamType $bet_player int
     */
    public function addBet($user_id, $bet_value, $bet_player) {
        //build query
        $query = "INSERT INTO  `need_for_beast`.`bets` (
                `bet_id` ,
                `user_id` ,
                `bet_value` ,
                `bet_player`
                )
                  VALUES (
                NULL, 
                '" . mysql_real_escape_string($user_id) . "',
                '" . mysql_real_escape_string($bet_value) . "',
                '" . mysql_real_escape_string($bet_player) . "'
                );";
        
        //execute query
        if (!$this->db->dbquery($query)) {
            return false;
        } else {
            //echo "Bet aangemaakt.";
        }
    }

    /**
     * @access public
     * @param int $bet_id
     * @ParamType $bet_id int
     */
    public function deleteBet($bet_id) {
        //build query
        $query = "DELETE FROM `bets` WHERE bet_id = " . $bet_id;
        //execute query
        if (!$this->db->dbquery($query)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @access public
     * @param int $bet_id
     * @ParamType $bet_id int
     */
    public function viewUser($bet_id) {
        //build query
        $query = "SELECT * FROM `bets` WHERE bet_id = " . $bet_id;
        
        // fetch query
        $data = $this->db->dbFetchArray($query);
        
        // check data
        if ( $data == NULL) {
            return FALSE;
        }
        return $data;
    }

    /**
     * @access public
     * @param int $bet_id
     * @param int $user_id
     * @param int $bet_value
     * @param int $bet_player
     * @ParamType $bet_id int
     * @ParamType $user_id int
     * @ParamType $bet_value int
     * @ParamType $bet_player int
     */
    public function editUser($bet_id, $user_id, $bet_value, $bet_player) {
        //build query
        $query = "UPDATE `bets` SET `user_id` = '".$user_id."', `bet_value` = '".$bet_value."', `bet_player` = '".$bet_player."' WHERE `bets`.`bet_id` = ".$bet_id.";";
        
        //execute query
        if (!$this->db->dbquery($query)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @access public
     */
    public function viewAllBets($page, $limit) {
        if($page == 0){
            
        }else{
            $page *= 30;
        }
        //build query
        $query = "SELECT * FROM `bets` ORDER BY `bet_value` DESC LIMIT " . $limit . " OFFSET " . $page;
        
        // check for data
        if (!$this->db->dbquery($query)) {
            return false;
        }
        // fetch data
        if(!($result = $this->db->dbFetchAll())){
            // set error.
            //echo TXT_NO_DATA;
            return FALSE;
        }
        // return
        return $result;
    }
    
    public function userBet($user_id){
        //build query
        $query = "SELECT * FROM `bets` WHERE user_id = " . $user_id;
        
        // fetch query
        $data = $this->db->dbFetchArray($query);
        
        // check data
        if ( $data == NULL) {
            return FALSE;
        }
        return $data;
    }

}

?>