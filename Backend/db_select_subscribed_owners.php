<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 

 
// check for required fields
//if (isset($_POST['sUsername']) && isset($_POST['productName']) && isset($_POST['productDesc']) && isset($_POST['productOPrice']) && isset($_POST['productNPrice']) ) {
 

    $json_input  = file_get_contents('php://input');
    $obj = json_decode($json_input);
    

    $username = $obj->{'username'};

    
    
    
    

    // include db connect class
    require_once './db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql inserting a new row
    $result = mysql_query("select sName from storeOwners o JOIN subscribe s ON o.sUsername=s.sUsername where s.username='$username'");
 
    // check if row inserted or not
    if ($result) {

        while ($row = mysql_fetch_assoc($result)) {
    $response[]=array('sName'=>$row['sName']);
}

        
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