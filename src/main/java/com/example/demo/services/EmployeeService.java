/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.services;

import com.example.demo.models.Employee;
import com.example.demo.repositories.EmployeeH2Repository;
import com.example.demo.repositories.EmployeeListRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeH2Repository repository;
//    @Autowired
//    private EmployeeListRepository repository;
    
    
    public long getNextID() {
        return repository.getNextID();
    }
    
    public List<Employee> getAll() {
        return repository.getAll();
    }
    
    public Employee getByID(long id) {
        return repository.getByID(id);
    }
    
    public long add(Employee employee){
        return repository.add(employee);
    }
    
    public boolean replace(long id, Employee employee){
        return repository.replace(id, employee);
    }
    
    public boolean patch(JsonPatch patch, Employee targetEmployee) throws JsonPatchException, JsonProcessingException{
        return repository.patch(patch, targetEmployee);
    }
    
    public boolean removeByID(long employeeID){
        return repository.removeByID(employeeID);
    }
    
}
