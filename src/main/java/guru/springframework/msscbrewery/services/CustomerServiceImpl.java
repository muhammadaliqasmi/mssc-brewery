package guru.springframework.msscbrewery.services;

import guru.springframework.msscbrewery.web.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by qasmi on 2021-02-27.
 */
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Override
    public CustomerDto getById(UUID customerId) {
        return CustomerDto.builder() //
            .id(customerId) //
            .name("Customer Name") //
            .build();
    }

    @Override
    public CustomerDto saveNewCustomer(CustomerDto customerDto) {
        return CustomerDto.builder() //
                .id(UUID.randomUUID()) //
                .build();
    }

    @Override
    public void updateCustomer(UUID customerId, CustomerDto customerDto) {
        // todo - would add a real implementation to update customer
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        log.debug("Deleting a customer...");
    }

}
