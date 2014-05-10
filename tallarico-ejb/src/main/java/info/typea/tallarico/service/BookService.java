package info.typea.tallarico.service;

import info.typea.tallarico.model.Book;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class BookService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;
    
    public void insertBook(Book book) {
    	em.persist(book);
    }
}
