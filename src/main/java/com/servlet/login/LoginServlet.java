package com.servlet.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // SQL query to check username and password
    private static final String LOGIN_QUERY = "SELECT * FROM user_credentials WHERE username=? AND password=?";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get PrintWriter
        PrintWriter pw = res.getWriter();
        // Set content type
        res.setContentType("text/html");
        // Read form values
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Create the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///aklantadb2", "root", "satu123#");
             PreparedStatement ps = con.prepareStatement(LOGIN_QUERY)) {

            // Set the values
            ps.setString(1, username);
            ps.setString(2, password);

            // Execute the query
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Create a new session
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
                res.sendRedirect("welcome.jsp");
            } else {
                pw.println("<h2>Invalid Username or Password</h2>");
                req.getRequestDispatcher("login.html").include(req, res);
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println(se.getMessage());
        }

        // Close the stream
        pw.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }
}
