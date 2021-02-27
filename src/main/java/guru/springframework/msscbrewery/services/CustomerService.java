package guru.springframework.msscbrewery.services;

import guru.springframework.msscbrewery.web.model.CustomerDto;

import java.util.UUID;

/**
 * Created by qasmi on 2021-02-27.
 */
public interface CustomerService {
    CustomerDto getById(UUID customerId);
}
