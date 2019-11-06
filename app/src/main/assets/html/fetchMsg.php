<!DOCTYPE html>
<!-- saved from url=(0065)file:///C:/xampp/htdocs/xampp/BhaktiChat/pages/examples/chat.html -->
<html class="bg-black"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>AdminLTE | Log in</title>
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- bootstrap 3.0.2 -->
        <link href="./MainChat_files/bootstrap.min.css" rel="stylesheet" type="text/css">
        <!-- font Awesome -->
        <link href="./MainChat_files/font-awesome.min.css" rel="stylesheet" type="text/css">
        <!-- Theme style -->
        <link href="./MainChat_files/AdminLTE.css" rel="stylesheet" type="text/css">

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->
    
     <?php
include ("https://thebhakti.com/PbChat/connect.php");
$userMob=$_GET['userMob'];

?>   

    </head>
    <body class="bg-black" style="">

      <?php

	
$sql="SELECT * from `'".$userMob."'`";
$result=mysqli_query($conn,$sql);
while($row=mysqli_fetch_array($result))
{
	if($row['type']=="admin")
	{
		?>
        
        <div id="astro" class="admin pull-left">
                        <span style="font-size:11px"><?php echo $row['name']; ?></span><br>
                        <h9><?php echo $row['msg']; ?> </h9><br>
                        
                   
                             
        <?php 
		$sql1="SELECT * from `login_users` WHERE id='".$row['id']."'";
		$result1=mysqli_query($conn,$sql1);
		while($row1=mysqli_fetch_array($result1))
		{
			?><span style="font-size:9px"><?php echo $row1['seen']; ?></span></div> <?php
		}
		  ?><?php
	}
	else
	{
		?>
         <div id="user" class="user pull-right">
                        <span style="font-size:11px"><?php echo $row['name']; ?></span><br>
                          <h9><?php echo $row['msg']; ?></h9><br>
                      <?php 
		$sql1="SELECT * from `login_users` WHERE id='".$row['id']."'";
		$result1=mysqli_query($conn,$sql1);
		while($row1=mysqli_fetch_array($result1))
		{
			?><span style="font-size:9px"><?php echo $row1['seen']; ?></span></div><?php
		}
		  ?><?php
	}

}


?>
                              
                      
                      
                     
                      
                      

    
</body></html>