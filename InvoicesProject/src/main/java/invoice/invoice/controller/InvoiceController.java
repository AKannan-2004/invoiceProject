package invoice.invoice.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import invoice.invoice.model.Invoice;
import invoice.invoice.model.InvoiceDetails;
import invoice.invoice.model.InvoiceHeader;
import invoice.invoice.service.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
@Tag(name = "Invoice", description = "Invoice Management APIs")
@RestController
@RequestMapping("/invoice")
//@CrossOrigin(origins = "http://localhost:3000")
public class InvoiceController {

	@Autowired

	InvoiceService invoiceSerivce;

	@PostMapping("/create")
	public ResponseEntity<@Valid Invoice> create(@Valid @RequestBody Invoice invoice) {

		invoiceSerivce.create(invoice);
		  return ResponseEntity.status(HttpStatus.CREATED).body(invoice);
	}

	@GetMapping("/getAll")
//	public ResponseEntity<List<Invoice>> getAll(){
//		   List<Invoice> invoices =invoiceSerivce.getAll();
//	        return ResponseEntity.ok(invoices);

	public List<Invoice> getAll() {
		return invoiceSerivce.getAll();
	}

	@PutMapping("update/{invoiceNo}")
	public Invoice updateById(@PathVariable("invoiceNo") long invoiceNo, @RequestBody Invoice invoice) {

		return invoiceSerivce.updateById(invoiceNo, invoice);
	}
	
	@Operation(summary = "Retrieve a Invoice using ID", description = "Get the Invoice using the passed ID as a path variable", tags= {"Invoice", "get"})
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = {
				@Content(schema=@Schema(implementation = Invoice.class),mediaType = "application/json")
			
		}),
		
		@ApiResponse(responseCode = "404", content = {
				@Content(schema = @Schema())}),
		
		@ApiResponse(responseCode = "500", content = {
				@Content(schema = @Schema())})
	})

	@GetMapping("get/{invoiceNo}")
	public Optional<Invoice> getByInvoiceNo(@PathVariable("invoiceNo") long invoiceNo) {
		return invoiceSerivce.getByInvoiceNo(invoiceNo);
	}

	@DeleteMapping("delete/{invoiceNo}")
	public void deletebyInvoiceNo(@PathVariable("invoiceNo") long invoice) {
		invoiceSerivce.deleteByInvoiceNo(invoice);
	}
	
	@GetMapping("get/{pageNo}/{pageSize}")
	public List<Invoice> getInvoicePagination(@PathVariable ("pageNo") int pageNo,@PathVariable ("pageSize") int pageSize){
		return invoiceSerivce.getInvoicePagination(pageNo, pageSize);
	}


}





//@RequestParam (defaultValue = "0") Integer pageNo,@RequestParam (defaultValue = "20") Integer pageSize,
