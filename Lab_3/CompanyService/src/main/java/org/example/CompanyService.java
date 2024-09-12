package org.example;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

 /*
  @author - Stepan Klem
  @project - CompanyService
  @date: 10.09.2024
 */

import java.util.List;
public class CompanyService implements ICompanyService {

    // Поле для збереження відвіданих компаній
    private Set<Company> visitedCompanies = new HashSet<>();

    @Override
    public Company getTopLevelParent(Company child) {
        visitedCompanies.clear(); // Очищаємо перед кожним викликом
        return findTopLevelParent(child);
    }

    private Company findTopLevelParent(Company company) {
        if (!visitedCompanies.add(company)) { // Якщо компанія вже була відвідана, значить є цикл
            throw new IllegalStateException("В ієрархії компанії виявлено циклічне посилання");
        }

        if (company.getParent() == null) {
            return company;
        }
        return findTopLevelParent(company.getParent());
    }

    @Override
    public long getEmployeeCountForCompanyAndChildren(Company company, List<Company> companies) {
        long totalEmployees = company.getEmployeeCount();

        if (totalEmployees < 0) {
            throw new IllegalArgumentException("Число співробітників компанії не може бути негативним");
        }

        for (Company c : companies) {
            if (c.getParent() != null && c.getParent().equals(company)) {
                totalEmployees += getEmployeeCountForCompanyAndChildren(c, companies);
            }

            if (c.getEmployeeCount() < 0) {
                throw new IllegalArgumentException("Число співробітників компанії не може бути негативним");
            }
        }

        return totalEmployees;
    }
}