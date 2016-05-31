<?php

require_once "DbUser.php";

/**
 * @access public
 * @author janwillem
 */
class User extends DbUser {

    /**
     * @AttributeType int
     */
    private $user_id;

    /**
     * @AttributeType string
     */
    private $user_name;

    /**
     * @AttributeType string
     */
    private $user_password;
    
    /**
     * @AttributeType int
     */
    private $user_score;

    /**
     * @AttributeType Scansysteem
     */
    private $user_db;

    /**
     * @access public
     */
    public function __construct() {
        $this->user_db = new DbUser();
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
    public function getUserName() {
        if (isset($this->user_name)) {
            return $this->user_name;
        } else {
            return "Not set";
        }
    }

    /**
     * @access public
     * @param string user_name
     * @return void
     * @ParamType user_name string
     * @ReturnType void
     */
    public function setUserName($user_name) {
        $this->user_name = $user_name;
    }

    /**
     * @access public
     */
    public function getUserPassword() {
        if (isset($this->user_password)) {
            return $this->user_password;
        } else {
            return "Not set";
        }
    }

    /**
     * @access public
     * @param string user_password
     * @return void
     * @ParamType user_password string
     * @ReturnType void
     */
    public function setUserPassword($user_password) {
        $this->user_password = md5($user_password);
    }

    /**
     * @access public
     */
    public function getUserScore() {
        if (isset($this->user_score)) {
            return $this->user_score;
        } else {
            return "Not set";
        }
    }

    /**
     * @access public
     * @param int user_score
     * @return void
     * @ParamType user_score int
     * @ReturnType void
     */
    public function setUserScore($user_score) {
        $this->user_score = $user_score;
    }

    /**
     * @access public
     */
    public function login() {
        // send login to db class
        //return $this->user_db->login($this->user_name, $this->user_password);
        // check if logged in
        if (!$this->isloggedin()) {
            // check info
            if ($user_data = $this->user_db->login($this->user_name, $this->user_password)) {
                
                // set session data
                
                //Set user_id in session
                $_SESSION['user_id'] = $user_data[0]['user_id'];
                // Set user_name in session
                $_SESSION['user_name'] = $user_data[0]['user_name'];
                // Set user_type in session
                $_SESSION['user_score'] = $user_data[0]['user_score'];
                
                //return the session
                return $_SESSION;
                
            } else {
                // display error
                echo "Naam of wachtwoord is fout.";
            }
        } else {
            // display error
            echo "U bent al ingelogd.";
        }
    }

    /**
     * @access public
     */
    public function addUser() {
        return $this->user_db->addUser($this->user_name, $this->user_password, $this->user_score);
    }

    /**
     * @access public
     */
    public function deleteUser() {
        return $this->user_db->deleteUser($this->user_id);
    }

    /**
     * @access public
     */
    public function viewUser() {
        $data = $this->user_db->viewUser($this->user_id);
        $this->user_name = $data['user_name'];
        $this->user_password = $data['user_password'];
        $this->user_score = $data['user_score'];
        
        return $data;
    }

    /**
     * @access public
     */
    public function editUser() {
        return $this->user_db->editUser($this->user_id, $this->user_name, $this->user_password, $this->user_score);
    }

    /**
     * @access public
     */
    public function viewAllUsers($page = 0, $limit = 30) {
        return $this->user_db->viewAllUsers($page, $limit);
    }
    
    public function isloggedin(){
        //var_dump($_SESSION);
        if (isset($_SESSION['user_name'])) {
            // session excists, return TRUE
            return true;
        } else {
            return false;
        }
    }

}

?>