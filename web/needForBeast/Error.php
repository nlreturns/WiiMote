<?php
/**
 * Description of Error
 *  Provide generic error handling class
 *
 * @author GHoogendoorn
 * @version 0.1
 */
class Error {
//     private $error_array = array();
//     private $write_to_file = FALSE;

//     //put your code here
//     public function Error(){


//     }

     public function setError( $error_txt ){
         if (!empty($error_txt)){
             $this->error_array[] = $error_txt;
         }
     }

//     /**
//      *
//      * Add an input error array to the current error array.<br />
//      * If the input is a string, the input is added to the error array.
//      *
//      * @param array $error_array input error array.
//      */
//     public function setErrorArray($error_array){
//         if ( is_array($error_array)){
//             $this->error_array = array_merge($this->error_array, $error_array);
//         } else if (is_string($error_array) ){
//             $this->error_array = $error_array;
//         }
//     }

//     public function getErrorArray(){
//         return $this->error_array;
//     }

//     public function resetError(){
//         $this->error_array = array();
//     }

//     public function getErrorNr(){
//         return (count($this->error_array));
//     }
 }
// ?>
