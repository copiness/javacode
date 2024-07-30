package com.servlet.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    // Create the query
    private static final String INSERT_QUERY = "INSERT INTO user_credentials(username, password, name, city, mobile, dob) VALUES(?,?,?,?,?,?)";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get PrintWriter
        PrintWriter pw = res.getWriter();
        // Set content type
        res.setContentType("text/html");

        // Read the form values
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String city = req.getParameter("city");
        String mobile = req.getParameter("mobile");
        String dob = req.getParameter("dob");

        // Load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Create the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///aklantadb2", "root", "satu123#");
             PreparedStatement ps = con.prepareStatement(INSERT_QUERY)) {
            // Set the values
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, name);
            ps.setString(4, city);
            ps.setString(5, mobile);
            ps.setString(6, dob);

            // Execute the query
            int count = ps.executeUpdate();

            if (count > 0) {
                // Registration successful, redirect to login page
                res.sendRedirect("login.html");
            } else {
                pw.println("Record not stored into database. <a href='register.html'>Try again</a>");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println(se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            pw.println(e.getMessage());
        }

        // Close the stream
        pw.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }
}
