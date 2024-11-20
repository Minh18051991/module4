// src/main/java/org/example/library/aspect/LoggingAspect.java
package org.example.library.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private  int visitCount = 0;

    @After("execution(* org.example.library.service.BookService.borrowBook(..)) || execution(* org.example.library.service.BookService.returnBook(..))")
    public void logBookStateChange() {
        System.out.println("Book state changed");

    }

    @After("execution(* org.example.library.controller.BookController.*(..))")
    public void logLibraryVisit() {
        visitCount++;
        System.out.println("Library visited. Total visits: " + visitCount);
    }
    public int getVisitCount() {
        return visitCount;
    }
}