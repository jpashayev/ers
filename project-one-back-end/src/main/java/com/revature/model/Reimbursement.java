package com.revature.model;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Objects;

public class Reimbursement {

    private int id;
    private int amount;
    private Timestamp submissionDate;
    private Timestamp resolutionDate;
    private String description;
    private InputStream receipt;

    private User authorId;
    private User resolvedId;
    private int status;
    private int type;

    public Reimbursement() {
    }

    public Reimbursement(int id, int amount, Timestamp submissionDate, Timestamp resolutionDate, String description, User authorId, User resolvedId, int status, int type) {
        this.id = id;
        this.amount = amount;
        this.submissionDate = submissionDate;
        this.resolutionDate = resolutionDate;
        this.description = description;
        this.authorId = authorId;
        this.resolvedId = resolvedId;
        this.status = status;
        this.type = type;
    }

    // resolution date + resolved manager id constructor
    public Reimbursement(int id, int amount, Timestamp submissionDate, Timestamp resolutionDate, int type, int status, User authorId, User resolvedId) {
        this.id = id;
        this.amount = amount;
        this.submissionDate = submissionDate;
        this.resolutionDate = resolutionDate;
        this.authorId = authorId;
        this.resolvedId = resolvedId;
        this.status = status;
        this.type = type;
    }

    // add a reimbursement constructor
    public Reimbursement(int reimbursementId, int amount, Timestamp submissionDate, int status, int type, User authorId, User resolverId) {
        this.id = reimbursementId;
        this.amount = amount;
        this.submissionDate = submissionDate;
        this.status = status;
        this.type = type;
        this.authorId = authorId;
        this.resolvedId = resolverId;
    }

    public Reimbursement(int reimbursementId, int amount, Timestamp submissionDate, Timestamp resolutionDate, String description,  int status, int type, User authorId, User resolverId) {
        this.id = reimbursementId;
        this.amount = amount;
        this.submissionDate = submissionDate;
        this.resolutionDate = resolutionDate;
        this.description = description;
        this.status = status;
        this.type = type;
        this.authorId = authorId;
        this.resolvedId = resolverId;
    }

    public InputStream getReceipt() {
        return receipt;
    }

    public void setReceipt(InputStream receipt) {
        this.receipt = receipt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Timestamp getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Timestamp submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Timestamp getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(Timestamp resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthorId() {
        return authorId;
    }

    public void setAuthorId(User authorId) {
        this.authorId = authorId;
    }

    public User getResolvedId() {
        return resolvedId;
    }

    public void setResolvedId(User resolvedId) {
        this.resolvedId = resolvedId;
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

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id=" + id +
                ", amount=" + amount +
                ", submissionDate=" + submissionDate +
                ", resolutionDate=" + resolutionDate +
                ", description='" + description + '\'' +
                ", receipt=" + receipt +
                ", authorId=" + authorId +
                ", resolvedId=" + resolvedId +
                ", status=" + status +
                ", type=" + type +
                '}';
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reimbursement that = (Reimbursement) o;
        return id == that.id && amount == that.amount && status == that.status && type == that.type && Objects.equals(submissionDate, that.submissionDate) && Objects.equals(resolutionDate, that.resolutionDate) && Objects.equals(description, that.description) && Objects.equals(receipt, that.receipt) && Objects.equals(authorId, that.authorId) && Objects.equals(resolvedId, that.resolvedId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, submissionDate, resolutionDate, description, receipt, authorId, resolvedId, status, type);
    }
}
