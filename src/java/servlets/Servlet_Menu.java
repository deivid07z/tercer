/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Negocio.Convocatoria;
import Negocio.Estado;
import datos.ConvocatoriaDAO;
import datos.EstadoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oracle.jdbc.OracleTypes;
import util.CaException;
import util.RHException;
import util.ServiceLocator;

/**
 *
 * @author Sebastian
 */
public class Servlet_Menu extends HttpServlet {

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
            throws ServletException, IOException, RHException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if(request.getSession().getAttribute("Datos incorrectos")!=null){
                out.println("<script>");
                out.println("eval(alert('"+request.getSession().getAttribute("Datos incorrectos")+"'));");
                out.println("</script>");
                request.getSession().setAttribute("Datos incorrectos",null);
                request.getSession().setAttribute("convocatoriaAct",null);
            }
            request.getSession().setAttribute("esActualizando", null);
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Menu de Opciones</title>");
            out.println("<link href='css/styles.css' rel='stylesheet'>");            
            out.println("</head>");
            out.println("<body>");
            if (CaException.getInstance().getDetalle() != null) {
                out.print(CaException.getInstance().getLastException());
                CaException.getInstance().setDetalle(null);
            }
            java.util.Date date = new java.util.Date();
            Date fechaAct = new Date(date.getTime());
            ConvocatoriaDAO convDAO = new ConvocatoriaDAO();
            EstadoDAO estDAO = new EstadoDAO();
            Convocatoria conv = convDAO.buscarConvocatoria(fechaAct);
            Estado est = estDAO.buscarEstado(fechaAct);
            if(conv!=null){
                request.getSession().setAttribute("periodo", conv.getK_periodo());
                if(request.getParameter("tipo2")!=null){
                    request.getSession().setAttribute("idEstado", Integer.parseInt(request.getParameter("tipo2")));
                }else{
                    request.getSession().setAttribute("idEstado", est.getIdestado());
                }
            }
            out.println("<h1>Menú</h1>");
            if(request.getSession().getAttribute("usuario")=="Estudiante"){
                if((request.getSession().getAttribute("idEstado")+"").equals("1")){
                    out.println("<FORM action='Servlet_Formulario' method='post'>");
                    out.println("<input name='Formulario' type='hidden' value='Llenar Formulario'>");
                    out.println("<input type='submit' value='Llenar Formulario' />");
                    out.println("</FORM>");
                    
                    out.println("<FORM action='Servlet_Formulario' method='post'>");
                    out.println("<input name='Formulario' type='hidden' value='Llenar Formulario'>");
                    out.println("<input name='actualizable' type='hidden' value='si'>");
                    out.println("<input type='submit' value='Actualizar Formulario' />");
                    out.println("</FORM>");
                }
                out.println("<FORM action='Servlet_Formulario' method='post'>");
                out.println("<input name='Formulario' type='hidden' value='Visualizar Formulario'>");
                out.println("<input type='submit' value='Visualizar Formulario' />");
                out.println("</FORM>");
            }
            if((request.getSession().getAttribute("usuario")=="Revisor")&&((request.getSession().getAttribute("idEstado")+"").equals("2"))){
                out.println("<FORM action='Servlet_VisualizarEstudiantes' method='post'>");
                out.println("<input type='submit' value='Visualizar Documentos Pendientes' style='width: 60%' />");
                out.println("</FORM>");
            }
            if(request.getSession().getAttribute("usuario")=="Administrador"){
                out.println("<h2>A continuación encontrará las opciones de la Convocatoria</h2>");
                out.println("<FORM action='Servlet_CrearConvocatoria' method='post'>");
                out.println("<input name='formularioMenu' type='submit' value='Actualizar Convocatoria'>");
                out.println("</FORM>");
                
                out.println("<FORM action='Servlet_CrearConvocatoria' method='post'>");
                out.println("<input name='formularioMenu' type='submit' value='Nueva Convocatoria'>");
                out.println("</FORM>");
                
                if(((request.getSession().getAttribute("idEstado")+"").equals("3"))||((request.getSession().getAttribute("idEstado")+"").equals("4"))){
                    out.println("<FORM action='Servlet_MostrarResultados' method='post'>");
                    out.println("<input name='mostrarResultados' type='submit' value='Mostrar Beneficiarios'>");
                    out.println("</FORM>");
                }
                if(((request.getSession().getAttribute("idEstado")+"").equals("2"))||((request.getSession().getAttribute("idEstado")+"").equals("3"))||((request.getSession().getAttribute("idEstado")+"").equals("4"))){
                    out.println("<FORM action='Servlet_Facultad' method='post'>");
                    out.println("<input name='mostFacSolicitudes' type='submit' value='Mostrar Solicitudes por Facultad' style='width: 70%;'>");
                    out.println("</FORM>");
                }
                if((request.getSession().getAttribute("idEstado")+"").equals("3")){
                    out.println("<FORM action='Servlet_Correo' method='post'>");
                    out.println("<input name='enviarCorroe' type='submit' value='Correo Beneficiarios'>");
                    out.println("</FORM>");
                }
                
                if((request.getSession().getAttribute("idEstado")+"").equals("3")){
                    try {
                        String runSP = "{call SAA.PK_GESTOR_ALMUERZO.P_OBTENER_SOLICITUDES(?,?)}";
                        Connection conn = ServiceLocator.getInstance().tomarConexion();
                        CallableStatement cs = conn.prepareCall(runSP);
                        cs.registerOutParameter(1, OracleTypes.VARCHAR);
                        cs.registerOutParameter(2, OracleTypes.NUMBER);    
                        cs.execute();
                        conn.commit();
                    } catch (SQLException e) {
                        CaException.getInstance().setDetalle(e);
                    }  finally {
                       ServiceLocator.getInstance().liberarConexion();
                    }
                    try {
                        String runSP = "{call SAA.PK_GESTOR_ALMUERZO.PR_TIPO(?)}";
                        Connection conn = ServiceLocator.getInstance().tomarConexion();
                        CallableStatement cs = conn.prepareCall(runSP);
                        cs.setString(1,request.getSession().getAttribute("periodo")+"");
                        cs.execute();
                        conn.commit();
                    } catch (SQLException e) {
                        CaException.getInstance().setDetalle(e);
                    }  finally {
                       ServiceLocator.getInstance().liberarConexion();
                    }
                }
            }
            out.println("<div class=\"container\">");
            out.println("<a href='formularioIngresar.jsp'><input type='button' value='Cerrar Sesion' class=\"cancelbtn\"></a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
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
        try {
            processRequest(request, response);
        } catch (RHException ex) {
            Logger.getLogger(Servlet_Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (RHException ex) {
            Logger.getLogger(Servlet_Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
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
