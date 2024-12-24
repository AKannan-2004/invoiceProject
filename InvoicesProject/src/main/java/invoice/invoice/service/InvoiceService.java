package invoice.invoice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import invoice.invoice.model.Invoice;
import invoice.invoice.model.InvoiceDetails;
import invoice.invoice.model.InvoiceHeader;
import invoice.invoice.repository.DetailsRepository;
import invoice.invoice.repository.HeaderRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
@Service

public class InvoiceService {
	@Autowired
	HeaderRepository headerRepository;

	@Autowired
	DetailsRepository detailsRepository;

	// Create Method
	public Invoice create(Invoice invoice) {

		long totalAmount = invoice.calculateTotalAmount();
		long totalDiscount = invoice.calculateTotalDiscount();
		long netAmount = invoice.calculateNetAmount(invoice.getInvoiceDetails(),
				invoice.getInvoiceHeader().getCashAmount(), invoice.getInvoiceHeader().getCardAmount(),
				invoice.getInvoiceHeader().getCreditAmount());

		InvoiceHeader header = invoice.getInvoiceHeader();
		header.setTotalAmount(totalAmount);
		header.setTotalDiscount(totalDiscount);
		header.setNetAmount(netAmount);

		header = headerRepository.save(header);

		if (invoice.getInvoiceDetails() != null) {

			for (InvoiceDetails detail : invoice.getInvoiceDetails()) {

				detail.setInvoiceNo(header.getInvoiceNo());
				detailsRepository.save(detail);

			}
		}
		invoice.setInvoiceHeader(header);
		invoice.setInvoiceDetails(invoice.getInvoiceDetails());
		return invoice;

	}
	// Update Method
	@Transactional

	public Invoice updateById(long id, Invoice invoice ) {
		//long totalAmount=invoice.calculateTotalAmount();
		
		List<InvoiceDetails> invoiceDetailsList = invoice.getInvoiceDetails();
		Set<Long> updatedProductIds = invoiceDetailsList.stream()
	            .filter(detail -> detail.getId() != null)
	            .map(InvoiceDetails::getId)
	            .collect(Collectors.toSet());

	   	    List<InvoiceDetails> existingDetailsList = detailsRepository.findByInvoiceNo(id);

	    
	    List<InvoiceDetails> detailsToRemove = existingDetailsList.stream()
	            .filter(detail -> !updatedProductIds.contains(detail.getId()))
	            .collect(Collectors.toList());

	   
	    detailsRepository.deleteAll(detailsToRemove);

		for (InvoiceDetails invoiceDetails : invoiceDetailsList) {
			if (invoiceDetails.getId() != null) {
				Optional<InvoiceDetails> existingDetails = detailsRepository.findById(invoiceDetails.getId());

				if (existingDetails.isPresent()) {
					InvoiceDetails detailToUpdate = existingDetails.get();
					detailToUpdate.setDate(invoiceDetails.getDate());
					detailToUpdate.setItemdescription(invoiceDetails.getItemdescription());
					detailToUpdate.setItemDiscount(invoiceDetails.getItemDiscount());
					detailToUpdate.setItemQty(invoiceDetails.getItemQty());
					detailToUpdate.setItemRate(invoiceDetails.getItemRate());
					detailToUpdate.setNetAmount(invoiceDetails.getNetAmount());
					
					
					
					detailsRepository.save(detailToUpdate);
				} else {
					InvoiceDetails newDetail = new InvoiceDetails();
					newDetail.setInvoiceNo(id);
					newDetail.setDate(invoiceDetails.getDate());
					newDetail.setItemdescription(invoiceDetails.getItemdescription());
					newDetail.setItemDiscount(invoiceDetails.getItemDiscount());
					newDetail.setItemQty(invoiceDetails.getItemQty());
					newDetail.setItemRate(invoiceDetails.getItemRate());
					newDetail.setNetAmount(invoiceDetails.getNetAmount());

					detailsRepository.save(newDetail);
				}
			} else {
				InvoiceDetails newDetail = new InvoiceDetails();
				newDetail.setInvoiceNo(id);
				newDetail.setDate(invoiceDetails.getDate());
				newDetail.setItemdescription(invoiceDetails.getItemdescription());
				newDetail.setItemDiscount(invoiceDetails.getItemDiscount());
				newDetail.setItemQty(invoiceDetails.getItemQty());
				newDetail.setItemRate(invoiceDetails.getItemRate());
				newDetail.setNetAmount(invoiceDetails.getNetAmount());

				detailsRepository.save(newDetail);
			}
		}

		Optional<InvoiceHeader> header = headerRepository.findById(id);
		if (header.isPresent()) {
			InvoiceHeader headers = header.get();
			headers.setDate(invoice.getInvoiceHeader().getDate());
			headers.setTotalAmount(invoice.getInvoiceHeader().getTotalAmount());
			headers.setTotalDiscount(invoice.getInvoiceHeader().getTotalDiscount());
			headers.setCardAmount(invoice.getInvoiceHeader().getCardAmount());
			headers.setCashAmount(invoice.getInvoiceHeader().getCashAmount());
			headers.setCreditAmount(invoice.getInvoiceHeader().getCreditAmount());
			headers.setNetAmount(invoice.getInvoiceHeader().getNetAmount());
			headers.setCreatedAt(invoice.getInvoiceHeader().getCreatedAt());
			headers.setTotalAmount(invoice.calculateTotalAmount());
			headers.setTotalDiscount(invoice.calculateTotalDiscount());
			headers.setNetAmount(invoice.calculateNetAmount(invoiceDetailsList, id, id, id));

			headerRepository.save(headers);
		}
		
	     
	
		

		return invoice;
	}

	// GetAll Method

	public List<Invoice> getAll() {
		List<InvoiceHeader> headers = headerRepository.findAll();
		List<Invoice> invoices = new ArrayList<>();

		for (InvoiceHeader header : headers) {
			List<InvoiceDetails> details = detailsRepository.findByInvoiceNo(header.getInvoiceNo());
			Invoice invoice = new Invoice();
			invoice.setInvoiceHeader(header);
			invoice.setInvoiceDetails(details);
			invoices.add(invoice);
		}

		return invoices;
	}

	// GetByInvoiceNo Method

	public Optional<Invoice> getByInvoiceNo(long invoiceNo) {
		Optional<InvoiceHeader> header = headerRepository.findById(invoiceNo);
		if (header.isPresent()) {

			List<InvoiceDetails> details = detailsRepository.findByInvoiceNo(invoiceNo);
			Invoice invoice = new Invoice();
			invoice.setInvoiceHeader(header.get());
			invoice.setInvoiceDetails(details);
			return Optional.of(invoice);
		} else {
			return Optional.empty();
		}
	}

	// Delete Method
	@Transactional
	public void deleteByInvoiceNo(long invoiceNo) {

		Optional<InvoiceHeader> header = headerRepository.findById(invoiceNo);
		if (header.isPresent()) {

			detailsRepository.deleteByInvoiceNo(invoiceNo);

			headerRepository.delete(header.get());
		}
	}
	
	//Inovoice Pagination
	
	public List<Invoice> getInvoicePagination(int pageNo, int pageSize){
		PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Order.desc("createdAt")));

		Page<InvoiceHeader> page= headerRepository.findAll(pageRequest);
		List<Invoice> invoices= new ArrayList<>();
	
		 
		for(InvoiceHeader header :page.getContent() ) {
		List<InvoiceDetails> details = detailsRepository.findByInvoiceNo(header.getInvoiceNo());
				Invoice invoice = new Invoice();
				invoice.setInvoiceHeader(header);
				invoice.setInvoiceDetails(details);
				invoices.add(invoice);
			}
		return invoices;
	}
		
		  
		
	}


