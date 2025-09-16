import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class blogJava extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/Legal";
    static final String USER = "root";
    static final String PASS = "90006@Bhagi";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String express = request.getParameter("express");

        Connection conn = null;
        PreparedStatement stmt = null;

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            if (username!= null && express!= null &&!username.isEmpty() &&!express.isEmpty()) {
                stmt = conn.prepareStatement("insert into discuss(username, express) values(?,?)");
                stmt.setString(1, username);
                stmt.setString(2, express);
                stmt.executeUpdate();
                out.println("<script>alert('Successfully posted!!');window.location.href='blog.html';</script>");
            } else {
                out.println("<script>alert('Enter username and text!!');window.location.href='blog.html';</script>");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            out.println("Error: " + se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (stmt!= null) {
                    stmt.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                if (conn!= null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName(JDBC_DRIVER);

            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();

            String sql = "SELECT username, express FROM discuss";
            ResultSet rs = stmt.executeQuery(sql);

            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Legal Awareness</title>");
            out.println("<link rel=\"icon\" href=\"logo.png\" type=\"image/x-icon\"/>");
            out.println("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'>");
            out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css' integrity='sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm' crossorigin='anonymous'>");
            out.println("<link rel='stylesheet' href='style.css'>");
            out.println("</head>");
            out.println("<body id='blogBody'>");
            out.println("<special-header></special-header>");
            out.println("<div class='container'>");
            out.println("<h1 id='blog-intro'>A place to share your experiences with any law or legal procedures. People will gain more understanding from other's experiences.</h1>");
            out.println("<div class='comment-section'>");
            out.println("<div class='add-comment'>");
            out.println("<form method='post' action='blog'>");
            out.println("<div class='comment-header my-3'>");
            out.println("<h3>Share your experience</h3>");
            out.println("<button>post</button>");
            out.println("</div>");
            out.println("<input type='text' id='usernameBlog' name='username' placeholder='username'><br>");
            out.println("<textarea id='express' name='express' placeholder='Express here...' rows=\"3\" cols=\"100\"></textarea>");
            out.println("</form>");
            out.println("</div>");
            out.println("</div>");           
            out.println("</div>");
            out.println("<div class='container my-3' style=\"padding: 10px;font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;\">");
            out.println("<h1 style=\"color:white;font-size:3em;font-weight:bold;\">Posts</h1>");
            out.println("<div class='display-comments my-3' style=\"padding: 10px;\">");
            while (rs.next()) {
                String user = rs.getString("username");
                String comment = rs.getString("express");
                out.println("<div class='comment' style=\"background: #def2f1;border-radius: 5px;\"><strong style=\"padding:10px\">" + user + " :</strong><br><span style=\"padding:10px\">" + comment + "</span></div>");
                out.println("<br>");
            }
            out.println("</div>");
            out.println("</div>");
            out.println("<script src='script.js'></script>");
            out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js'></script>");
            out.println("<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'></script>");
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException se) {
            se.printStackTrace();
            out.println("Error: " + se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }
}