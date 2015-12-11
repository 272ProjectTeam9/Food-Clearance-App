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
    $productName = $obj->{'productName'};
    $productDesc = $obj->{'productDesc'};
    $productOPrice = $obj->{'productOPrice'};
    $productNPrice = $obj->{'productNPrice'};
    

    // include db connect class
    require_once './db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql inserting a new row
    $result = mysql_query("INSERT INTO products(sUsername, productName, productDesc, productOPrice, productNPrice) VALUES('$sUsername', '$productName', '$productDesc', '$productOPrice', '$productNPrice')");
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Product successfully added.";
 
        // echoing JSON response
        echo json_encode($response);

       $query = sprintf("SELECT username FROM subscribe WHERE sUsername='%s'",mysql_real_escape_string($sUsername));
       
       $result = mysql_query($query);
       if (!$result) {
          $message  = 'Invalid query: ' . mysql_error() . "\n";
          $message .= 'Whole query: ' . $query;
          die($message);
       }
       while ($row = mysql_fetch_assoc($result)) {
           echo $row['username'];
       }

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