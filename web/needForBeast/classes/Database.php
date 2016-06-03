<?php

/**
 * Description of database
 *
 * @author GHoogendoorn
 * @version 0.1
 *  
 * Version history:
 * 0.1                GHoogendoorn    Initial version
 * 0.2                GHoogendoorn    Added create database.
 * 0.3  - 30.10.2014  Jan-Willem Dooms <janwillem.dooms@gmail.com>  MySQL to MySQLi conversion
 *
 */
require_once "classes/Config.php";
require_once "classes/Error.php";

class Database {

    protected $connection;         //The MySQL database connection
    private $query_result;
    private $error;

    public function __construct() {
        /* Make an instance of the error class */
        $this->error = new Error();
        /* Make connection to database */
        $this->connection = new \mysqli(DB_SERVER, DB_USER, DB_PASS, DB_NAME) or die('There was a problem connecting to the database');
    }

    /**
     *
     * @return string  '' | mysqlerrno()-mysql error txt [query]
     */
    protected function getDbError() {
        if ($this->connection->errno) {
            return $this->connection->errno . '-' . $this->connection->error . " [$query]";
        }
        return '';
    }

    /**
     *
     * The function checks the mysql error.
     * If found an error is triggered.
     * @param string $query
     * @return bool FALSE (Ok) or TRUE The error array is set, and should be checked.
     */
    protected function checkDbErrors($query) {
        if ($this->connection->errno) {
            switch ($this->connection->errno) {
                case 1062:
                    $this->error->setError(TXT_ERROR_DUPLICATE_ENTRY);
                    break;
                case 0:
                    $this->error->setError("ERROR UNKNOWN");

                default:
                    $error = "MySQL error " . $this->connection->errno . ": " .
                            $this->connection->error . "\n<br><br>\n$query\n<br>";
                    $this->error->setError($error);
                    break;
            }

            return TRUE;
        }
        $this->error->setError("No errors.");
        return FALSE;
    }

    /**
     * query - Performs the given query on the database and
     * returns the result, which may be false, true or a
     * resource identifier.
     * @param string $query
     * @return bool TRUE if Ok | FALSE check error array
     */
    protected function dbquery($query) {

        $this->query_result = $this->connection->query($query);

        return $this->query_result;
    }

    /**
     *
     *  Gerhard Hoogendoorn
     * @param array $data_array The result from a mysql_fetch_array()
     * @return array the modified input array
     */
    protected function dbOutArray($data_array) {
        //var_dump($data_array);
        if ($data_array) {
            foreach ($data_array as $field => $value) {
                if (is_numeric($value)) {
                    continue;
                } else if (is_string($value)) {
                    $data_array[$field] = $this->dbOutString($value);
                }
            }
        }
        return $data_array;
    }

    /**
     * Get the single results from the database
     * This function also removes the database escapes.
     *
     * @return array The array contains the elements of mysql_fetch_array
     * 
     * @return FALSE No data was found.
     */
    protected function dbFetchArray($query) {

        $result = $this->connection->query($query);

        //TODO: add error check.	
        $data_array = $result->fetch_array(MYSQLI_ASSOC);

        if ($data_array === FALSE) {
            $this->error->setError("No data");
            return FALSE;
        }

        if (!$this->connection->errno) {
            $data_array = $this->dbOutArray($data_array);
        }
        return $data_array;
    }

    protected function dbNumRows() {
        if ($this->isMySqliResource($this->query_result)) {
            return $this->query_result->num_rows;
        } else {
            $this->error->setError("No rows");
            return FALSE;
        }
    }

    /**
     * Get multiple results from the database
     * This function also removes the database escapes.
     *
     * @return array The array contains an array with the row elements
     *
     * @return FALSE No succesfull query was found.
     */
    protected function dbFetchAll() {

        //@@TODO RESOURCE CHECK IS_A ?

        $return_array = array();

        while ($row = $this->query_result->fetch_array(MYSQLI_ASSOC)) {
            $return_array[] = $this->dbOutArray($row);
        }
        return $return_array;
    }

    /**
     *
     * @param string $table MySql table name
     * @return bool TRUE if exists | False if not exists
     */
    protected function dbTableExists($table) {
        $query = "DESC " . $table;
        $result = $this->dbquery($query);

        if ($result = ($this->connection->errno == 1146)) {
            $this->error->setError("Table doesn't exist");
            return FALSE;
        }
        return TRUE;
    }

    /**
     * The function escapes a string.
     *  This function should be called before storage
     *  in the database.
     *
     * @param string $string
     * @return string
     */
    protected function dbInString($string) {

        return $this->connection->real_escape_string($string);
    }

    /**
     * The function removes the escapes from a database returned string.
     *  This function should be called after the database query.
     *
     * @param string $string
     * @return string
     */
    protected function dbOutString($string) {
        if (is_string($string)) {
            return trim(stripslashes($string));
        }
    }

    /**
     * Just reset the query resource link
     * 
     */
    protected function dbReset() {
        $this->query_result = '';
    }

    protected function checkText($text, $len) {
        if (empty($text) ||
                (!is_string($text)) ||
                (strlen($text) > $len )) {
            return FALSE;
        }
        return TRUE;
    }

    /**
     *
     * @param int $id A database Id.
     * @param string $field Datase field name
     * @return bool TRUE (ok) or FALSE  The error array is set, 
     *                                  and should be checked
     */
    protected function checkId($id, $field) {
        if (!is_numeric($id)) {
            $this->error->setError(TXT_ERROR_WRONG_VAR_TYPE . " [$id] " . $field);
            return FALSE;
        }
        return TRUE;
    }

    /**
     *
     * @param resource $res The resource that should be checked
     * @return bool TRUE if $res is mysql resource | FALSE any other case
     * (check error array)
     * 
     * @@TODO NEEDS REWORKING
     */
    private function isMySqliResource($res) {
        $res_type = is_resource($res) ? get_resource_type($res) : gettype($res);

        if (!is_a($res_type, 'Mysqli')) {
            $this->error->setError('Invalid resource type: ' . $res_type);
            return FALSE;
        }
        return TRUE;
    }

    private function createDatabase() {
        $query = "CREATE DATABASE " . DB_NAME;
        return $this->dbquery($query);
    }

    public function __destruct() {

        //echo "<br />". __FILE__ . ' ' . __LINE__ . ' '. "<strong>CLOSE DB #". $this->connection ."</strong><br />";
    }

    public function lastId() {
        return $this->connection->insert_id;
    }

}

?>