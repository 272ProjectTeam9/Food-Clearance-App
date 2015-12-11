<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
//if (isset($_POST['sUsername']) && isset($_POST['productName']) && isset($_POST['productDesc']) && isset($_POST['productOPrice']) && isset($_POST['productNPrice']) ) {
 

    $json_input  = file_get_contents('php://input');
    $obj = json_decode($json_input);
    
    $sUsername = $obj->{'sUsername'};
    $sPassword = $obj->{'sPassword'};
    $sEmail = $obj->{'sEmail'};
    $sName = $obj->{'sName'};
    $sAddress = $obj->{'sAddress'};
    $sCity = $obj->{'sCity'};
    $sZipCode = $obj->{'sZipCode'};
    
   
    
    
    

    // include db connect class
    require_once './db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    $sPasswordmd5 = md5($sPassword);
    // mysql inserting a new row
    $result = mysql_query("Update storeOwners set sPassword='$sPasswordmd5',sEmail='$sEmail',sName='$sName',sAddress='$sAddress',sCity='$sCity',sZipCode='$sZipCode' where sUsername='$sUsername'");
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "successfully subscribed";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    }

?>									