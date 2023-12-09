package com.client.feignclient.service;

import com.client.feignclient.entity.Employee;
import com.client.feignclient.repository.EmployeeRepo;
import com.client.feignclient.response.EmployeeResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ModelMapper mapper;

    public EmployeeResponse getEmployeeById(int id) {
        Optional<Employee> optionalEmployee = employeeRepo.findById(id);

        // Extract the Employee object from Optional or provide a default value
        Employee employee = optionalEmployee.orElse(null);

        // Map the Employee object to EmployeeResponse
        EmployeeResponse employeeResponse = mapper.map(employee, EmployeeResponse.class);

        return employeeResponse;
    }
}
