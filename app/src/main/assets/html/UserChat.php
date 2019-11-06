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
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script type="text/javascript" src="sendMsg.js"></script>
        
      
        <?php
		include ("https://thebhakti.com/PbChat/connect.php");
		$mob=$_GET['mob'];
		$userMob=$_GET['userMob'];
		$name=$_GET['name'];
		$type=$_GET['type'];
		
		?>
        
        <script>
function fetchMsg(userMob)
{

	 if (userMob=="") {
    alert("Your session has been expired Plz reselect the user from which you want to chat");
  }
  else { 
 	
		
        if (window.XMLHttpRequest) {
            // code for IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp = new XMLHttpRequest();
        } else {
            // code for IE6, IE5
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        } 
  xmlhttp.onreadystatechange=function() 
   {
    if (this.readyState==4 && this.status==200) 
	{
      document.getElementById("loadMsg").innerHTML=this.responseText;   
    }
  };
  xmlhttp.open("GET","fetchMsg.php?userMob="+userMob,true);
  xmlhttp.send();
}	
}

</script>
<script>
setInterval(function(){
fetchMsg(<?php echo $userMob; ?>);
},2000);
</script>
    </head>
    <body class="bg-black" style="">

        <div class="form-box" id="form-box">
            <div class="header">Live Chat</div>
            <form action="file:///C:/xampp/htdocs/xampp/BhaktiChat/index.html" method="post">
                <div class="body bg-gray conversation">   
                <div class="live-chat">
                              
                     <div id="loadMsg"></div>
                      
                  </div>  
               </div>
                
            
            <div class="footer1">
            <textarea placeholder="Type A message..." name="msg" id="msg"></textarea>   
            <button type="button" id="send" name="send" style="height:50px" onclick="sendMsg(<?php echo $mob; ?>,'<?php echo $name; ?>','<?php echo $type; ?>',<?php echo $userMob; ?>)"><img src="images/click.PNG" /></button>
            </div>
            </form>
            
        </div>


        <!-- jQuery 2.0.2 -->
        <script src="./MainChat_files/jquery.min.js.download"></script>
        <!-- Bootstrap -->
        <script src="./MainChat_files/bootstrap.min.js.download" type="text/javascript"></script>        

    
</body></html>