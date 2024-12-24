package invoice.invoice.model;

import java.util.List;
import java.util.Optional;

public class Invoice {

	private InvoiceHeader invoiceHeader;
	private List<InvoiceDetails> invoiceDetails;

	public InvoiceHeader getInvoiceHeader() {
		return invoiceHeader;
	}

	public void setInvoiceHeader(InvoiceHeader invoiceHeader) {
		this.invoiceHeader = invoiceHeader;
	}

	public List<InvoiceDetails> getInvoiceDetails() {
		return invoiceDetails;
	}

	public void setInvoiceDetails(List<InvoiceDetails> invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}

	public long calculateTotalAmount() {
		long totalAmount = 0;
		for (InvoiceDetails detail : invoiceDetails) {
			totalAmount += detail.getItemRate() * detail.getItemQty();
		}
		return totalAmount;

	}

	public long calculateTotalDiscount() {
		long totalDiscount = 0;
		for (InvoiceDetails detail : invoiceDetails) {
			totalDiscount += detail.getItemDiscount();
		}
		return totalDiscount;
	}

	public long calculateNetAmount(List<InvoiceDetails> list, long l, long m, long n) {
		long totalAmount = 0;
		long totalDiscount = 0;
		long cashAmount = 0;
		long cardAmount = 0;
		long creditAmount = 0;

		for (InvoiceDetails detail : invoiceDetails) {
			totalAmount += detail.getItemRate() * detail.getItemQty();
			totalDiscount += detail.getItemDiscount();
		}

		long netAmount = totalAmount - totalDiscount;
		long calculatedNetAmount = cashAmount + cardAmount + creditAmount;

		if (netAmount != calculatedNetAmount) {

			cashAmount = netAmount - (cardAmount + creditAmount);
		}

		return netAmount;
	}

}
