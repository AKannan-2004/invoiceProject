package invoice.invoice.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



import invoice.invoice.model.InvoiceHeader;

public interface HeaderRepository extends JpaRepository<InvoiceHeader, Long> {

	void save(Optional<InvoiceHeader> header);
	


}
