/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controllers;

import com.example.demo.models.Employee;
import com.example.demo.services.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    

    @GetMapping(path = "/employees")
    public ResponseEntity getAll() {
        List<Employee> list=employeeService.getAll();
        return ResponseEntity.ok(list);
    }
    
    @GetMapping(path = "/employees/{id}")
    public ResponseEntity<?> getByID(@PathVariable long id) {
        Employee employee= employeeService.getByID(id);
        if(employee!=null)return ResponseEntity.ok(employee);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND); //status 404
    }

    @PostMapping(path = "/employees")
    public ResponseEntity add(@RequestBody Employee employee) {
        //post não deve receber o id, assim, criamos um novo id aqui. 
        long nextID=employeeService.getNextID();
        employee.setId(nextID);
        if(employeeService.add(employee)>0)return new ResponseEntity<>(HttpStatus.CREATED); //status 201.  caso tenha sido criado imediatamente.                 
        //return new ResponseEntity<>(HttpStatus.ACCEPTED);//status 202. caso não seja criado imediatamente 
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
    }
    
    @PutMapping(path = "/employees/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody Employee employee) {
        //com o put, fazemos a substituição total
        if(employeeService.replace(id, employee))return new ResponseEntity<>(HttpStatus.NO_CONTENT); //status 204
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
    @PatchMapping(path = "/employees/{id}") 
    public ResponseEntity patch(@PathVariable long id, @RequestBody JsonPatch patch)  throws JsonProcessingException, JsonPatchException{
        //com o patch, fazemos a substituição parcial
        Employee employee=employeeService.getByID(id);
        if(employeeService.patch(patch,employee))return new ResponseEntity<>(HttpStatus.NO_CONTENT); //status 204
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/employees/{id}")
    public ResponseEntity<?> remove(@PathVariable long id) {
        if(employeeService.removeByID(id)) return new ResponseEntity<>(HttpStatus.NO_CONTENT);//status 204
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
