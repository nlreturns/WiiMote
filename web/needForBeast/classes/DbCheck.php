<?php

require_once "Database.php";

/**
 * @access public
 * @author janwillem
 */
class DbCheck extends Database {

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
     */
    public function viewWinner() {
        //build query
        $query = "SELECT * FROM `lastwon` WHERE id = 0";
        
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
     * @param int $winner
     * @ParamType $winner int
     */
    public function editWinner($winner) {
        //build query
        $query = "UPDATE `lastwon` SET `name` = '".$winner."' WHERE `lastwon`.`id` = 0";
        
        //execute query
        if (!$this->db->dbquery($query)) {
            return false;
        } else {
            return true;
        }
    }

}

?>