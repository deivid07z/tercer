/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Negocio.Convocatoria;
import Negocio.Estado;
import Negocio.Tipo;
import datos.ConvocatoriaDAO;
import datos.EstadoDAO;
import datos.TipoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.CaException;
import util.Ingreso;
import util.RHException;
/**
 *
 * @author danie
 */
@WebServlet(name = "Servlet_CrearConvocatoria", urlPatterns = {"/Servlet_CrearConvocatoria"})
public class Servlet_CrearConvocatoria extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if(request.getSession().getAttribute("Datos incorrectos")!=null){
                out.println("<script>");
                out.println("eval(alert('"+request.getSession().getAttribute("Datos incorrectos")+"'));");
                out.println("</script>");
                request.getSession().setAttribute("Datos incorrectos",null);
                request.getSession().setAttribute("convocatoriaAct",null);
            }
            ConvocatoriaDAO convDAO = new ConvocatoriaDAO();
            TipoDAO tipoDAO = new TipoDAO();
            EstadoDAO estDAO = new EstadoDAO();
            Convocatoria conv;
            if(request.getParameter("formularioMenu")!=null)
                request.getSession().setAttribute("formularioMenu",request.getParameter("formularioMenu"));  
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Nueva Convocatoria</title>");            
            out.println("<link href='css/styles_c.css' rel='stylesheet'>");  
            out.println("</head>");
            out.println("<body>");
            if (CaException.getInstance().getDetalle() != null) {
                out.print(CaException.getInstance().getLastException());
                CaException.getInstance().setDetalle(null);
            }
            if((request.getParameter("fechaIC")==null || request.getParameter("fechaIC")==("")) && (request.getSession().getAttribute("formularioMenu").equals("Nueva Convocatoria"))){
                out.println("<h1>Nueva Convocatoria</h1>");
                out.println("<h2>A continuación registre cada uno de los campos para crear una concovatoria correspondiente al periodo deseado</h2><br>");
                out.println("<form action=Servlet_CrearConvocatoria method=\"post\">");
                out.println("<h3>Ingrese el periodo</h3>");
                out.println("<input type=\"text\" name=\"periodo\" value=\"2019-3\"><br>");
                out.println("<h3>Ingrese el valor de la fecha inicial de la convocatoria</h3>");
                out.println("<input type=\"date\" name=\"fechaIC\" min=\"2019-07-11\" value=\"2019-07-11\" class=\"dates\"><br>");
                out.println("<h3>Ingrese el valor de la fecha final de la convocatoria</h3>");
                out.println("<input type=\"date\" name=\"fechaFC\" min=\"2019-07-11\" value=\"2019-07-11\" class=\"dates\"><br>");
                out.println("<hr/>");
                out.println("<h4>Ingrese los valores para los siguientes 4 periodos de tiempo</h4>");
                out.println("<hr/>");
                out.println("<h31>Nombre del periodo 1</h31><br>");
                out.println("<input name=\"nPeriodo1\" type=\"text\" value=\"Abierta\" required/>");
                out.println("<h3>Fecha de inicio</h3>");
                out.println("<input type=\"date\" name=\"fechaIP1\" min=\"2019-07-11\" value=\"2019-07-11\" class=\"dates\"><br>");
                out.println("<h3>Fecha de fin</h3>");
                out.println("<input type=\"date\" name=\"fechaFP1\" min=\"2019-07-11\" value=\"2019-07-11\" class=\"dates\"><br><br>");
                out.println("<hr/>");
                out.println("<h31>Nombre del periodo 2</h31><br>");
                out.println("<input name=\"nPeriodo2\" type=\"text\" value=\"Validacion\" required/>");
                out.println("<h3>Fecha de inicio</h3>");
                out.println("<input type=\"date\" name=\"fechaIP2\" min=\"2019-07-11\" value=\"2019-07-11\" class=\"dates\"><br>");
                out.println("<h3>Fecha de fin</h3>");
                out.println("<input type=\"date\" name=\"fechaFP2\" min=\"2019-07-11\" value=\"2019-07-11\" class=\"dates\"><br><br>");
                out.println("<hr/>");
                out.println("<h31>Nombre del periodo 3</h31><br>");
                out.println("<input name=\"nPeriodo3\" type=\"text\" value=\"Asignacion puntaje\" required/>");
                out.println("<h3>Fecha de inicio</h3>");
                out.println("<input type=\"date\" name=\"fechaIP3\" min=\"2019-07-11\" value=\"2019-07-11\" class=\"dates\"><br>");
                out.println("<h3>Fecha de fin</h3>");
                out.println("<input type=\"date\" name=\"fechaFP3\" min=\"2019-07-11\" value=\"2019-07-11\" class=\"dates\"><br><br>");
                out.println("<hr/>");
                out.println("<h31>Nombre del periodo 4</h31><br>");
                out.println("<input name=\"nPeriodo4\" type=\"text\" value=\"Publicacion resultados\" required/>");
                out.println("<h3>Fecha de inicio</h3><br>");
                out.println("<input type=\"date\" name=\"fechaIP4\" min=\"2019-07-11\" value=\"2019-07-11\" class=\"dates\"><br>");
                out.println("<h3>Fecha de fin</h3>");
                out.println("<input type=\"date\" name=\"fechaFP4\" min=\"2019-07-11\" value=\"2019-07-11\" class=\"dates\"><br><br>");
                out.println("<hr/>");
                out.println("<h4>Ingrese los cupos de cada tipo</h4>");
                out.println("<hr/>");
                out.println("<h3>Ingrese los cupos del tipo CO - Completo</h3>");
                out.println("<input type=\"number\" name=\"co\" min=\"0\" value=\"0\" class=\"numbers\"><br>");
                out.println("<h3>Ingrese los cupos del tipo TA - Tipo A</h3>");
                out.println("<input type=\"number\" name=\"ta\" min=\"0\" value=\"0\" class=\"numbers\"><br>");
                out.println("<h3>Ingrese los cupos del tipo TB - Tipo B<br/></h3>");
                out.println("<input type=\"number\" name=\"tb\" min=\"0\" value=\"0\" class=\"numbers\"><br><br>");
                out.println("<hr/>");
                out.println("<input type=\"submit\" value=\"Ingresar\">");
                out.println("<hr/>");
                out.println("<div class=\"container\">");
                out.println("<a href='Servlet_Menu'><input type='button' value='Volver' class=\"cancelbtn\"></a>");
                out.println("</div>");
            }else{
                if((request.getParameter("fechaIC")==null || request.getParameter("fechaIC")==("")) && (request.getSession().getAttribute("formularioMenu").equals("Actualizar Convocatoria"))&&(request.getParameter("convocatoriaAct")==null)&&request.getParameter("periodoBuscar")==null){
                    out.println("<form action=Servlet_CrearConvocatoria method=\"post\">");
                    out.println("<br>");
                    out.println("<h2>Ingrese el periodo</h2>");
                    out.println("<input type=\"text\" name=\"periodoBuscar\" value=\"2019-3\"><br/>");
                    out.println("<input type=\"submit\" value=\"Enviar\">");
                    out.println("<div class=\"container\">");
                    out.println("<a href='Servlet_Menu'><input type='button' value='Volver' class=\"cancelbtn\"></a>");
                    out.println("</div>");

                }else{
                    if(request.getParameter("periodoBuscar")!=null){
                        conv =convDAO.buscarConvocatoriaPK(request.getParameter("periodoBuscar")); 
                        if(conv!=null){
                            request.getSession().setAttribute("esActualizando", "ActualizarDatos");
                            ArrayList<Tipo> tipo= tipoDAO.buscarTipoPK(request.getParameter("periodoBuscar"));
                            ArrayList<Estado> est = estDAO.buscarEstadoPK(request.getParameter("periodoBuscar"));
                            request.getSession().setAttribute("convocatoriaAct","validado"); 
                            out.println("<h1>Actualizar convocatoria</h1>");
                            out.println("<form action=Servlet_CrearConvocatoria method=\"post\">");
                            out.println("<h3>Periodo: "+request.getParameter("periodoBuscar")+"</h3><br>");
                            out.println("<input name='periodo' type='hidden' value='"+request.getParameter("periodoBuscar")+"'>");
                            out.println("<h3>Ingrese el valor de la fecha inicial de la convocatoria<h3>");
                            out.println("<input type=\"date\" name=\"fechaIC\" min=\"2019-07-11\" value="+conv.getFechaApertura()+" class=\"dates\"><br>");
                            out.println("<h3>Ingrese el valor de la fecha final de la convocatoria</h3>");
                            out.println("<input type=\"date\" name=\"fechaFC\" min=\"2019-07-11\" value="+conv.getFechaCierre()+" class=\"dates\"><br>");
                            out.println("<hr>");
                            out.println("<h4>Ingrese los valores para los siguientes 4 periodos</h4>");
                            out.println("<hr>");
                            out.println("<h31>Nombre del periodo 1</h31><br>");
                            out.println("<input name=\"nPeriodo1\" type=\"text\" value="+est.get(0).getDescripcion_estado()+" required/><br/>");
                            out.println("<h3>Fecha de inicio</h3>");
                            out.println("<input type=\"date\" name=\"fechaIP1\" min=\"2019-07-11\" value="+est.get(0).getFecha_inicial()+" class=\"dates\"><br>");
                            out.println("<h3>Fecha de fin</h3>");
                            out.println("<input type=\"date\" name=\"fechaFP1\" min=\"2019-07-11\" value="+est.get(0).getFecha_final()+" class=\"dates\"><br><nr>");
                            out.println("<hr>");
                            out.println("<h31>Nombre del periodo 2</h31><br>");
                            out.println("<input name=\"nPeriodo2\" type=\"text\" value="+est.get(1).getDescripcion_estado()+" required/><br/>");
                            out.println("<h3>Fecha de inicio</h3>");
                            out.println("<input type=\"date\" name=\"fechaIP2\" min=\"2019-07-11\" value="+est.get(1).getFecha_inicial()+" class=\"dates\"><br>");
                            out.println("<h3>Fecha de fin</h3>");
                            out.println("<input type=\"date\" name=\"fechaFP2\" min=\"2019-07-11\" value="+est.get(1).getFecha_final()+" class=\"dates\"><br><br>");
                            out.println("<hr>");
                            out.println("<h31>Nombre del periodo 3</h31><br>");
                            out.println("<input name=\"nPeriodo3\" type=\"text\" value="+est.get(2).getDescripcion_estado()+" required/><br/>");
                            out.println("<h3>Fecha de inicio</h3>");
                            out.println("<input type=\"date\" name=\"fechaIP3\" min=\"2019-07-11\" value="+est.get(2).getFecha_inicial()+" class=\"dates\"><br>");
                            out.println("<h3>Fecha de fin</h3>");
                            out.println("<input type=\"date\" name=\"fechaFP3\" min=\"2019-07-11\" value="+est.get(2).getFecha_final()+" class=\"dates\"><br><br>");
                            out.println("<hr>");
                            out.println("<h31>Nombre del periodo 4</h31><br>");
                            out.println("<input name=\"nPeriodo4\" type=\"text\" value="+est.get(3).getDescripcion_estado()+" required/><br/>");
                            out.println("<h3>Fecha de inicio</h3>");
                            out.println("<input type=\"date\" name=\"fechaIP4\" min=\"2019-07-11\" value="+est.get(3).getFecha_inicial()+" class=\"dates\"><br>");
                            out.println("<h3>Fecha de fin</h3>");
                            out.println("<input type=\"date\" name=\"fechaFP4\" min=\"2019-07-11\" value="+est.get(3).getFecha_final()+" class=\"dates\"><br><br>");
                            out.println("<hr>");
                            out.println("<h4>Ingrese los cupos de cada tipo</h4>");
                            out.println("<hr>");
                            out.println("<h3>Ingrese los cupos del tipo CO - Completo</h3>");
                            out.println("<input type=\"number\" name=\"co\" min=\"0\" value="+tipo.get(0).getCantidad()+" class=\"numbers\"><br>");
                            out.println("<h3>Ingrese los cupos del tipo TA - Tipo A</h3>");
                            out.println("<input type=\"number\" name=\"ta\" min=\"0\" value="+tipo.get(1).getCantidad()+" class=\"numbers\"><br>");
                            out.println("<h3>Ingrese los cupos del tipo TB - Tipo B</h3>");
                            out.println("<input type=\"number\" name=\"tb\" min=\"0\" value="+tipo.get(2).getCantidad()+" class=\"numbers\"><br><br>");
                            out.println("<hr>");
                            out.println("<input type=\"submit\" value=\"Ingresar\">");
                            out.println("<hr>");
                            out.println("<div class=\"container\">");
                            out.println("<a href='Servlet_Menu'><input type='button' value='Volver' class=\"cancelbtn\"></a>");
                            out.println("</div>");
                            
                        }else{
                            request.getSession().setAttribute("Datos incorrectos", "Convocatoria no existe");
                            response.sendRedirect("Servlet_Menu");
                        }
                    }else{                     
                            boolean falso = false;
                            Date fechaIC = Date.valueOf(request.getParameter("fechaIC"));
                            Date fechaFC = Date.valueOf(request.getParameter("fechaFC"));
                            Date fechaIP1 = Date.valueOf(request.getParameter("fechaIP1"));
                            Date fechaFP1 = Date.valueOf(request.getParameter("fechaFP1"));
                            Date fechaIP2 = Date.valueOf(request.getParameter("fechaIP2"));
                            Date fechaFP2 = Date.valueOf(request.getParameter("fechaFP2"));
                            Date fechaIP3 = Date.valueOf(request.getParameter("fechaIP3"));
                            Date fechaFP3 = Date.valueOf(request.getParameter("fechaFP3"));
                            Date fechaIP4 = Date.valueOf(request.getParameter("fechaIP4"));
                            Date fechaFP4 = Date.valueOf(request.getParameter("fechaFP4"));
                            if(!fechaIC.equals(fechaIP1)){
                                falso=true;
                                request.getSession().setAttribute("Datos incorrectos", "Revise la fecha inicial de la convocatoria y la fecha inicial del periodo 1");
                            }else{
                                if(fechaIP1.after(fechaFP1)){
                                    falso=true;
                                    request.getSession().setAttribute("Datos incorrectos","Revise la fecha inicial del periodo 1 y la fecha final del periodo 1");
                                }else{
                                    if(fechaFP1.after(fechaIP2)){
                                        falso=true;
                                        request.getSession().setAttribute("Datos incorrectos", "Revise la fecha final del periodo 1 "
                                                + "y la fecha inicial del periodo 2");
                                    }else{
                                        if(fechaIP2.after(fechaFP2)){
                                            falso=true;
                                            request.getSession().setAttribute("Datos incorrectos", "Revise la fecha inicial del periodo 2"
                                                    + "y la fecha final del periodo 2");
                                        }else{
                                            if(fechaFP2.after(fechaIP3)){
                                               falso=true;
                                               request.getSession().setAttribute("Datos incorrectos", "Revise la fecha final del periodo 2 "
                                                + "y la fecha inicial del periodo 3");
                                            }else{
                                                if(fechaIP3.after(fechaFP3)){
                                                    request.getSession().setAttribute("Datos incorrectos", "Revise la fecha inicial del periodo 3"
                                                            + " y la fecha final del periodo 3");
                                                    falso=true;
                                                }else{
                                                    if(fechaFP3.after(fechaIP4)){
                                                        falso=true;
                                                        request.getSession().setAttribute("Datos incorrectos", "Revise la fecha final del periodo 3 "
                                                + "y la fecha inicial del periodo 4");
                                                    }else{
                                                        if(fechaIP4.after(fechaFP4)){
                                                            request.getSession().setAttribute("Datos incorrectos", "Revise la fecha inicial del periodo 4"
                                                                    + " y la fecha final del periodo 4");
                                                            falso=true;
                                                        }else{
                                                            if(!fechaFP4.equals(fechaFC)){
                                                               falso=true; 
                                                               request.getSession().setAttribute("Datos incorrectos", "Revise la fecha final del periodo 4 "
                                                + "y la fecha final de la convocatoria");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                
                            }
                            if(falso){
                                response.sendRedirect("Servlet_CrearConvocatoria");            
                            }else{
                                if(request.getSession().getAttribute("esActualizando")!=null){
                                    convDAO.actualizarConvocatoria(new Convocatoria(request.getParameter("periodo"), fechaIC,fechaFC));
                                    tipoDAO.actualizarTipo(new Tipo("CO", Integer.parseInt(request.getParameter("co")), request.getParameter("periodo")));
                                    tipoDAO.actualizarTipo(new Tipo("TA", Integer.parseInt(request.getParameter("ta")), request.getParameter("periodo")));
                                    tipoDAO.actualizarTipo(new Tipo("TB", Integer.parseInt(request.getParameter("tb")), request.getParameter("periodo")));
                                    estDAO.actualizarEstado(new Estado(fechaIP1, fechaFP1, 1, request.getParameter("nPeriodo1"), request.getParameter("periodo")));
                                    estDAO.actualizarEstado(new Estado(fechaIP2, fechaFP2, 2, request.getParameter("nPeriodo2"), request.getParameter("periodo")));
                                    estDAO.actualizarEstado(new Estado(fechaIP3, fechaFP3, 3, request.getParameter("nPeriodo3"), request.getParameter("periodo")));
                                    estDAO.actualizarEstado(new Estado(fechaIP4, fechaFP4, 4, request.getParameter("nPeriodo4"), request.getParameter("periodo")));
                                    request.getSession().setAttribute("esActualizando", null);
                                    response.sendRedirect("Servlet_Menu");
                                }else{
                                    convDAO.incluirConvocatoria(new Convocatoria(request.getParameter("periodo"), fechaIC,fechaFC));
                                    tipoDAO.incluirTipo(new Tipo("CO", Integer.parseInt(request.getParameter("co")), request.getParameter("periodo")));
                                    tipoDAO.incluirTipo(new Tipo("TA", Integer.parseInt(request.getParameter("ta")), request.getParameter("periodo")));
                                    tipoDAO.incluirTipo(new Tipo("TB", Integer.parseInt(request.getParameter("tb")), request.getParameter("periodo")));
                                    estDAO.incluirEstado(new Estado(fechaIP1, fechaFP1, 1, request.getParameter("nPeriodo1"), request.getParameter("periodo")));
                                    estDAO.incluirEstado(new Estado(fechaIP2, fechaFP2, 2, request.getParameter("nPeriodo2"), request.getParameter("periodo")));
                                    estDAO.incluirEstado(new Estado(fechaIP3, fechaFP3, 3, request.getParameter("nPeriodo3"), request.getParameter("periodo")));
                                    estDAO.incluirEstado(new Estado(fechaIP4, fechaFP4, 4, request.getParameter("nPeriodo4"), request.getParameter("periodo")));
                                    response.sendRedirect("Servlet_Menu");
                                }
                            }
                        }
                    }
                }
            }
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        } catch (RHException ex) {
            Logger.getLogger(Servlet_CrearConvocatoria.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Servlet_CrearConvocatoria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
