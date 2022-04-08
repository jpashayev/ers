package com.revature.dto;
import java.io.InputStream;
import java.util.Objects;

public class AddReimbursementDTO {

    private int id;
    private int amount;
    private int author;
    private int status;
    private int type;
    private String desc;
    private InputStream receipt;

    public AddReimbursementDTO() {
    }

    public AddReimbursementDTO(int id, int amount, int author,int status, int type) {
        this.id = id;
        this.amount = amount;
        this.author = author;
        this.status = status;
        this.type = type;
    }

    public AddReimbursementDTO(int id, int amount, int author, int status, int type, String desc, InputStream receipt) {
        this.id = id;
        this.amount = amount;
        this.author = author;
        this.status = status;
        this.type = type;
        this.desc = desc;
        this.receipt = receipt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
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

    @Override
    public String toString() {
        return "AddReimbursementDTO{" +
                "id=" + id +
                ", amount=" + amount +
                ", author=" + author +
                ", status=" + status +
                ", type=" + type +
                ", desc='" + desc + '\'' +
                ", receipt=" + receipt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddReimbursementDTO that = (AddReimbursementDTO) o;
        return id == that.id && amount == that.amount && author == that.author && status == that.status && type == that.type && Objects.equals(desc, that.desc) && Objects.equals(receipt, that.receipt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, author, status, type, desc, receipt);
    }
}
