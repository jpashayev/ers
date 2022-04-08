package com.revature.service;

import com.revature.dao.ReimbursementDAO;
import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.ResponseReimbursementDTO;
import com.revature.exception.IllegalStatusException;
import com.revature.exception.InvalidReceiptException;
import com.revature.exception.ReceiptNotFoundException;
import com.revature.model.Reimbursement;
import io.javalin.http.UnauthorizedResponse;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementService {

    public static Logger log = LoggerFactory.getLogger(Driver.class);

    private final ReimbursementDAO reimbursementDAO;

    public ReimbursementService() {
        this.reimbursementDAO = new ReimbursementDAO();
    }

    public ReimbursementService(ReimbursementDAO mockDAO) {
        this.reimbursementDAO = mockDAO;
    }

    public ResponseReimbursementDTO addReimbursement(int employeeId, AddReimbursementDTO dto) throws SQLException, InvalidReceiptException, IOException {

        Tika tika = new Tika();
        String mimeType = tika.detect(dto.getReceipt());

        if (!mimeType.equals("image/jpeg") && !mimeType.equals("image/png") && !mimeType.equals("image/gif")) {
            throw new InvalidReceiptException("receipt must be jpeg/png/gif");
        }
        Reimbursement reimbursement = this.reimbursementDAO.addReimbursement(employeeId, dto);

        log.info("\nAttempting to add Reimbursement [Service layer]\n");
        // create a new reimbursementDTO with the important details
        // add receipt when you figure it out
        // 5 fields are filled in by reimbursement
        // 2 fields "Status" & "Grader" are filled in by server
        return new ResponseReimbursementDTO(
                reimbursement.getId(), reimbursement.getAmount(), reimbursement.getSubmissionDate(), reimbursement.getResolutionDate(),
                reimbursement.getDescription(), reimbursement.getType(),1, reimbursement.getAuthorId().getUsername(),null);
    }

    public List<ResponseReimbursementDTO> getAllPendingReimbursements() throws SQLException {

        log.info("\nCall to getAllReimbursements from the Service Layer\n");
        List<Reimbursement> reimbursements = this.reimbursementDAO.getAllReimbursements();

        List<ResponseReimbursementDTO> reimbursementDTOs = new ArrayList<>();

        for (Reimbursement reimbursement : reimbursements) {
            if (reimbursement.getStatus() == 1) {
                reimbursementDTOs.add(
                        new ResponseReimbursementDTO(
                                reimbursement.getId(), reimbursement.getAmount(), reimbursement.getSubmissionDate(),
                                reimbursement.getType(), reimbursement.getStatus(),
                                reimbursement.getAuthorId().getUsername(), reimbursement.getResolvedId().getUsername()
                        ));
            }
        }

        log.info("\nReturning a list of Reimbursement DTOs\n");
        return reimbursementDTOs;
    }

    // Get All Reimbursements for Financial Manager
    public List<ResponseReimbursementDTO> getAllReimbursements() throws SQLException {

        log.info("\nCall to getAllReimbursements from the Service Layer\n");
        List<Reimbursement> reimbursements = this.reimbursementDAO.getAllReimbursements();

        List<ResponseReimbursementDTO> reimbursementDTOs = new ArrayList<>();

        for (Reimbursement reimbursement : reimbursements) {
            reimbursementDTOs.add(
                    new ResponseReimbursementDTO(
                            reimbursement.getId(), reimbursement.getAmount(), reimbursement.getSubmissionDate(),
                            reimbursement.getResolutionDate(), reimbursement.getDescription(), reimbursement.getType(), reimbursement.getStatus(),
                            reimbursement.getAuthorId().getUsername(), reimbursement.getResolvedId().getUsername()
                    ));
        }

        log.info("\nReturning a list of Reimbursement DTOs\n");
        return reimbursementDTOs;
    }

    public List<ResponseReimbursementDTO> getAllReimbursementsByUserId(int id) throws SQLException {

        log.info("\nCall to getAllReimbursementsByUserId from the Service Layer\n");
        List<Reimbursement> reimbursements = this.reimbursementDAO.getAllReimbursementsByUserId(id);

        List<ResponseReimbursementDTO> reimbursementDTOs = new ArrayList<>();

        for (Reimbursement reimbursement : reimbursements) {
            reimbursementDTOs.add(
                    new ResponseReimbursementDTO(
                            reimbursement.getId(), reimbursement.getAmount(), reimbursement.getSubmissionDate(),
                            reimbursement.getResolutionDate(), reimbursement.getDescription(), reimbursement.getType(), reimbursement.getStatus(),
                            reimbursement.getAuthorId().getUsername(), reimbursement.getResolvedId().getUsername()
                    ));
        }

        log.info("\nReturning a list of Reimbursement DTOs\n");
        return reimbursementDTOs;
    }

    public int getCurrentStatusByReimbursementId(String reimbursementId) throws SQLException {
            int intReimbursementId = Integer.parseInt(reimbursementId);
            return this.reimbursementDAO.getCurrentStatusByReimbursementId(intReimbursementId);
    }

    // Financial Manager response to the reimbursement
    public ResponseReimbursementDTO respondReimbursement(String reimbursementId, String status, int id) throws SQLException {

        try {
            int intReimbursementId = Integer.parseInt(reimbursementId);
            int intStatus = Integer.parseInt(status);

            if (intStatus > 3 || intStatus < 2) {
                throw new IllegalStatusException("Cannot update with a status other than 2 or 3 [Accepted / Denied]");
            }

            Reimbursement reimbursement = this.reimbursementDAO.answerReimbursement(intReimbursementId, intStatus, id);
            return new ResponseReimbursementDTO(
                    reimbursement.getId(), reimbursement.getAmount(), reimbursement.getSubmissionDate(),
                    reimbursement.getResolutionDate(), reimbursement.getDescription(), reimbursement.getType(), reimbursement.getStatus(),
                    reimbursement.getAuthorId().getUsername(), reimbursement.getResolvedId().getUsername()
                    );
        } catch (NumberFormatException e) {
            throw new UnauthorizedResponse("Reimbursement ID and Status must be integer values");
        } catch (IllegalStatusException e) {
            throw new UnauthorizedResponse("Status must be 2 or 3");
        }
    }

    public InputStream getReimbursementReceipt(String reimbId) throws SQLException, ReceiptNotFoundException {
        try {
            int rId = Integer.parseInt(reimbId);
            InputStream is = this.reimbursementDAO.getReimbursementReceipt(rId);

            if (is == null) {
                throw new ReceiptNotFoundException("There was no receipt");
            }

            return is;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Reimbursement id and or Author id must be an integer value");
        }
    }
}
