package invoice.invoice.model;

import java.time.LocalDateTime;
import java.util.Date;





import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;


@Entity
@Table(name = "invoiceHeader")
public class InvoiceHeader {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long invoiceNo;
	private Date date;
	private long totalAmount;
	private long totalDiscount;
	private long netAmount;
	private long cashAmount;
	private long cardAmount;
	private long creditAmount;

    
    private LocalDateTime createdAt;

    @PrePersist
    public void onPrePersist() {
        this.createdAt = LocalDateTime.now(); 
    }


	public long getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(long invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public long getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(long totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public long getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(long netAmount) {
		this.netAmount = netAmount;
	}

	public long getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(long cashAmount) {
		this.cashAmount = cashAmount;
	}

	public long getCardAmount() {
		return cardAmount;
	}

	public void setCardAmount(long cardAmount) {
		this.cardAmount = cardAmount;
	}

	public long getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(long creditAmount) {
		this.creditAmount = creditAmount;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	
	

}
