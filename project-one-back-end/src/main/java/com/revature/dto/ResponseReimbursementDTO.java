package com.revature.dto;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Objects;

public class ResponseReimbursementDTO {

    // reimbursement variables
    private int id;
    private int amount;
    private Timestamp submissionDate;
    private Timestamp resolutionDate;
    private String description;
    private InputStream receipt;
    private int type;
    private int status;

    // user variables
    private String username;
    private String resolver;

    public ResponseReimbursementDTO() {
    }

    public ResponseReimbursementDTO(int id, int amount, Timestamp submissionDate, Timestamp resolutionDate, String description, InputStream receipt, int type, int status, String username, String resolver) {
        this.id = id;
        this.amount = amount;
        this.submissionDate = submissionDate;
        this.resolutionDate = resolutionDate;
        this.description = description;
        this.receipt = receipt;
        this.type = type;
        this.status = status;
        this.username = username;
        this.resolver = resolver;
    }

    public ResponseReimbursementDTO(int id, int amount, Timestamp submissionDate, int type, int status, String username, String resolver) {
        this.id = id;
        this.amount = amount;
        this.submissionDate = submissionDate;
        this.type = type;
        this.status = status;
        this.username = username;
        this.resolver = resolver;
    }

    public ResponseReimbursementDTO(int id, int amount, Timestamp submissionDate, Timestamp resolutionDate, String description, int type, int status, String username, String resolver) {
        this.id = id;
        this.amount = amount;
        this.submissionDate = submissionDate;
        this.resolutionDate = resolutionDate;
        this.description = description;
        this.type = type;
        this.status = status;
        this.username = username;
        this.resolver = resolver;
    }

    public ResponseReimbursementDTO(int id, int amount, Timestamp submissionDate, String description, int type, int status, String username, String resolver) {
        this.id = id;
        this.amount = amount;
        this.submissionDate = submissionDate;
        this.description = description;
        this.type = type;
        this.status = status;
        this.username = username;
        this.resolver = resolver;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Timestamp submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getResolver() {
        return resolver;
    }

    public void setResolver(String resolver) {
        this.resolver = resolver;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Timestamp getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(Timestamp resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InputStream getReceipt() {
        return receipt;
    }

    public void setReceipt(InputStream receipt) {
        this.receipt = receipt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseReimbursementDTO that = (ResponseReimbursementDTO) o;
        return id == that.id && amount == that.amount && type == that.type && status == that.status && Objects.equals(submissionDate, that.submissionDate) && Objects.equals(resolutionDate, that.resolutionDate) && Objects.equals(description, that.description) && Objects.equals(receipt, that.receipt) && Objects.equals(username, that.username) && Objects.equals(resolver, that.resolver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, submissionDate, resolutionDate, description, receipt, type, status, username, resolver);
    }

    @Override
    public String toString() {
        return "ResponseReimbursementDTO{" +
                "id=" + id +
                ", amount=" + amount +
                ", submissionDate=" + submissionDate +
                ", resolutionDate=" + resolutionDate +
                ", description='" + description + '\'' +
                ", receipt=" + receipt +
                ", type=" + type +
                ", status=" + status +
                ", username='" + username + '\'' +
                ", resolver='" + resolver + '\'' +
                '}';
    }
}
