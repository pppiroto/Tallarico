package info.typea.tallarico.service;

import info.typea.tallarico.model.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
    
    /**
     * @see http://relation.to/Bloggers/HibernateStaticMetamodelGeneratorAnnotationProcessor
     * @see http://hibernate.org/orm/tooling/
     * @param title
     * @param price
     * @param description
     * @param isbn
     * @return
     */
    public List<Book> selectBooksByCondition2(
    		String title, 
			Float price, 
			String description,
			String isbn) {    	
    
    	CriteriaBuilder builder = em.getCriteriaBuilder();
    	CriteriaQuery<Book> query = builder.createQuery(Book.class);
    	
    	Root<Book> book = query.from(Book.class);
    	
    	query.select(book);
   	
    	List<Predicate> creteria = new ArrayList<Predicate>();
    		
		if (title != null) {
			creteria.add(builder.like(book.<String>get("title"), title + "%"));
		}
		
		if (price != null) {
			creteria.add(builder.equal(book.<Float>get("price"), price));
		}

		if (description != null) {
			creteria.add(builder.like(book.<String>get("description"), "%"+description+"%"));
		}

		if (isbn != null) {
			creteria.add(builder.equal(book.<String>get("isbn"), isbn));
		}
		
		if (creteria.size() > 0) {
			query.where(builder.and(creteria.toArray(new Predicate[]{})));
		}
		
    	return em.createQuery(query).getResultList();
    }
    
    
    public List<Book> selectBooksByCondition(
			String title, 
			Float price, 
			String description,
			String isbn) {

    	int condCnt = 0;
    	Map<String, Object> parameterMap = new HashMap<String, Object>();
    	StringBuilder buf = new StringBuilder();
    	
    	buf.append("select b from Book b");
    	if (title != null ||
    		price != null ||
    		description != null || 
    		isbn != null){
    	
    		buf.append(" where");
    		
    		if (title != null) {
    			buf.append(((condCnt++ == 0)?"":" and"));
    			buf.append(" title like :title");
    			parameterMap.put("title", title + "%");
    		}
    		
    		if (price != null) {
    			buf.append(((condCnt++ == 0)?"":" and"));
    			buf.append(" price = :price");
    			parameterMap.put("price", price);
    		}

    		if (description != null) {
    			buf.append(((condCnt++ == 0)?"":" and"));
    			buf.append(" description like :description");
    			parameterMap.put("description", "%" + description + "%");
    		}

    		if (isbn != null) {
    			buf.append(((condCnt++ == 0)?"":" and"));
    			buf.append(" isbn = :isbn");
    			parameterMap.put("isbn", isbn);
    		}
    	
    	}
    
    	//Query query = em.createQuery(buf.toString());
    	TypedQuery<Book> query = em.createQuery(buf.toString(), Book.class);
    	if (parameterMap.size() > 0) {
    		for(Map.Entry<String, Object> entry : parameterMap.entrySet()) {
    			query.setParameter(entry.getKey(), entry.getValue());
    		}
    	}
    
    	return query.getResultList();
    }
}
