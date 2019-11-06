<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<?php
include ("https://thebhakti.com/PbChat/connect.php");
$userMob=$_GET['userMob'];

?>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

</head>

<body>
<?php

	
$sql="SELECT * from `'".$userMob."'`";
$result=mysqli_query($conn,$sql);
while($row=mysqli_fetch_array($result))
{
	if($row['type']=="admin")
	{
		?><div style="background-color:#009; color:#FFF"><font size="2px"><?php echo $row['name']; ?></font> :<?php echo $row['msg']; ?> </div><?php 
		$sql1="SELECT * from `login_users` WHERE id='".$row['id']."'";
		$result1=mysqli_query($conn,$sql1);
		while($row1=mysqli_fetch_array($result1))
		{
			echo $row1['seen'];
		}
		  ?><?php
	}
	else
	{
		?><div style="background-color:#009; color:#FFF"><font size="2px"><?php echo $row['name']; ?> </font>:<?php echo $row['msg']; ?> </div><?php 
		$sql1="SELECT * from `login_users` WHERE id='".$row['id']."'";
		$result1=mysqli_query($conn,$sql1);
		while($row1=mysqli_fetch_array($result1))
		{
			echo $row1['seen'];
		}
		  ?><?php
	}

}


?>


</body>
</html>