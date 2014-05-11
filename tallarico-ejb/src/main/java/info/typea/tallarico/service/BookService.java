package info.typea.tallarico.service;

import info.typea.tallarico.model.Book;

import java.util.List;
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
    
    public Book findBookById(Long id) {
    	return (Book)em.createNamedQuery(Book.QUERY_FIND_BY_ID)
    			.setParameter("id", id)
    			.getSingleResult()
    			;
    }
    
    @SuppressWarnings("unchecked")
	public List<Book> selectAllBooks() {
    	return em.createNamedQuery(Book.QUERY_SELECT_ALL).getResultList();
    }
    
    @SuppressWarnings("unchecked")
	public List<Book> selectBooksByTitle(String title) {
    	return em.createNamedQuery(Book.QUERY_SELECT_BY_TITLE)
    			.setParameter("title", title + "%")
    			.getResultList()
    			;
    }
    
    @SuppressWarnings("unchecked")
	public List<Book> selectMoreExpensiveBooks(Float price) {
    	return em.createNamedQuery(Book.QUERY_SELECT_MORE_EXPENSIVE)
    			.setParameter("price", price)
    			.getResultList()
    			;
    }
}
