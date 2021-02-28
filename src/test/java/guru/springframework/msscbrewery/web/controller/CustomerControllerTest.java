package guru.springframework.msscbrewery.web.controller;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewery.services.CustomerService;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    CustomerDto validCustomer;

    @Before
    public void setup() {
        validCustomer = CustomerDto.builder() //
                .id(UUID.randomUUID()) //
                .name("John") //
                .build();
    }

    @Test
    public void shouldGetCustomer() throws Exception {
        // given
        given(customerService.getById(any(UUID.class))).willReturn(validCustomer);

        // when
        mockMvc.perform(get("/api/v1/customer/" + validCustomer.getId().toString())
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.id", is(validCustomer.getId().toString())))
        .andExpect(jsonPath("$.name", is(validCustomer.getName())));
    }

    @Test
    public void shouldHandlePost() throws Exception {
        // given
        CustomerDto customerDto = validCustomer;
        customerDto.setId(null);
        CustomerDto saveDto = CustomerDto.builder().id(UUID.randomUUID()).name("New Customer").build();
        String customerDtoJson = objectMapper.writeValueAsString(customerDto);

        given(customerService.saveNewCustomer(any())).willReturn(saveDto);

        // when
        mockMvc.perform(post("/api/v1/customer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldHandleUpdate() throws  Exception {
        // given
        CustomerDto customerDto = validCustomer;
        String customerDtoString = objectMapper.writeValueAsString(customerDto);

        // when
        mockMvc.perform(put("/api/v1/customer/" + validCustomer.getId().toString())
        .contentType(MediaType.APPLICATION_JSON)
        .content(customerDtoString))
        .andExpect(status().isNoContent());

        then(customerService).should().updateCustomer(any(),any());
    }

    @Test
    public void shouldDeleteCustomer() throws  Exception {
        mockMvc.perform(delete("/api/v1/customer/" + validCustomer.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
