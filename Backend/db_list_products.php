<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 

 
// check for required fields
//if (isset($_POST['sUsername']) && isset($_POST['productName']) && isset($_POST['productDesc']) && isset($_POST['productOPrice']) && isset($_POST['productNPrice']) ) {
 

    $json_input  = file_get_contents('php://input');
    $obj = json_decode($json_input);
    

    $sName = $obj->{'sName'};

    
   
    
    

    // include db connect class
    require_once './db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql inserting a new row
    $result = mysql_query("select productName,sUsername from products where sUsername = (select sUsername from storeOwners where sName = '$sName') ");
 
    // check if row inserted or not
    if ($result) {

        while ($row = mysql_fetch_assoc($result)) {
    $response[]=array('productName'=>$row['productName'],'sUsername'=>$row['sUsername']);
    ///                'title'=>$row['title'],
    ///                'start'=>$row['start'].' '.$row['time'],
    ///                'backgroundColor'=>$row['backgroundColor']
    
}

        // successfully inserted into database
       /// $response["success"] = 1;
       /// $response["message"] = "Products successfully selected.";
       /// $response["result"] = $result;
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    }
//} else {
//    // required field is missing
//    $response["success"] = 0;
//    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
//    echo json_encode($response);
//}
?>													