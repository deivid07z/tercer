/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Negocio.Categoria;
import Negocio.Condicion;
import Negocio.Documento;
import Negocio.Solicitud;
import datos.CondicionDAO;
import datos.SolicitudDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.RHException;
import util.ServiceLocator;

/**
 *
 * @author Sebastian
 */
public class Servlet_Datos extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws util.RHException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, RHException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            CondicionDAO condDAO = new CondicionDAO();
            SolicitudDAO solDAO = new SolicitudDAO();
            Solicitud sol;
            Condicion cond; 
            Categoria cat;
            String cat2;
            ArrayList<Condicion> listaCondiciones = condDAO.buscarCondiciones();
            ArrayList<Documento> lsDoc = new ArrayList<Documento>();
            Documento doc = null;
            String usuario = null;
            for(int i=0;i<listaCondiciones.size();i++){
                cond = listaCondiciones.get(i);
                for(int j=0;j<cond.getListaCat().size();j++){
                    cat = cond.getListaCat().get(j);
                    if(cond.getPermiteVarios()==1){ 
                        cat2=request.getParameter(String.valueOf(cond.getK_idCondicion())+String.valueOf(cat.getK_idCategoria()));
                        if((cat2!=null)&&(cat2.equals(cat.getNombreCategoria()))){
                            usuario = String.valueOf(ServiceLocator.getInstance().getUsuario()).substring(2);
                            doc=new Documento(request.getParameter(String.valueOf(cond.getK_idCondicion())+String.valueOf(cat.getK_idCategoria())+"_doc"), 
                                    cat.getPfk_idCondicion(), cat.getK_idCategoria(), 
                                    request.getSession().getAttribute("periodo")+"", Long.parseLong(usuario));
                            lsDoc.add(doc);   
                        }
                    }else{
                        cat2=request.getParameter(String.valueOf(cond.getK_idCondicion()));
                        if((cat2!=null)&&(cat2.equals(cat.getNombreCategoria()))){
                            usuario = String.valueOf(ServiceLocator.getInstance().getUsuario()).substring(2);
                            doc=new Documento(request.getParameter(String.valueOf(cond.getK_idCondicion())+"_doc"), 
                                    cat.getPfk_idCondicion(), cat.getK_idCategoria(), 
                                    request.getSession().getAttribute("periodo")+"", Long.parseLong(usuario));
                            lsDoc.add(doc);  
                        }
                    }
                          
                }    
            }
            sol = new Solicitud(Long.parseLong(usuario), (Integer)request.getSession().getAttribute("idEstado"), 
                    request.getSession().getAttribute("periodo")+"", lsDoc);
            solDAO.incluirSolicitud(sol);
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link href='css/styles.css' rel='stylesheet'>");
            out.println("<title>Envio de la Convocatoria</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Datos ingresados al sistema satisfactoriamente.</h1>");
            out.println("<h2>Gracias por inscribirse al apoyo alimentario</h2>");
            out.println("<a href='Servlet_Menu'><input type='button' value='Regresar'></a>");
            out.println("");
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
        } catch (RHException | SQLException ex) {
            Logger.getLogger(Servlet_Datos.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (RHException | SQLException ex) {
            Logger.getLogger(Servlet_Datos.class.getName()).log(Level.SEVERE, null, ex);
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