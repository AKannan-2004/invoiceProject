package invoice.invoice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import invoice.invoice.model.InvoiceDetails;
import invoice.invoice.model.InvoiceHeader;

public interface DetailsRepository extends JpaRepository<InvoiceDetails, Long> {

	void save(List<InvoiceDetails> details);

	List<InvoiceDetails> findByInvoiceNo(long invoiceNo);

	void deleteByInvoiceNo(long invoiceNo);

}
