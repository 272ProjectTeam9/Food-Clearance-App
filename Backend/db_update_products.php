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
    $productId = $obj->{'productId'};
    $productName = $obj->{'productName'};
    $productDesc = $obj->{'productDesc'};
    $productOPrice = $obj->{'productOPrice'};
    $productNPrice = $obj->{'productNPrice'};
    

    // include db connect class
    require_once './db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql inserting a new row
    $result = mysql_query("update products SET TIME = current_timestamp, productName='$productName',productDesc='$productDesc',productOPrice='$productOPrice',productNPrice='$productNPrice' where sUsername='$sUsername' and productId='$productId'");
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Product successfully updated.";
 
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