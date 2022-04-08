package com.revature.dao;

import com.revature.dto.AddReimbursementDTO;
import com.revature.main.Driver;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.utility.ConnectionUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDAO {
    public static Logger logger = LoggerFactory.getLogger(Driver.class);

    public List<Reimbursement> getAllReimbursements() throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            List<Reimbursement> reimbursements = new ArrayList<>();

            // check sql string still
            String sql = "SELECT reimbursement.id, reimb_amount, reimb_submitted, reimb_resolved, reimb_receipt, reimb_description, reimb_status, reimb_type, emp_user.id as emp_id, emp_user.username as emp_username, emp_user.user_email as emp_email, man_user.id as man_id, man_user.username as man_username, man_user.user_email as man_email " +
                    "FROM reimbursement " +
                    "LEFT JOIN users emp_user " +
                    "ON emp_user.id = reimbursement.reimb_author " +
                    "LEFT JOIN users man_user " +
                    "ON man_user.id = reimbursement.reimb_resolver";

            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    // Assignments
                    int reimbursementId = rs.getInt("id");
                    int amount = rs.getInt("reimb_amount");
                    Timestamp submissionDate = rs.getTimestamp("reimb_submitted");
                    Timestamp resolutionDate = rs.getTimestamp("reimb_resolved");
//                DONT FORGET ABOUT THE RECEIPT receipt
                    String description = rs.getString("reimb_description");
                    int status = rs.getInt("reimb_status");
                    int type = rs.getInt("reimb_type");

                    // Student User
                    int empId = rs.getInt("emp_id");
                    String empUser = rs.getString("emp_username");
                    String empEmail = rs.getString("emp_email");
                    String empRole = "Employee";

                    User Employee = new User(empId, empUser, empEmail, empRole);

                    // Trainer User
                    int manId = rs.getInt("man_id");
                    String manUser = rs.getString("man_username");
                    String manEmail = rs.getString("man_email");
                    String manRole = "Manager";

                    User Manager = new User(manId, manUser, manEmail, manRole);

                    Reimbursement a = new Reimbursement(reimbursementId, amount, submissionDate, resolutionDate, description, status, type, Employee, Manager);

                    reimbursements.add(a);
                }
                logger.info("\nReturn all Reimbursements [DAO Layer]\n");
                return reimbursements;
            }
        }
    }

    public List<Reimbursement> getAllReimbursementsByUserId(int userId) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            List<Reimbursement> reimbursements = new ArrayList<>();

            // check sql string still
            String sql = "SELECT reimbursement.id, reimb_amount, reimb_submitted, reimb_resolved, reimb_receipt, reimb_description, reimb_status, reimb_type, emp_user.id as emp_id, emp_user.username as emp_username, emp_user.user_email as emp_email, man_user.id as man_id, man_user.username as man_username, man_user.user_email as man_email " +
                    "FROM reimbursement " +
                    "LEFT JOIN users emp_user " +
                    "ON emp_user.id = reimbursement.reimb_author " +
                    "LEFT JOIN users man_user " +
                    "ON man_user.id = reimbursement.reimb_resolver " +
                    "WHERE reimbursement.reimb_author = ?";

            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, userId);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    // Assignments
                    int reimbursementId = rs.getInt("id");
                    int amount = rs.getInt("reimb_amount");
                    Timestamp submissionDate = rs.getTimestamp("reimb_submitted");
                    Timestamp resolutionDate = rs.getTimestamp("reimb_resolved");
//                DONT FORGET ABOUT THE RECEIPT receipt
                    String description = rs.getString("reimb_description");
                    int status = rs.getInt("reimb_status");
                    int type = rs.getInt("reimb_type");

                    // Student User
                    int empId = rs.getInt("emp_id");
                    String empUser = rs.getString("emp_username");
                    String empEmail = rs.getString("emp_email");
                    String empRole = "Employee";

                    User Employee = new User(empId, empUser, empEmail, empRole);

                    // Trainer User
                    int manId = rs.getInt("man_id");
                    String manUser = rs.getString("man_username");
                    String manEmail = rs.getString("man_email");
                    String manRole = "Manager";

                    User Manager = new User(manId, manUser, manEmail, manRole);

                    Reimbursement a = new Reimbursement(reimbursementId, amount, submissionDate, resolutionDate, description, status, type, Employee, Manager);

                    reimbursements.add(a);
                }
                logger.info("\nReturn all Reimbursements [DAO Layer]\n");
                return reimbursements;
            }
        }
    }

    public Reimbursement addReimbursement(int employeeId, AddReimbursementDTO dto) throws SQLException {

        try(Connection con = ConnectionUtility.getConnection()) {
            con.setAutoCommit(false);

            String sqlOne = "INSERT INTO reimbursement (reimb_amount, reimb_description, reimb_receipt, reimb_author, reimb_status, reimb_type) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement prepOne = con.prepareStatement(sqlOne, Statement.RETURN_GENERATED_KEYS)) {

                prepOne.setInt(1, dto.getAmount());
                prepOne.setString(2, dto.getDesc());
                prepOne.setBinaryStream(3, dto.getReceipt());
                prepOne.setInt(4, dto.getAuthor());
                prepOne.setInt(5, dto.getStatus());
                prepOne.setInt(6, dto.getType());
                prepOne.executeUpdate();

                ResultSet rs = prepOne.getGeneratedKeys();
                rs.next();
                int reimbursementId = rs.getInt("id");
                Timestamp submissionDate = rs.getTimestamp("reimb_submitted");

                String sqlTwo = "SELECT * FROM users WHERE id = ?";

                PreparedStatement prepTwo = con.prepareStatement(sqlTwo);
                prepTwo.setInt(1, employeeId);

                ResultSet rsTwo = prepTwo.executeQuery();
                rsTwo.next();

                int empId = rsTwo.getInt("id");
                String empUser = rsTwo.getString("username");
                String empEmail = rsTwo.getString("user_email");
                String role = "Employee";

                User employee = new User(empId, empUser, empEmail, role);

                Reimbursement reimbursement = new Reimbursement(reimbursementId, dto.getAmount(), submissionDate, null, dto.getDesc(), employee, null, 1, dto.getType());

                con.commit();
                logger.info("\nAdded a new Reimbursement [DAO Layer]\n");

                return reimbursement;
            }
        }
    }

    public Reimbursement answerReimbursement(int reimbursementId, int status, int id) throws SQLException {

        try (Connection con = ConnectionUtility.getConnection()) {
            con.setAutoCommit(false);
            String sql = "UPDATE reimbursement " +
                    "SET reimb_status = ?, " +
                    "reimb_resolver = ?, " +
                    "reimb_resolved = current_timestamp " +
                    "WHERE id = ?";

            PreparedStatement prep = con.prepareStatement(sql);
            prep.setInt(1, status);
            prep.setInt(2, id);
            prep.setInt(3, reimbursementId);

            prep.executeUpdate();

            String sql2 = "SELECT r.reimb_amount, r.reimb_submitted, r.reimb_resolved, r.reimb_type, r.reimb_status, emp.username as emp_user, emp.id as emp_id, emp.user_email as emp_email, manager.username as man_user, manager.id as man_id, manager.user_email as man_email " +
                    "FROM reimbursement r " +
                    "LEFT JOIN users emp " +
                    "ON emp.id = r.reimb_author " +
                    "LEFT JOIN users manager " +
                    "ON manager.id = r.reimb_resolver " +
                    "WHERE r.id = ?";

            PreparedStatement prep2 = con.prepareStatement(sql2);
            prep2.setInt(1, reimbursementId);
            ResultSet rs2 = prep2.executeQuery();
            rs2.next();

            int amount = rs2.getInt("reimb_amount");
            Timestamp submission = rs2.getTimestamp("reimb_submitted");
            Timestamp resolved = rs2.getTimestamp("reimb_resolved");
            int type = rs2.getInt("reimb_type");

            int empId = rs2.getInt("emp_id");
            String empUser = rs2.getString("emp_user");
            String empEmail = rs2.getString("emp_email");
            String empRole = "Employee";

            User employee = new User(empId, empUser, empEmail, empRole);

            int manId = rs2.getInt("man_id");
            String manUser = rs2.getString("man_user");
            String manEmail = rs2.getString("man_email");
            String manRole = "Manager";

            User manager = new User(manId, manUser, manEmail, manRole);

            Reimbursement reimbursement = new Reimbursement(reimbursementId, amount, submission, resolved, type, status, employee, manager);
            con.commit();
            logger.info("\nAnswer a new Reimbursement [DAO Layer]\n");

            return  reimbursement;
        }
    }

    public int getCurrentStatusByReimbursementId(int reimbursementId) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {

            String sql = "SELECT reimbursement.reimb_status " +
                    "FROM reimbursement " +
                    "WHERE reimbursement.id = ?";

            PreparedStatement prep = con.prepareStatement(sql);

            prep.setInt(1, reimbursementId);
            ResultSet rs = prep.executeQuery();
            rs.next();

            return rs.getInt("reimb_status");
        }
    }

    public InputStream getReimbursementReceipt(int rId) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "SELECT r.reimb_receipt " +
                    "FROM reimbursement r " +
                    "WHERE r.id = ?";

            PreparedStatement prep = con.prepareStatement(sql);

            prep.setInt(1, rId);

            ResultSet rs = prep.executeQuery();

            if (rs.next()) {
                InputStream is = rs.getBinaryStream("reimb_receipt");
                return is;
            } else {
                return null;
            }
        }
    }
}
