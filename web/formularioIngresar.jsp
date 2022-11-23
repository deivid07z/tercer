<%-- 
    Document   : formularioIngresar
    Created on : 10/05/2019, 12:27:32 PM
    Author     : Sebastian
--%>

<%
    if(request.getSession().getAttribute("error")!=null){
        out.println("<script>");
        out.println("eval(alert('Datos Incorrectos'));");
        out.println("</script>");
    }
    request.getSession().setAttribute("error",null);
%>
            
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="width=device-width, initial-scale=1">
        <link href="css/styles.css" rel="stylesheet" type="text/css">
        <title>Inicio de Sesion</title>
    </head>
    <body>
        <h1>Sistema de Apoyo Alimentario</h1>
        <h2>Bienvenido al Sistema de Apoyo Alimentario</h2>
        <p>A continuación ingrese sus datos de Usuario, Contraseña y Rol para
        poder ingresar al sistema</p>
        <FORM action=Servlet_Login method="post">
            <div class="imgcontainer">
                <img src="images/avatar.png" alt="Avatar" class="avatar">
            </div>
            <div class="container">
                <input name="user" type="text" value="Usuario" required/><br>
                <input name="password" type="password" value="Password" required/><br><br>
                <input type='radio' name="tipo" value="Estudiante" required> Estudiante &nbsp
                <input type='radio' name="tipo" value="Revisor" required> Revisor &nbsp
                <input type='radio' name="tipo" value="Administrador" required> Administrador &nbsp <br>
                <input type="submit" value="Ingresar" />
            </div>
        </FORM>
    </body>
</html>
