/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.repositories;

import com.example.demo.helper.Helper;
import com.example.demo.helper.Logger;
import com.example.demo.interfaces.IEmployeeRepository;
import com.example.demo.models.Employee;
import com.github.fge.jsonpatch.JsonPatch;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


public class EmployeeMySQLRepository implements IEmployeeRepository{

      @PersistenceContext()
    private EntityManager entityManager;

    @Override
    public long getNextID() {
        try {
            long maxID = (long) entityManager.createQuery("select max(e.id) from Employee e where e.id >= :myOrg")//usei o myOrg só para testar o setParameter :-) 
                    .setParameter("myOrg", 0L)
                    .getSingleResult();
            return maxID+1;  
        } catch (Exception ex) {             
            Logger.logError(Helper.prepareError(ex));
            throw ex;
        }
    }

    //@Query(value = "SELECT * FROM person WHERE id = :id", nativeQuery = true)
    //public Employee getByID(@Param("id") long id);
    @Override
    public List<Employee> getAll() {
        try {
            List<Employee> list;
            list = entityManager.createQuery("SELECT a FROM "+Employee.class.getName()+" a", Employee.class).getResultList();
            return list;
        } catch (Exception ex) {
            Logger.logError(Helper.prepareError(ex));
        }
        return null;
    }

    @Override
    public Employee getByID(long id) {
        try {
            Employee employee = entityManager.find(Employee.class, id);
            return employee;
        } catch (Exception ex) {
            Logger.logError(Helper.prepareError(ex));
            throw ex;
        }
    }

    @Transactional
    @Override
    public long add(Employee employee) {
        try {
            if(employee.getId()<1)employee.setId(getNextID());
            entityManager.persist(employee);
            return employee.getId();
        } catch (Exception ex) {
            Logger.logError(Helper.prepareError(ex));
            throw ex;
        }
    }

    @Transactional
    @Override
    public boolean replace(long id, Employee employee) {
        try {
            Employee emp = entityManager.find(Employee.class, id);
            if (emp == null) {
                return false;
            }
            entityManager.persist(employee);
            return true;
        } catch (Exception ex) {
            Logger.logError(Helper.prepareError(ex));
            throw ex;
        }
    }

    @Override
    public boolean patch(JsonPatch patch, Employee targetEmployee) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Transactional
    @Override
    public boolean removeByID(long employeeID) {
        try {
            Employee employee = entityManager.find(Employee.class, employeeID);
            entityManager.remove(employee);
            return true;
        } catch (Exception ex) {
            Logger.logError(Helper.prepareError(ex));
            throw ex;
        }
    }
    
}
