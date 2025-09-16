import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class UserDataServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost/Legal";
        String USER = "root";
        String PASS = "90006@Bhagi";
        Connection conn = null;
        Statement stmt = null;

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            
            String query = "SELECT email, score FROM signup WHERE email = '" + email + "'";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                String userEmail = rs.getString("email");
                int score = rs.getInt("score");

                out.print("{\"email\":\"" + userEmail + "\", \"score\":" + score + "}");
            } else {
                out.print("{\"error\":\"User not found\"}");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            se.printStackTrace();
            out.print("{\"error\":\"" + se.getMessage() + "\"}");
        } catch(Exception e) {
            e.printStackTrace();
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        } finally {
            try {
                if(stmt != null)
                    stmt.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
            try {
                if(conn != null)
                    conn.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
