<script language= javascript type= text/javascript >
	function dirPage(page){
		window.location="../"+page;
	}
</script>

<?php
	//Destiny
	$to = $_POST['to'];
	//Subject
	$subject = "Programa Apoyo Alimentario";
	//Message
	$message = "Ha sido aceptado(a) en el programa";
	//Send
	$bool = mail($to,$subject,$message);
	if($bool){
		echo "<script> dirPage('Servlet_Login');</script>";
	}else{
		echo "Mensaje no enviado";
	}
?>
