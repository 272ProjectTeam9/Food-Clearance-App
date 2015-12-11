<?php

 $response = array();

 $json_input  = file_get_contents('php://input');
 $obj = json_decode($json_input);

require_once './db_connect.php';
 
$db = new DB_CONNECT();
 
 $sUsername = $obj->{'sUsername'};
 $sPassword = md5($obj->{'sPassword'});
 
	
	 
	 $res = mysql_query("SELECT sUsername,sPassword,sName FROM storeOwners WHERE sUsername='$sUsername' AND sPassword='$sPassword'");
	 if ($res) {
                 while ($row = mysql_fetch_assoc($res)) {
         $response[] = array('sName'=>$row['sName'] );
   }  
                    


                  
         echo json_encode($response);
	 } else {
                $response["success"] = 0;
		$response["message"] = "Login Failed";
        echo json_encode($response);
	 }
?>	