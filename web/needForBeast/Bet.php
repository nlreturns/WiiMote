<?php

require_once "DbBet.php";

/**
 * @access public
 * @author janwillem
 */
class Bet extends DbBet {

    /**
     * @AttributeType int
     */
    private $bet_id;

    /**
     * @AttributeType int
     */
    private $user_id;

    /**
     * @AttributeType int
     */
    private $bet_value;
    
    /**
     * @AttributeType int
     */
    private $bet_player;

    /**
     * @AttributeType DbClass
     */
    private $bet_db;

    /**
     * @access public
     */
    public function __construct() {
        $this->bet_db = new DbBet();
    }

    /**
     * @access public
     */
    public function getUserId() {
        if (isset($this->user_id)) {
            return $this->user_id;
        } else {
            return NULL;
        }
    }

    /**
     * @access public
     * @param int user_id
     * @return void
     * @ParamType user_id int
     * @ReturnType void
     */
    public function setUserId($user_id) {
        if (is_numeric($user_id)) {
            $this->user_id = $user_id;
        } else {
            return $user_id . "is not numerical";
        }
    }
    
    /**
     * @access public
     */
    public function getBetId() {
        if (isset($this->bet_id)) {
            return $this->bet_id;
        } else {
            return NULL;
        }
    }

    /**
     * @access public
     * @param int bet_id
     * @return void
     * @ParamType bet_id int
     * @ReturnType void
     */
    public function setBetId($bet_id) {
        if (is_numeric($bet_id)) {
            $this->bet_id = $bet_id;
        } else {
            return $bet_id . "is not numerical";
        }
    }

    /**
     * @access public
     */
    public function getBetValue() {
        if (isset($this->bet_value)) {
            return $this->bet_value;
        } else {
            return "Not set";
        }
    }

    /**
     * @access public
     * @param int bet_value
     * @return void
     * @ParamType bet_value int
     * @ReturnType void
     */
    public function setBetValue($bet_value) {
        $this->bet_value = $bet_value;
    }

    /**
     * @access public
     */
    public function getBetPlayer() {
        if (isset($this->bet_player)) {
            return $this->bet_player;
        } else {
            return "Not set";
        }
    }

    /**
     * @access public
     * @param int bet_player
     * @return void
     * @ParamType bet_player int
     * @ReturnType void
     */
    public function setBetPlayer($bet_player) {
        $this->bet_player = $bet_player;
    }

    /**
     * @access public
     */
    public function addBet() {
        return $this->bet_db->addBet($this->user_id, $this->bet_value, $this->bet_player);
    }

    /**
     * @access public
     */
    public function deleteBet() {
        return $this->bet_db->deleteBet($this->bet_id);
    }

    /**
     * @access public
     */
    public function viewBet() {
        $data = $this->bet_db->viewBet($this->bet_id);
        $this->user_id = $data['user_id'];
        $this->bet_value = $data['bet_value'];
        $this->bet_player = $data['bet_player'];
        
        return $data;
    }

    /**
     * @access public
     */
    public function editBet() {
        return $this->bet_db->editBet($this->bet_id, $this->user_id, $this->bet_value, $this->bet_player);
    }

    /**
     * @access public
     */
    public function viewAllBets($page = 0, $limit = 30) {
        return $this->bet_db->viewAllBets($page, $limit);
    }
    
}

?>