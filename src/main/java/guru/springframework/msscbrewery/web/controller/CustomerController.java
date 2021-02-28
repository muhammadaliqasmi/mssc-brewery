package guru.springframework.msscbrewery.web.controller;

import com.sun.net.httpserver.Headers;
import guru.springframework.msscbrewery.services.CustomerService;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by Qasmi on 2021-02-27
 */
@RequestMapping("/api/v1/customer")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerId") UUID customerId){
        return new ResponseEntity<>(this.customerService.getById(customerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity handlePost(@RequestBody CustomerDto customerDto) {
        final CustomerDto saveDto = customerService.saveNewCustomer(customerDto);
        final Headers headers = new Headers();
        //todo - add hostname to the URL
        headers.add("location", "/api/v1/customer/" + saveDto.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity handlePut(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDto customerDto) {
        customerDto.setId(customerId);
        customerService.updateCustomer(customerId, customerDto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("customerId") UUID customerId) {
        customerService.deleteCustomer(customerId);
    }
}
