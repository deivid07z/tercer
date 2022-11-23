/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Negocio.Solicitud;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oracle.jdbc.OracleTypes;
import util.CaException;
import util.Ingreso;
import util.ServiceLocator;

/**
 *
 * @author danie
 */
@WebServlet(name = "Servlet_MostrarResultados", urlPatterns = {"/Servlet_MostrarResultados"})
public class Servlet_MostrarResultados extends HttpServlet {

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
            throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Servlet_MostrarResultados</title>"); 
            out.println("<link href=\"css/table.css\" rel=\"stylesheet\">");
            out.println("<link href='css/styles_c.css' rel='stylesheet'>");  
            out.println("</head>");
            out.println("<body>");
            ArrayList sol = new ArrayList();
            try {
                String runSP;
                Connection conn = ServiceLocator.getInstance().tomarConexion();
                CallableStatement cs;
                if(request.getParameter("Formulario")!=null){
                    if(request.getParameter("Formulario").equals("Seleccionar Facultad")){
                        runSP = "{call SAA.PK_GESTOR_ADMINISTRATIVO.PR_LISTAR_SOLICITUDES_FACULTAD(?,?,?)}";
                        cs = conn.prepareCall(runSP);
                        cs.setString(1, (String) request.getSession().getAttribute("periodo"));
                        cs.setString(2, (String) request.getParameter("fac"));
                        cs.registerOutParameter(3, OracleTypes.CURSOR);
                        cs.execute();
                        ResultSet resultSet = (ResultSet) cs.getObject(3);
                        while (resultSet.next()) {
                            Long codigo = resultSet.getLong(1);
                            String periodo = resultSet.getString(5);
                            Integer puntaje = resultSet.getInt(3);
                            String tipo_apoyo = "";
                            sol.add(codigo);
                            sol.add(periodo);
                            sol.add(puntaje);
                            sol.add(tipo_apoyo);
                        }
                    }
                }else{
                    runSP = "{call SAA.PK_GESTOR_ADMINISTRATIVO.PR_LISTAR_BENEFICIARIOS(?,?)}";
                    cs = conn.prepareCall(runSP);
                    cs.setString(1, (String) request.getSession().getAttribute("periodo"));
                    cs.registerOutParameter(2, OracleTypes.CURSOR);    
                    cs.execute();
                    ResultSet resultSet = (ResultSet) cs.getObject(2);
                    while (resultSet.next()) {
                        Long codigo = resultSet.getLong(1);
                        String periodo = resultSet.getString(2);
                        Integer puntaje = resultSet.getInt(3);
                        String tipo_apoyo = resultSet.getString(4);
                        sol.add(codigo);
                        sol.add(periodo);
                        sol.add(puntaje);
                        sol.add(tipo_apoyo);
                    }
                }
                
                out.println("<table class=\"tg\">");
                out.println("<tr>");
                out.println("<th class=\"tg-0pky\">Código</th>");
                out.println("<th class=\"tg-0pky\">Periodo</th>");
                out.println("<th class=\"tg-0pky\">Puntaje</th>");
                out.println("<th class=\"tg-0pky\">Tipo Apoyo</th>");
                out.println("</tr>");
                out.println("</table>");
                
                out.println("<table class=\"tg\">");
                out.println("<tr>");
                for(int i=0;i<sol.size();i++){
                    
                    if((i+1)%4==1){
                        out.println("<th class=\"tg-0pky\">"+sol.get(i)+"</th>");
                    }
                    if((i+1)%4==2){
                        out.println("<th class=\"tg-0pky\">"+sol.get(i)+"</th>");
                    }
                    if((i+1)%4==3){
                        out.println("<th class=\"tg-0pky\">"+sol.get(i)+"</th>");
                    }
                    if((i+1)%4==0){
                        out.println("<th class=\"tg-0pky\">"+sol.get(i)+"</th>");
                        out.println("<tr>");
                    }
                    out.println("<br>");
                    
                }
                out.println("</tr>");
                out.println("</table>");
            } catch (SQLException e) {
                System.out.println(e);
                CaException.getInstance().setDetalle(e);
                 //throw new RHException( "ERROR", "ERROR "+ e.getMessage());
            }  finally {
               ServiceLocator.getInstance().liberarConexion();
            }
            out.println("<div class=\"container\">");
            out.println("<a href='Servlet_Menu'><input type='button' value='Volver' class=\"cancelbtn\"></a>");
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
