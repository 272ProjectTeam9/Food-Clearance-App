<?php
 

 
// check for required fields
//if (isset($_POST['sUsername']) && isset($_POST['productName']) && isset($_POST['productDesc']) && isset($_POST['productOPrice']) && isset($_POST['productNPrice']) ) {
 

    $json_input = file_get_contents('php://input');
    $obj = json_decode($json_input);
    

    $sUsername = $obj->{'sUsername'};
    $productName = $obj->{'productName'};
   // $productId = $obj->{'productId'};
   

    
    
    
    

    // include db connect class
    require_once './db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql inserting a new row
    $result = mysql_query("select productDesc,productOPrice,productNPrice,date_format(Time,'%m-%d-%Y') as Time from products where sUsername='$sUsername' and productName='$productName'"); //and productId='$productId'");
 
    // check if row inserted or not
    if ($result) {
       while ($row = mysql_fetch_assoc($result)) {
       
        $response[]=array('productDesc'=>$row['productDesc'] ,
                          'productOPrice'=>$row['productOPrice'] ,
                          'productNPrice'=>$row['productNPrice'],
                           'Time'=>$row['Time']
                     );
 
       } 
        echo json_encode($response);

    } else {
        
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
        echo json_encode($response);
    }
?>								