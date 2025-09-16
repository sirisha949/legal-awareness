import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.naming.spi.DirStateFactory;

public class ResetServlet extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String email=request.getParameter("email");
        String password=request.getParameter("password");
        String JDBC_DRIVER="com.mysql.jdbc.Driver";
        String DB_URL="jdbc:mysql://localhost/Legal";
        String USER="root";
        String PASS="90006@Bhagi";
        Connection conn=null;
        Statement stmt=null;
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try{
        Class.forName(JDBC_DRIVER);
        conn=DriverManager.getConnection(DB_URL,USER,PASS);
        stmt=conn.createStatement();
        String sql = "SELECT * FROM signup WHERE email = '" + email + "'";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            sql = "UPDATE signup SET password = '"+password+"' WHERE email = '"+email+"'";
            stmt.executeUpdate(sql);
            out.println("<script>alert('Sucessfully Updated!!');window.location.href='login.html';</script>");
        } else {
            out.println("<script>alert('Invalid email');window.location.href='reset.html';</script>");
        }
        rs.close();
        stmt.close();
        conn.close();
        }
        catch(SQLException se){
        se.printStackTrace();
        out.println("Error: "+se.getMessage());
        }
        catch(Exception e){
        e.printStackTrace();
        out.println("Error: "+e.getMessage());
        }
        finally{
        try{
        if (stmt!=null)
        stmt.close();
        }
        catch(SQLException se){
        se.printStackTrace();
        }
        try{
        if(conn!=null)
        conn.close();
        }
        catch(Exception e){
        e.printStackTrace();
        }}
    }
}