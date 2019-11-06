<?php
include("connect.php");
$userMob=$_GET['userMob'];
$name=$_GET['name'];
$mob=$_GET['mob'];
$type=$_GET['type'];
$msg=$_GET['msg'];
$date=date("Y-m-d");
$sql="INSERT INTO `'".$userMob."'` (id, name, msg,type,time)
VALUES ('".$mob."','".$name."','".$msg."','".$type."','".$date."')";

$result=mysqli_query($conn,$sql);

// update users seen
$updateSql="UPDATE login_users SET seen='seen' WHERE id='".$userMob."'";
$result1=mysqli_query($conn,$updateSql);
print_r($msg);

?>