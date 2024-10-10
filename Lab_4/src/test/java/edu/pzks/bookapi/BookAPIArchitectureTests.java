package edu.pzks.bookapi;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.thirdparty.com.google.common.hash.HashCode;
import edu.pzks.bookapi.model.Book;
import edu.pzks.bookapi.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Equals;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

/*
  @author   Stepan Klem
  @project   book-api
  @version  1.0.0
  @since 30.09.2024
*/

class BookAPIArchitectureTests {

    private static JavaClasses applicationClasses;

    @BeforeEach
    void initialize() {
        applicationClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_ARCHIVES)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("edu.pzks.bookapi");
    }

    @Test
    void shouldFollowArchitecture() {
        layeredArchitecture()
                .consideringAllDependencies()
                .layer("Controller").definedBy("..controller..")
                .layer("Service").definedBy("..service..")
                .layer("Repository").definedBy("..repository..")
                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller", "Service")
                .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service")
                .check(applicationClasses);
    }

    @Test
    void controllersShouldBeAnnotatedWithRestController() {
        classes()
                .that().resideInAPackage("..controller..")
                .should().beAnnotatedWith(RestController.class)
                .check(applicationClasses);
    }

    @Test
    void repositoriesShouldBeInterfaces() {
        classes()
                .that().resideInAPackage("..repository..")
                .should().beInterfaces()
                .check(applicationClasses);
    }

    @Test
    void serviceClassesShouldResideInServicePackage() {
        classes()
                .that().resideInAPackage("..service..")
                .should().beAnnotatedWith(org.springframework.stereotype.Service.class)
                .check(applicationClasses);
    }

    @Test
    void modelFieldsShouldNotBePublic() {
        fields()
                .that().areDeclaredInClassesThat().resideInAPackage("..model..")
                .should().notBePublic()
                .check(applicationClasses);
    }

    @Test
    void servicesShouldHaveOnlyPublicMethods() {
        methods()
                .that().areDeclaredInClassesThat().resideInAPackage("..service..")
                .should().bePublic()
                .check(applicationClasses);
    }

    @Test
    void controllersShouldHaveOnlyPublicMethods() {
        methods()
                .that().areDeclaredInClassesThat().resideInAPackage("..controller..")
                .should().bePublic()
                .check(applicationClasses);
    }

    @Test
    void servicesShouldNotDependOnControllers() {
        noClasses()
                .that().resideInAPackage("..service..")
                .should().dependOnClassesThat().resideInAPackage("..controller..")
                .check(applicationClasses);
    }

    @Test
    void modelClassesShouldNotDependOnOtherLayers() {
        noClasses()
                .that().resideInAPackage("..model..")
                .should().dependOnClassesThat().resideInAnyPackage("..service..", "..repository..", "..controller..")
                .check(applicationClasses);
    }

    @Test
    void servicesShouldOnlyBeCalledInControllers() {
        classes()
                .that()
                .resideInAPackage("..service..")
                .should()
                .onlyHaveDependentClassesThat()
                .resideInAPackage("..controller..");
    }

    @Test
    void repositoriesShouldBeAnnotatedWithRepository() {
        classes()
                .that().resideInAPackage("..repository..")
                .should().beAnnotatedWith(org.springframework.stereotype.Repository.class)
                .check(applicationClasses);
    }

    @Test
    void servicesShouldNotDependOnOtherServices() {
        noClasses()
                .that().resideInAPackage("..service..")
                .should().dependOnClassesThat().resideInAPackage("..service..")
                .check(applicationClasses);
    }

    @Test
    void servicesShouldUseConstructorInjection() {
        constructors()
                .that().areDeclaredInClassesThat().resideInAPackage("..service..")
                .should().haveRawParameterTypes(BookRepository.class) // перевіряємо, що використовується правильний конструктор
                .check(applicationClasses);
    }

    @Test
    void controllerMethodsShouldBeAnnotatedWithHttpMethods() {
        methods()
                .that().areDeclaredInClassesThat().resideInAPackage("..controller..")
                .should().beAnnotatedWith(org.springframework.web.bind.annotation.GetMapping.class)
                .orShould().beAnnotatedWith(org.springframework.web.bind.annotation.PostMapping.class)
                .orShould().beAnnotatedWith(org.springframework.web.bind.annotation.PutMapping.class)
                .orShould().beAnnotatedWith(org.springframework.web.bind.annotation.DeleteMapping.class)
                .check(applicationClasses);
    }

    @Test
    void servicesShouldNotDependOnSQLClasses() {
        noClasses()
                .that().resideInAPackage("..service..")
                .should().dependOnClassesThat().resideInAnyPackage("java.sql..")
                .check(applicationClasses);
    }

    @Test
    void servicesShouldHaveValidMethodNames() {
        methods()
                .that().areDeclaredInClassesThat().resideInAPackage("..service..")
                .and().areNotAnnotatedWith(PostConstruct.class) // виключаємо методи, позначені @PostConstruct
                .should().haveNameStartingWith("get")
                .orShould().haveNameStartingWith("create")
                .orShould().haveNameStartingWith("update")
                .orShould().haveNameStartingWith("delete")
                .check(applicationClasses);
    }

    @Test
    void controllersShouldHaveValidMethodNames() {
        methods()
                .that().areDeclaredInClassesThat().resideInAPackage("..controller..")
                .should().haveNameStartingWith("get")
                .orShould().haveNameStartingWith("create")
                .orShould().haveNameStartingWith("update")
                .orShould().haveNameStartingWith("delete")
                .check(applicationClasses);
    }

    @Test
    void controllersShouldNotDependOnEachOther() {
        noClasses()
                .that().resideInAnyPackage("..controller..")
                .should().dependOnClassesThat().resideInAnyPackage("..controller..")
                .check(applicationClasses);
    }

    @Test
    void servicesShouldNotDependOnEachOther() {
        noClasses()
                .that().resideInAnyPackage("..service..")
                .should().dependOnClassesThat().resideInAnyPackage("..service..")
                .check(applicationClasses);
    }

    @Test
    void modelsShouldNotDependOnEachOther() {
        noClasses()
                .that().resideInAnyPackage("..model..")
                .should().dependOnClassesThat().resideInAnyPackage("..model..")
                .check(applicationClasses);
    }

}
