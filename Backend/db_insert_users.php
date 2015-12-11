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
    

    $username = $obj->{'username'};
    $password = $obj->{'password'};
    $email = $obj->{'email'};
    $firstName = $obj->{'firstName'};
    $lastName = $obj->{'lastName'};
    
    
    

    // include db connect class
    require_once './db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
     $passwordmd5 = md5($password);
    // mysql inserting a new row
    $result = mysql_query("INSERT INTO users(username, password, email, firstName, lastName) VALUES('$username', '$passwordmd5', '$email', '$firstName', '$lastName')");
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "User successfully added.";
 
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