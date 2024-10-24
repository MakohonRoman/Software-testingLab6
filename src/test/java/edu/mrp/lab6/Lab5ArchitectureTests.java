package edu.mrp.lab6;

/*
@author   Makohon Roman
@project   Architecture testing
@class  $444A
@version  1.0.0
@since 9.10.2024 - 14.23
*/

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

class Lab5ArchitectureTests {

    private JavaClasses applicationClasses;

    @BeforeEach
    void setup() {
        applicationClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_ARCHIVES)
                .importPackages("edu.mrp.lab5");
    }

    // 1. Контролери повинні бути в пакеті 'controller' і мати суфікс 'Controller'
    @Test
    void controllersShouldBeInControllerPackageAndHaveProperNaming() {
        classes()
                .that().resideInAPackage("..controller..")
                .should().haveSimpleNameEndingWith("Controller")
                .check(applicationClasses);
    }

    // 2. Класи сервісів повинні бути анотовані @Service
    @Test
    void serviceClassesShouldBeAnnotatedWithService() {
        classes()
                .that().resideInAPackage("..service..")
                .should().beAnnotatedWith(Service.class)
                .check(applicationClasses);
    }

    // 3. Репозиторії повинні бути анотовані @Repository
    @Test
    void repositoryClassesShouldBeAnnotatedWithRepository() {
        classes()
                .that().resideInAPackage("..repository..")
                .should().beAnnotatedWith(Repository.class)
                .check(applicationClasses);
    }

    // 4. Контролери не повинні залежати від інших контролерів
    @Test
    void controllersShouldNotDependOnOtherControllers() {
        noClasses()
                .that().resideInAPackage("..controller..")
                .should().dependOnClassesThat().resideInAPackage("..controller..")
                .check(applicationClasses);
    }

    // 5. Поля в моделях повинні мати специфічні типи
    @Test
    void modelFieldsShouldHaveSpecificTypes() {
        fields()
                .that().areDeclaredInClassesThat().resideInAPackage("..model..")
                .should().haveRawType(String.class)
                .orShould().haveRawType(int.class)
                .orShould().haveRawType(double.class)
                .check(applicationClasses);
    }

    // 6. Усі класи контролерів повинні бути публічними
    @Test
    void controllerClassesShouldBePublic() {
        classes()
                .that().resideInAPackage("..controller..")
                .should().bePublic()
                .check(applicationClasses);
    }

    // 7. Репозиторії повинні бути інтерфейсами
    @Test
    void repositoryClassesShouldBeInterfaces() {
        classes()
                .that().resideInAPackage("..repository..")
                .should().beInterfaces()
                .check(applicationClasses);
    }

    // 8. Сервіси не повинні залежати від контролерів
    @Test
    void servicesShouldNotDependOnControllers() {
        noClasses()
                .that().resideInAPackage("..service..")
                .should().dependOnClassesThat().resideInAPackage("..controller..")
                .check(applicationClasses);
    }

    // 9. Методи контролера повинні повертати об'єкт або ResponseEntity
    @Test
    void controllerMethodsShouldReturnObjectOrResponseEntity() {
        methods()
                .that().areDeclaredInClassesThat().resideInAPackage("..controller..")
                .should().haveRawReturnType(Object.class)
                .orShould().haveRawReturnType(org.springframework.http.ResponseEntity.class)
                .check(applicationClasses);
    }

    // 10. Методи сервісів повинні бути публічними
    @Test
    void serviceMethodsShouldBePublicOrProtected() {
        methods()
                .that().areDeclaredInClassesThat().resideInAPackage("..service..")
                .should().bePublic().orShould().beProtected()
                .check(applicationClasses);
    }



    // 11. Поля сервісів не повинні бути публічними
    @Test
    void serviceFieldsShouldNotBePublic() {
        fields()
                .that().areDeclaredInClassesThat().resideInAPackage("..service..")
                .should().notBePublic()
                .check(applicationClasses);
    }

    // 12. Контролери повинні бути анотовані @RestController
    @Test
    void controllerClassesShouldBeAnnotatedWithRestController() {
        classes()
                .that().resideInAPackage("..controller..")
                .should().beAnnotatedWith(RestController.class)
                .check(applicationClasses);
    }

    // 13. Контролери не повинні мати полів, анотованих @Autowired
    @Test
    void controllerFieldsShouldNotBeAnnotatedWithAutowired() {
        noClasses()
                .that().resideInAPackage("..controller..")
                .should().beAnnotatedWith(Autowired.class)
                .check(applicationClasses);
    }

    // 14. Поля класів моделей не повинні бути публічними
    @Test
    void modelFieldsShouldNotBePublic() {
        fields()
                .that().areDeclaredInClassesThat().resideInAPackage("..model..")
                .should().notBePublic()
                .check(applicationClasses);
    }

    // 15. Класи контролера повинні використовуватися тільки в контролерах
    @Test
    void controllerClassesShouldOnlyBeUsedInControllerLayer() {
        classes()
                .that().resideInAPackage("..controller..")
                .should().onlyBeAccessed().byAnyPackage("..controller..")
                .check(applicationClasses);
    }

    // 16. Поля класів репозиторіїв не повинні бути публічними
    @Test
    void repositoryFieldsShouldNotBePublic() {
        fields()
                .that().areDeclaredInClassesThat().resideInAPackage("..repository..")
                .should().notBePublic()
                .allowEmptyShould(true)
                .check(applicationClasses);
    }


    // 17. Сервіси повинні використовуватися тільки в контролерах або інших сервісах
    @Test
    void serviceClassesShouldOnlyBeUsedInControllerOrServiceLayers() {
        classes()
                .that().resideInAPackage("..service..")
                .should().onlyBeAccessed().byAnyPackage("..controller..", "..service..")
                .check(applicationClasses);
    }

    // 18. Методи контролерів не повинні кидати RuntimeException
    @Test
    void controllerMethodsShouldNotThrowRuntimeException() {
        methods()
                .that().areDeclaredInClassesThat().resideInAPackage("..controller..")
                .should().notHaveRawReturnType(RuntimeException.class) // Перевірка, що метод не повертає RuntimeException
                .check(applicationClasses);
    }

    // 19. Методи репозиторіїв повинні бути публічними
    @Test
    void repositoryMethodsShouldBePublic() {
        methods()
                .that().areDeclaredInClassesThat().resideInAPackage("..repository..")
                .should().bePublic()
                .allowEmptyShould(true)
                .check(applicationClasses);
    }


    // 20. Поля класів моделі повинні бути закритими (private)
    @Test
    void modelFieldsShouldBePrivate() {
        fields()
                .that().areDeclaredInClassesThat().resideInAPackage("..model..")
                .should().bePrivate()
                .check(applicationClasses);
    }
}

