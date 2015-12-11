<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
//$response = array();
 
// check for required fields
//if (isset($_POST['sUsername']) && isset($_POST['productName']) && isset($_POST['productDesc']) && isset($_POST['productOPrice']) && isset($_POST['productNPrice']) ) {
 

    $json_input  = file_get_contents('php://input');
    $obj = json_decode($json_input);
    

    $sZipCode = $obj->{'sZipCode'};

    
    
    
    

    // include db connect class
    require_once './db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql inserting a new row
    $result = mysql_query("select sUsername,sName from storeOwners where sZipCode='$sZipCode'");
 if ($result) {

       // $response["success"] = 1;
       // $response["message"] = "Products successfully selected.";
 while ($row = mysql_fetch_assoc($result)) {
         $response[] = array('sName'=>$row['sName'] , 'sUsername'=>$row['sUsername']
                              );
   } 
        echo json_encode($response);
    } else {
       
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        
        echo json_encode($response);
    }

?>								