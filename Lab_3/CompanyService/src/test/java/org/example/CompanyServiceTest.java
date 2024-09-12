package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
 /*
  @author - Stepan Klem
  @project - CompanyService
  @date: 10.09.2024
 */

class CompanyServiceTest {

    // Змінні для тестування сервісу
    private CompanyService service;
    private Company topLevel;
    private Company middleLevel;
    private Company bottomLevel;
    private Company parent;
    private Company child;
    private Company grandChild;
    private Company noParentCompany;
    private Company anotherTopLevel;
    private List<Company> companies;

    @BeforeEach
    void setUp() {
        // Ініціалізація тестових даних
        service = new CompanyService();
        topLevel = new Company(null, 100);        // Топ-рівень
        middleLevel = new Company(topLevel, 50);  // Середній рівень
        bottomLevel = new Company(middleLevel, 20); // Нижній рівень
        parent = new Company(null, 100);          // Батьківська компанія
        child = new Company(parent, 50);          // Дочірня компанія
        grandChild = new Company(child, 20);      // Внучка
        noParentCompany = new Company(null, 80);  // Компанія без батька
        anotherTopLevel = new Company(null, 200); // Ще одна топ-компанія
        companies = Arrays.asList(parent, child, grandChild); // Список компаній
    }

    // Тести для методу getTopLevelParent

    /**
     * Перевіряє, що компанія з кількома рівнями ієрархії правильно повертає верхній рівень.
     */
    @Test
    void whenCompanyHasMultipleParentLevels_thenReturnTopLevelParent() {
        // Змінив тест на порівняння з використанням різних підрівнів
        assertEquals(topLevel, service.getTopLevelParent(middleLevel));
        assertNotEquals(middleLevel, service.getTopLevelParent(bottomLevel));
    }

    /**
     * Перевіряє, що компанія з великою ієрархією підкомпаній правильно повертає верхній рівень.
     */
    @Test
    void whenCompanyHasLargeHierarchy_thenReturnTopLevelParent() {
        // Головна компанія
        Company mainCompany = new Company(null, 500);

        // Підкомпанії
        Company frontEnd = new Company(mainCompany, 150);
        Company backEnd = new Company(mainCompany, 200);
        Company design = new Company(mainCompany, 100);

        // Підкомпанії для front-end
        Company frontEndDev = new Company(frontEnd, 50);
        Company frontEndQA = new Company(frontEnd, 30);

        // Підкомпанії для back-end
        Company backEndDev = new Company(backEnd, 70);
        Company backEndOps = new Company(backEnd, 60);

        // Підкомпанії для design
        Company designGraphics = new Company(design, 40);
        Company designUX = new Company(design, 30);

        // Тестування
        assertEquals(mainCompany, service.getTopLevelParent(frontEndDev));
        assertEquals(mainCompany, service.getTopLevelParent(frontEndQA));
        assertEquals(mainCompany, service.getTopLevelParent(backEndDev));
        assertEquals(mainCompany, service.getTopLevelParent(backEndOps));
        assertEquals(mainCompany, service.getTopLevelParent(designGraphics));
        assertEquals(mainCompany, service.getTopLevelParent(designUX));
        assertEquals(mainCompany, service.getTopLevelParent(frontEnd));
        assertEquals(mainCompany, service.getTopLevelParent(backEnd));
        assertEquals(mainCompany, service.getTopLevelParent(design));
        assertEquals(mainCompany, service.getTopLevelParent(mainCompany));
    }


    /**
     * Перевіряє, що якщо у компанії немає батька, вона повертається як топ-рівень.
     */
    @Test
    void whenCompanyHasNoParent_thenReturnItselfAsTopLevelParent() {
        assertEquals(noParentCompany, service.getTopLevelParent(noParentCompany));
        assertEquals(anotherTopLevel, service.getTopLevelParent(anotherTopLevel));
    }

    /**
     * Перевіряє, що компанія з батьком повертає правильного верхнього батька.
     */
    @Test
    void whenCompanyHasParent_thenReturnCorrectTopLevelParent() {
        assertEquals(parent, service.getTopLevelParent(child));
        assertEquals(topLevel, service.getTopLevelParent(middleLevel));
    }

    /**
     * Перевіряє, що компанія з одного рівня, але з різними батьками, повертає відповідних батьків.
     */
    @Test
    void whenCompaniesAtSameLevelHaveDifferentParents_thenReturnRespectiveTopLevelParent() {
        Company sibling = new Company(parent, 70); // Новий "сіблінг" для перевірки
        assertEquals(parent, service.getTopLevelParent(sibling));
        assertNotEquals(topLevel, service.getTopLevelParent(sibling));
    }

    /**
     * Перевіряє, що компанія на топ-рівні повертає саму себе.
     */
    @Test
    void whenCompanyIsTopLevelParent_thenReturnSameCompany() {
        assertEquals(topLevel, service.getTopLevelParent(topLevel));
        assertEquals(anotherTopLevel, service.getTopLevelParent(anotherTopLevel));
    }

    /**
     * Перевіряє ситуацію, коли компанія без батьків з дочірніми компаніями все одно повертає саму себе.
     */
    @Test
    void whenCompanyHasChildrenButNoParent_thenReturnItselfAsTopLevel() {
        Company childlessTop = new Company(null, 150);
        child.setParent(childlessTop);
        assertEquals(childlessTop, service.getTopLevelParent(child));
    }

    /**
     * Перевіряє, що при виявленні циклічної залежності виникає виняток.
     */
    @Test
    void whenCompanyIsPartOfCircularReference_thenThrowException() {
        parent.setParent(child);

        Exception exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            service.getTopLevelParent(parent);
        });
        assertEquals("В ієрархії компанії виявлено циклічне посилання", exception.getMessage());
    }

    /**
     * Перевіряє, що якщо компанія є null, то викликається NullPointerException.
     */
    @Test
    void whenCompanyIsNullForTopLevelParent_thenThrowException() {
        Exception exception = Assertions.assertThrows(NullPointerException.class, () -> {
            service.getTopLevelParent(null);
        });
        Assertions.assertSame(NullPointerException.class, exception.getClass());
    }

    // Тести для методу getEmployeeCountForCompanyAndChildren

    /**
     * Перевіряє, що компанія без дітей повертає кількість своїх співробітників.
     */
    @Test
    void whenCompanyHasNoChildren_thenReturnItsEmployeeCount() {
        assertEquals(100, service.getEmployeeCountForCompanyAndChildren(parent, Collections.emptyList()));
    }

    /**
     * Перевіряє, що компанія з однією дочірньою компанією повертає суму співробітників.
     */
    @Test
    void whenCompanyHasOneChild_thenReturnSumOfEmployees() {
        assertEquals(150, service.getEmployeeCountForCompanyAndChildren(parent, Arrays.asList(child)));
    }

    /**
     * Перевіряє, що компанія, яка має дочірню компанію, та дана інші топ-рівень компанія, то повертається правильна сума
     */
    @Test
    void whenCompanyHasChildAndIsGivenAnotherTopLevelCompany_thenReturnCorrectSum() {
        Company topLevelChild = new Company(null, 200);
        List<Company> children = Arrays.asList(child, topLevelChild);
        assertEquals(150, service.getEmployeeCountForCompanyAndChildren(parent, children));
    }

    /**
     * Перевіряє, що якщо будь-яка компанія має негативну кількість співробітників, то викидається виняток.
     */
    @Test
    void whenAnyCompanyHasNegativeEmployees_thenThrowException() {
        Company parent = new Company(null, 100);
        Company negativeEmployeeChild = new Company(parent, -10); // Негативна кількість співробітників
        List<Company> children = Arrays.asList(negativeEmployeeChild);

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.getEmployeeCountForCompanyAndChildren(parent, children);
        });
        assertEquals("Число співробітників компанії не може бути негативним", exception.getMessage());
    }

    /**
     * Перевіряє, що компанія з кількома дочірніми компаніями повертає суму співробітників.
     */
    @Test
    void whenCompanyHasMultipleChildren_thenReturnSumOfAllEmployeeCounts() {
        assertEquals(170, service.getEmployeeCountForCompanyAndChildren(parent, companies));
    }

    /**
     * Перевіряє, що компанія без співробітників і без дітей повертає 0.
     */
    @Test
    void whenCompanyHasNoChildrenAndNoEmployees_thenReturnZero() {
        Company emptyCompany = new Company(null, 0);
        assertEquals(0, service.getEmployeeCountForCompanyAndChildren(emptyCompany, Collections.emptyList()));
    }

    /**
     * Перевіряє, що компанія без дітей, але з внуками повертає правильну кількість співробітників.
     */
    @Test
    void whenCompanyHasOnlyGrandchildren_thenReturnCorrectEmployeeCount() {
        Company childlessParent = new Company(null, 100);
        grandChild.setParent(childlessParent);
        List<Company> grandChildren = Arrays.asList(grandChild);
        assertEquals(120, service.getEmployeeCountForCompanyAndChildren(childlessParent, grandChildren));
    }

    /**
     * Перевіряє, що метод обробляє циклічні посилання в компаніях.
     */
    @Test
    void whenCompanyHasCircularReference_thenHandleGracefully() {
        parent.setParent(child); // Циклічна залежність
        assertEquals(150, service.getEmployeeCountForCompanyAndChildren(parent, Arrays.asList(child)));
    }

    /**
     * Перевіряє, що якщо компанія не входить до списку, повертається кількість співробітників тільки цієї компанії.
     */
    @Test
    void whenCompanyNotInList_thenOnlyReturnItsEmployeeCount() {
        Company unrelatedCompany = new Company(null, 300);
        assertEquals(300, service.getEmployeeCountForCompanyAndChildren(unrelatedCompany, companies));
    }

    /**
     * Перевіряє, що якщо компанія є null, то викликається NullPointerException.
     */
    @Test
    void whenCompanyIsNullForEmployeeCount_thenThrowException() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            service.getEmployeeCountForCompanyAndChildren(null, companies);
        });
        Assertions.assertSame(NullPointerException.class, exception.getClass());
    }
}
