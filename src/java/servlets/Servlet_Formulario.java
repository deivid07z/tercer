/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Negocio.Categoria;
import Negocio.Condicion;
import Negocio.Documento;
import Negocio.Revisor;
import Negocio.Solicitud;
import datos.BeneficiarioDAO;
import datos.CategoriaDAO;
import datos.CondicionDAO;
import datos.SolicitudDAO;
import datos.RevisorDAO;
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
import util.CaException;
import util.RHException;
import util.ServiceLocator;

/**
 *
 * @author Sebastian
 */
public class Servlet_Formulario extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            //   Estudiante 
            SolicitudDAO solDAO = new SolicitudDAO();
            CondicionDAO condDAO = new CondicionDAO();
            ArrayList<Condicion> listaCondiciones = condDAO.buscarCondiciones();
            Solicitud solc;
            Condicion cond;
            Categoria cat;
            String estado;
            String usuario;
            estado = request.getParameter("Formulario");
            String nameSubmit="";
            String var = "";
            Boolean documento = true;
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
            out.println("<link href='css/styles_c.css' rel='stylesheet'>");
            out.println("<title>Formulario del Apoyo</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Formulario del Apoyo Alimentario</h1>");
            if(estado.equals("Llenar Formulario")){
                if((request.getParameter("actualizable")!=null)&&(request.getParameter("actualizable").equals("si"))){
                    try {
                        solDAO.eliminarSolicitud(Long.parseLong(String.valueOf(ServiceLocator.getInstance().getUsuario()).substring(2)),
                                request.getSession().getAttribute("periodo")+"");
                    } catch (SQLException ex) {
                        Logger.getLogger(Servlet_Formulario.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                var = "Servlet_Datos";
            }else if(estado.equals("Visualizar Formulario")){
                var = "Servlet_Menu";
            }else{
                var = "Servlet_UpdateEstado";
            }
            out.println("<FORM action='" + var+ "' method='post'>");
            if(CaException.getInstance().getDetalle()!=null){
                out.print(CaException.getInstance().getLastException());
                CaException.getInstance().setDetalle(null);
            }
            int k=0;
            int l=0;
            ArrayList puntajes = null;
            for(int i=0;i<listaCondiciones.size();i++){
                cond = listaCondiciones.get(i);
                out.println("<hr/>");
                out.println("<h4>"+cond.getDescripcion()+"</h4>");
                out.println("<hr/>");
                if(estado.equals("Llenar Formulario")){
                    for(int j=0;j<cond.getListaCat().size();j++){
                        cat = cond.getListaCat().get(j);
                        if(cond.getPermiteVarios()==1){ //CHECKBOX O RADIO
                            out.println("<input type='checkbox' name='"+cond.getK_idCondicion()+cat.getK_idCategoria()+"' value='"+cat.getNombreCategoria()+"'> "+cat.getNombreCategoria()+"&nbsp");
                            out.println("<input name='"+cond.getK_idCondicion()+cat.getK_idCategoria()+"_doc' type='file' /><br>");
                        }else{
                            if(documento){
                                out.println("<input name='"+cond.getK_idCondicion()+"_doc' type='file' /><br>");
                                documento=false;
                            }
                            out.println("<input type='radio' name='"+cond.getK_idCondicion()+"' value='"+cat.getNombreCategoria()+"' required> "+cat.getNombreCategoria()+"<br>");
                        }
                    }
                    nameSubmit = "Enviar Convocatoria";
                }else if(estado.equals("Visualizar Formulario")){
                    usuario = String.valueOf(ServiceLocator.getInstance().getUsuario()).substring(2);
                    solc = solDAO.buscarSolicitud(Long.parseLong(usuario),request.getSession().getAttribute("periodo")+"");
                    ArrayList<Documento> lsDoc;
                    if(solc!=null){
                        lsDoc = solc.getListDocumento();
                    }else{
                        lsDoc = new ArrayList<Documento>();
                    }
                    RevisorDAO revDao = new RevisorDAO();
                    CategoriaDAO catDao = new CategoriaDAO();
                    if((i==0)&&
                            ((request.getSession().getAttribute("idEstado")+"").equals("3")||
                            ((request.getSession().getAttribute("idEstado")+"").equals("4")))
                            ){
                        BeneficiarioDAO benDAO = new BeneficiarioDAO();
                        
                        out.println("<h31>El puntaje total es: </h31>"+benDAO.puntajeSolicitud(Long.parseLong(usuario),request.getSession().getAttribute("periodo")+"")+"<br>");
                        out.println("<hr/>");
                        if(benDAO.beneficiario(Long.parseLong(usuario), request.getSession().getAttribute("periodo")+""))
                            out.println("<h31>El estudiante fue aceptado en la convocatoria</h31><br><hr/>");
                        else
                            out.println("<h31>El estudiante NO fue aceptado en la convocatoria</h31><br><hr/>");
                        puntajes = benDAO.puntajeDocumento(Long.parseLong(usuario), request.getSession().getAttribute("periodo")+"");
                    }
                    while (lsDoc.size()>l) {
                        if(lsDoc.get(l).getFk_idCondicion() == cond.getK_idCondicion()){
                            Revisor rvs = revDao.buscarRevisor(lsDoc.get(l).getFk_codRevisor());
                            cat = catDao.buscarCategoria(lsDoc.get(l).getPfk_idCategoria(),lsDoc.get(l).getFk_idCondicion());
                            String estDoc = "";
                            if(lsDoc.get(l).getEstado().equals("CK")){
                                estDoc = "PARA REVISAR";
                            }else if(lsDoc.get(l).getEstado().equals("AP")){
                                estDoc = "APROBADO";
                            }else if(lsDoc.get(l).getEstado().equals("RP")){
                                estDoc = "REPROBADO";
                            }
                            if(rvs!=null){
                                if(estDoc.equals("REPROBADO")){
                                    puntajes.set(k,0);
                                }
                                out.println("<hr/>");
                                out.println(cat.getNombreCategoria() + "<h3> Documento </h3><br>" + lsDoc.get(l).getDocumento() + "<h3> Estado </h3><br>" + estDoc + "<h3> Revisor </h3><br>" + rvs.getNombreRevisor()+"<h3> Puntaje </h3><br>"+puntajes.get(k)+"<br>");
                                k++;
                            }else{
                                out.println(cat.getNombreCategoria() + " Documento " + lsDoc.get(l).getDocumento() + " Estado " + estDoc + " Revisor En Asignacion</br>");
                            }
                            out.println("<a href='http://localhost:8084/SAA/"+lsDoc.get(l).getDocumento()+"'><input type='button' value='Ver Documento'></a></br>");
                            //out.println("<p align='center'><iframe src='http://docs.google.com/gview?url=http://localhost:8084/SAA/"+lsDoc.get(l).getDocumento()+"&amp;embedded=true' frameborder='0' width='600px' height='830px' mce_src='http://docs.google.com/gview?url=http://www.miweb.com/descargas/docu.pdf&amp;embedded=true' style='align: center; width: 600px; height: 830px;'></iframe></p>");
                        }
                        l++;
                    }
                    nameSubmit = "Regresar";
                }else{
                    usuario = String.valueOf(ServiceLocator.getInstance().getUsuario()).substring(2);
                    solc = solDAO.buscarSolicitud(Long.parseLong(request.getParameter("code")),request.getSession().getAttribute("periodo")+"");
                    ArrayList<Documento> lsDoc = solc.getListDocumento();
                    RevisorDAO revDao = new RevisorDAO();
                    CategoriaDAO catDao = new CategoriaDAO();
                    while (lsDoc.size()>l) {
                        if(lsDoc.get(l).getFk_idCondicion() == cond.getK_idCondicion()){
                            Revisor rvs = revDao.buscarRevisor(Long.parseLong(usuario));
                            cat = catDao.buscarCategoria(lsDoc.get(l).getPfk_idCategoria(),lsDoc.get(l).getFk_idCondicion());
                            out.println(cat.getNombreCategoria() + " Documento " + lsDoc.get(l).getDocumento() + " Revisor " + rvs.getNombreRevisor()+"</br>");
                            out.println("<a href='http://localhost:8084/SAA/"+lsDoc.get(l).getDocumento()+"'><input type='button' value='Ver Documento'></a></br>");
                            out.println("<input type='radio' name='"+cond.getK_idCondicion()+cat.getK_idCategoria()+"' value='AP' required>APROBADO<br>");
                            out.println("<input type='radio' name='"+cond.getK_idCondicion()+cat.getK_idCategoria()+"' value='RP' required>REPROBADO<br>");
                        }
                        l++;
                    }
                    out.println("<input name='code' type='hidden' value='"+request.getParameter("code")+"'>");
                    nameSubmit = "Actualizar";
                }
                documento=true;
                l=0;
            }
            out.println("<input type='submit' value='"+nameSubmit+"' />");
            out.println("</FORM>");
           
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
            Logger.getLogger(Servlet_Formulario.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Servlet_Formulario.class.getName()).log(Level.SEVERE, null, ex);
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
