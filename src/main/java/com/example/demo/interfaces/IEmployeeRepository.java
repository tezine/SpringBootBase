/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.interfaces;

import com.example.demo.models.Employee;
import com.github.fge.jsonpatch.JsonPatch;
import java.util.List;


public interface IEmployeeRepository {
    public long getNextID();
    public List<Employee> getAll();
    public Employee getByID(long id);
    public long add(Employee employee);
    public boolean replace(long id, Employee employee);
    public boolean patch(JsonPatch patch, Employee targetEmployee);
    public boolean removeByID(long employeeID);
}
