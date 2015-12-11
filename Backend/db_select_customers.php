<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 

 
// check for required fields
//if (isset($_POST['sUsername']) && isset($_POST['productName']) && isset($_POST['productDesc']) && isset($_POST['productOPrice']) && isset($_POST['productNPrice']) ) {
 

    $json_input  = file_get_contents('php://input');
    $obj = json_decode($json_input);
    

    $sUsername = $obj->{'sUsername'};

    
    
    
    

    // include db connect class
    require_once './db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql inserting a new row
    $result = mysql_query("select u.firstName,u.lastName,u.email from users u JOIN subscribe s ON u.username=s.username where s.sUsername='$sUsername'");
 
    // check if row inserted or not
    if (mysql_num_rows($result)>0) {

        while ($row = mysql_fetch_assoc($result)) {
    $response[]=array('firstName'=>$row['firstName'],
                       'lastName'=>$row['$lastName'],
                       'email'=>$row['email']
    );
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