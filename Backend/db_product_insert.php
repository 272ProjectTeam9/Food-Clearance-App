<?php
  //Generic php function to send GCM push notification
   function sendMessageThroughGCM($registatoin_ids, $message) {
    //Google cloud messaging GCM-API url
        $url = 'https://android.googleapis.com/gcm/send';
        $fields = array(
            'registration_ids' => $registatoin_ids,
            'data' => $message,
        );
    // Update your Google Cloud Messaging API Key
    define("GOOGLE_API_KEY", "AIzaSyDVKbjpRwAvrqVyeyFZ23Ge5dOYoECPsdo");    
        $headers = array(
            'Authorization: key=' . GOOGLE_API_KEY,
            'Content-Type: application/json'
        );
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0); 
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
        $result = curl_exec($ch);       
        if ($result === FALSE) {
            die('Curl failed: ' . curl_error($ch));
        }
        curl_close($ch);
        return $result;
    }
?>


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
       $gcmRegIds = array();
       while ($row = mysql_fetch_assoc($result)) {
           $usr = $row['username'];
           
           $query1 = sprintf("SELECT gcmRegId FROM users WHERE username='%s'",mysql_real_escape_string($usr));
           $result1 = mysql_query($query1);
           if(!$result1){
               $message  = 'Invalid query: ' . mysql_error() . "\n";
               $message .= 'Whole query: ' . $query1;
               die($message);
           }
           while($row1 = mysql_fetch_assoc($result1))
          {
                $id = $row1['gcmRegId'];
                echo $id;
                array_push($gcmRegIds, $id); 
          }

       }
       $message = array("m" => "New Product Added","store" => "$sUsername","newPrice" => "$productNPrice", "productName" => "$productName" );
       $pushStatus = sendMessageThroughGCM($gcmRegIds, $message);
       echo $pushStatus;

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