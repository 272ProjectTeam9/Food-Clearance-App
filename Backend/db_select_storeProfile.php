<?php

 $response = array();

 $json_input  = file_get_contents('php://input');
 $obj = json_decode($json_input);

require_once './db_connect.php';
 
$db = new DB_CONNECT();
 
 $sUsername = $obj->{'sUsername'};
 
 
	
	 
	 $res = mysql_query("SELECT * FROM storeOwners WHERE sUsername='$sUsername'");
	 if ($res) {
                 while ($row = mysql_fetch_assoc($res)) {
         $response[] = array('sName'=>$row['sName'],'sEmail'=>$row['sEmail'],'sAddress'=>$row['sAddress'],'sCity'=>$row['sCity'],'sZipCode'=>$row['sZipCode'],'sPassword'=>$row['sPasswordmd5'] );
   }  
                    


                  
         echo json_encode($response);
	 } else {
                $response["success"] = 0;
		$response["message"] = "Login Failed";
        echo json_encode($response);
	 }
?>	