// JavaScript Document
function sendMsg(mob,name,type,userMob)
{

	var msg=$("#msg").val();
	$("#msg").val(' ');
	 if (msg=="") {
    alert("Please Enter Msg");
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
       
	}
  };
  xmlhttp.open("GET","sendMsgg.php?userMob="+userMob+'&name='+name+'&type='+type+'&msg='+msg+'&mob='+mob,true);
  xmlhttp.send();
}
	
}