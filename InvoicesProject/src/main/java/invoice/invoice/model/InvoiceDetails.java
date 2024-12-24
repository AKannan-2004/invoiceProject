package invoice.invoice.model;

import java.util.Date;


import org.hibernate.annotations.AnyDiscriminator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "invoiceDetails")
public class InvoiceDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private long invoiceNo;

	private Date date;
	@Valid
	@NotNull(message="Item description should not be null")
	private String itemdescription;
	@Min(value=1,message = "Item rate must be greater than zero")
	private long itemRate;
	@Min(value=1,message = "Item qty must be greater than zero")
	private int itemQty;
	private int itemDiscount;
	private long netAmount;

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

	public String getItemdescription() {
		return itemdescription;
	}

	public void setItemdescription(String itemdescription) {
		this.itemdescription = itemdescription;
	}

	public long getItemRate() {
		return itemRate;
	}

	public void setItemRate(long itemRate) {
		this.itemRate = itemRate;
		calculateItemDiscountAndNetAmount();
	}

	public int getItemQty() {
		return itemQty;
	}

	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
		calculateItemDiscountAndNetAmount();
	}

	public int getItemDiscount() {
		return itemDiscount;
	}

	public void setItemDiscount(int itemDiscount) {
		this.itemDiscount = itemDiscount;
	}

	public long getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(long netAmount) {
		this.netAmount = netAmount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	private void calculateItemDiscountAndNetAmount() {
		this.itemDiscount=(int)(this.itemRate*0.10);
		this.netAmount=(this.itemRate - this.itemDiscount) * this.itemQty;
		
	}
	
	@PreUpdate
	@PrePersist
	private void onSaveOrUpdate() {
		calculateItemDiscountAndNetAmount();
	}

}
