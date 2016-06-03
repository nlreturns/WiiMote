<?php

require_once "DbCheck.php";

/**
 * @access public
 * @author janwillem
 */
class Check extends DbCheck {

    /**
     * @AttributeType int
     */
    private $winner;
    
    private $check_db;

    /**
     * @access public
     */
    public function __construct() {
        $this->check_db = new DbCheck();
    }

    /**
     * @access public
     */
    public function getWinner() {
        if (isset($this->winner)) {
            return $this->winner;
        } else {
            return NULL;
        }
    }

    /**
     * @access public
     * @param int winner
     * @return void
     * @ParamType winner int
     * @ReturnType void
     */
    public function setWinner($winner) {
        if (is_numeric($winner)) {
            $this->winner = $winner;
        } else {
            return $winner . "is not numerical";
        }
    }

    /**
     * @access public
     */
    public function viewWinner() {
        $data = $this->check_db->viewWinner();
        $this->winner = $data['name'];
        
        return $data;
    }

    /**
     * @access public
     */
    public function editWinner() {
        return $this->check_db->editWinner($this->winner);
    }

}

?>