import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class SaveScoreServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email"); // Assuming you set this during login
        int newScore = Integer.parseInt(request.getParameter("score"));
        
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost/Legal";
        String USER = "root";
        String PASS = "90006@Bhagi";
        Connection conn = null;
        Statement stmt = null;

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            
            // Fetch the current score from the database
            String query = "SELECT score FROM signup WHERE email = '" + email + "'";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                int currentScore = rs.getInt("score");

                // Update the score only if the new score is higher than the current score
                if (newScore > currentScore) {
                    String updateQuery = "UPDATE signup SET score = " + newScore + " WHERE email = '" + email + "'";
                    stmt.executeUpdate(updateQuery);
                    out.println("Score updated successfully");
                } else {
                    out.println("New score is not higher than the current score. No update made.");
                }
            } else {
                out.println("Email not found in the database.");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            se.printStackTrace();
            out.println("Error: " + se.getMessage());
        } catch(Exception e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
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
