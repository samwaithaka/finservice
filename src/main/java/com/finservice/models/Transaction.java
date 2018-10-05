package main.java.com.finservice.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	@Column(name = "amount", length=10)
    private int amount;
	@Column(name = "transaction_type", length=1)
    private String transactionType;
	@Column(name = "created_on")
    private Timestamp createdOn;
	@Column(name = "created_by", length=32)
    private String createdBy;
	@Column(name = "edited_on")
    private Timestamp editedOn;
	@Column(name = "edited_by", length=32)
    private String editedBy;
	
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
	
	public String getTransactionType() {
		return transactionType;
	}
	
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public Timestamp getEditedOn() {
		return editedOn;
	}
	
	public void setEditedOn(Timestamp editedOn) {
		this.editedOn = editedOn;
	}
	
	public String getEditedBy() {
		return editedBy;
	}
	
	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}
	
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", amount=" + amount + ", transactionType=" + transactionType + ", createdOn="
				+ createdOn + ", createdBy=" + createdBy + ", editedOn=" + editedOn + ", editedBy=" + editedBy + "]";
	}
}