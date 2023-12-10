package com.client.feignclient.service;

import com.client.feignclient.entity.Employee;
import com.client.feignclient.feignclient.AddressClient;
import com.client.feignclient.repository.EmployeeRepo;
import com.client.feignclient.response.AddressResponse;
import com.client.feignclient.response.EmployeeResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AddressClient addressClient;

    public EmployeeResponse getEmployeeById(int id) {
        Optional<Employee> optionalEmployee = employeeRepo.findById(id);

        // Check if the employee is present in the Optional
        if (optionalEmployee.isEmpty()) {
            // Handle the case where the employee is not found (throw an exception, return a default response, etc.)
            // For now, throwing an exception as an example
            throw new RuntimeException("Employee not found with id: " + id);
        }

        // Extract the Employee object from Optional
        Employee employee = optionalEmployee.get();

        // Map the Employee object to EmployeeResponse
        EmployeeResponse employeeResponse = mapper.map(employee, EmployeeResponse.class);

        // FeignClient
        ResponseEntity<AddressResponse> addressResponse = addressClient.getAddressByEmployeeId(id);

        // Check if the address response is successful before setting the address details
        if (addressResponse.getStatusCode().is2xxSuccessful()) {
            employeeResponse.setAddressResponse(addressResponse.getBody());
        } else {
            // Handle the case where fetching address failed (throw an exception, log a message, etc.)
            // For now, logging a message as an example
            System.out.println("Failed to fetch address for employee with id: " + id);
        }

        return employeeResponse;
    }
}
