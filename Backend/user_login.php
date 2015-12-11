<?php
 
 $response = array();
 
 $json_input  = file_get_contents('php://input');
 $obj = json_decode($json_input);
 
require_once './db_connect.php';
 
$db = new DB_CONNECT();
 
 $username = $obj->{'username'};
 $password = md5($obj->{'password'});
 
	 $username = $_POST['username'];
	 $password = md5($_POST['password']);
	 $sql = "SELECT username, password FROM users WHERE username='".$username."' AND password='".$password."' LIMIT 1";
	 $res = mysql_query($sql);
	 if ($res) {
         $response["success"] = 1;
		 $response["message"] = "Login Successful";
         echo json_encode($response);
	 } else {
        $response["success"] = 0;
		$response["message"] = "Login Failed";
        echo json_encode($response);
	 }
?>	