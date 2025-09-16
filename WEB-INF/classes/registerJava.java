import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.naming.spi.DirStateFactory;

public class registerJava extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String username=request.getParameter("name");
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

        // If a matching record is found, redirect the user to the Userpage.html page
        if (rs.next()) {
            out.println("<script>alert('You already signed up. Please login in');window.location.href='login.html';</script>");
        } else {
            // If no matching record is found, display an error message
            String sql1;
            sql1="insert into signup(username,email,password) values('"+username+"','"+email+"','"+password+"')";
            stmt.executeUpdate(sql1);
            out.println("<script>alert('Successfully Signed up!!!Please do login');window.location.href='login.html';</script>");
        }
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