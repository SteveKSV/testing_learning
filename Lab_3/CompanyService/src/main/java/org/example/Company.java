package org.example;

 /*
  @author - Stepan Klem
  @project - CompanyService
  @date: 10.09.2024
 */

 public class Company {
     private Company parent;
     private long employeeCount;

     public Company(Company parent, long employeeCount) {
         this.parent = parent;
         this.employeeCount = employeeCount;
     }

     public Company getParent() {
         return parent;
     }

     public void setParent(Company parent) {
         this.parent = parent;
     }

     public long getEmployeeCount() {
         return employeeCount;
     }

     public void setEmployeeCount(long employeeCount) {
         this.employeeCount = employeeCount;
     }
 }
