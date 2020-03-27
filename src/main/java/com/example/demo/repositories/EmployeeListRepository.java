/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.repositories;

import com.example.demo.interfaces.IEmployeeRepository;
import com.example.demo.models.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeListRepository implements IEmployeeRepository {

    @Autowired
    private ObjectMapper objectMapper;
    private final List<Employee> employeeList;
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    public EmployeeListRepository() {
        employeeList = new ArrayList<>();
//        employeeList.add(new Employee(getNextID(), "Maria"));
//        employeeList.add(new Employee(getNextID(), "Madalena"));
    }

    @Override
    public final long getNextID() {
        return this.counter.incrementAndGet();
    }

    @Override
    public List<Employee> getAll() {
        return employeeList;
    }

    @Override
    public Employee getByID(long id) {
        for (int i = 0; i < employeeList.size(); i++) {
            Employee employee = employeeList.get(i);
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public long add(Employee employee) {
        employeeList.add(employee);
        return employee.getId();
    }

    @Override
    public boolean replace(long id, Employee employee) {
        for (int i = 0; i < employeeList.size(); i++) {
            Employee emp = employeeList.get(i);
            if (emp.getId() != id) {
                continue;
            }
            employeeList.set(i, employee);
            return true;
        }
        return false;
    }

    @Override
    public boolean patch(JsonPatch patch, Employee targetEmployee)  {
        try {
            JsonNode patched = patch.apply(objectMapper.convertValue(targetEmployee, JsonNode.class));
            System.out.println(patched.toPrettyString());
            Employee employee = objectMapper.treeToValue(patched, Employee.class);
            return replace(targetEmployee.getId(), employee);
        } catch (JsonPatchException | JsonProcessingException ex) {
            Logger.getLogger(EmployeeListRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean removeByID(long employeeID) {
        for (int i = 0; i < employeeList.size(); i++) {
            Employee employee = employeeList.get(i);
            if (employee.getId() != employeeID) {
                continue;
            }
            employeeList.remove(i);
            return true;
        }
        return false;
    }
}
