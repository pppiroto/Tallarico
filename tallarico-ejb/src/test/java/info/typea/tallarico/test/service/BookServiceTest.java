package info.typea.tallarico.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import info.typea.tallarico.model.Book;
import info.typea.tallarico.service.BookService;
import info.typea.tallarico.util.Resources;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <h1>UserTransaction のインジェクト</h1>
 * http://arquillian.org/guides/testing_java_persistence_ja/
 * 
 * 
 * 
 * @author piroto
 *
 */
@RunWith(Arquillian.class)
public class BookServiceTest {
	   @Deployment
	    public static Archive<?> createTestArchive() {
	        return ShrinkWrap.create(WebArchive.class, "test.war")
	        		.addClass(Resources.class)
	                .addClasses(Book.class,BookService.class)
	                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
	                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
	                ;
	    }

	    @Inject
	    Logger log;
	    
	    @Inject
	    BookService bookService;

	    @Inject
	    UserTransaction utx;
	    
	    @Before
	    public void beforeTest() throws Exception {
	    	utx.begin();
	    }
	    
	    @After
	    public void afterTest() throws Exception {
	    	utx.rollback();
	    }
	    
	    @Test
	    public void insertBookTest() {
	    	Book book = createTestBook((int)(Math.random() * 10));
	    	bookService.insertBook(book);
	    	log.info(book.toString());
	    	assertNotNull(book.getId());
	    }
	    
	    @Test
	    public void findBookByIdTest() {
	    	createTestData(5);

	    	List<Book> allBooks = bookService.selectAllBooks();
	    	int pos = (int)(Math.random() * 100d) % allBooks.size(); 
	    	
	    	Book book1 = allBooks.get(pos);
	    	log.info(book1.toString());
	    	
	    	Book book2 = bookService.findBookById(book1.getId());
	    	log.info(book2.toString());
	    	
	    	assertEquals(book1, book2);
	    	assertTrue(book1 == book2);
	    }
	    
	    
	    @Test
	    public void selectBooksByTitleTest() {
	    	createTestData(5);
	    	
	    	List<Book> books = bookService.selectBooksByTitle(SAMPLE_BOOK_TITLE_PREFIX);
	    	for (Book book : books) {
	    		log.info(book.toString());
	    		assertTrue(book.getTitle().startsWith(SAMPLE_BOOK_TITLE_PREFIX));
	    	}
	    }
	    
	    @Test
	    public void selectMoreExpensiveBooksTest() {
	    	createTestData(5);
	    	
	    	final float BASE_PRICE = 300f;
	    	
	    	List<Book> books = bookService.selectMoreExpensiveBooks(BASE_PRICE);
	    	for (Book book : books) {
	    		log.info(book.toString());
	    		assertTrue(book.getPrice() > BASE_PRICE);
	    	}
	    }
	    
	    @Test
	    public void selectBooksByCondition2Test() {
	    	List<Book> sampleList = createTestData(10);
	    	Book searchConditionSample = sampleList.get(5);
	    	log.info(searchConditionSample.toString());
	    	
	    	int[] titleSubstr = {0,5};
	    	int[] descSubstr = {2,6};
	    	List<Book> books = bookService.selectBooksByCondition2(
	    			searchConditionSample.getTitle().substring(titleSubstr[0],titleSubstr[1]), 
	    			searchConditionSample.getPrice(), 
	    			searchConditionSample.getDescription().substring(descSubstr[0],descSubstr[1]), 
	    			searchConditionSample.getIsbn()
	    	);
	    
	    	for (Book book : books) {
	    		log.info(book.toString());
	    		
	    		assertEquals(
	    				searchConditionSample.getTitle().substring(titleSubstr[0],titleSubstr[1]),
	    				book.getTitle().substring(titleSubstr[0],titleSubstr[1]));
	    		assertEquals(
	    				searchConditionSample.getPrice(),
	    				book.getPrice());
	     		assertEquals(
	     				searchConditionSample.getDescription().substring(descSubstr[0],descSubstr[1]),
	     				book.getDescription().substring(descSubstr[0],descSubstr[1]));
	    		assertEquals(
	    				searchConditionSample.getIsbn(),
	    				book.getIsbn());
	    	}
	    }
	    
	    @Test
	    public void selectBooksByConditionTest() {
	    	List<Book> sampleList = createTestData(10);
	    	Book searchConditionSample = sampleList.get(5);
	    	log.info(searchConditionSample.toString());
	    	
	    	int[] titleSubstr = {0,5};
	    	int[] descSubstr = {2,6};
	    	List<Book> books = bookService.selectBooksByCondition(
	    			searchConditionSample.getTitle().substring(titleSubstr[0],titleSubstr[1]), 
	    			searchConditionSample.getPrice(), 
	    			searchConditionSample.getDescription().substring(descSubstr[0],descSubstr[1]), 
	    			searchConditionSample.getIsbn());
	    
	    	for (Book book : books) {
	    		log.info(book.toString());
	    		
	    		assertEquals(
	    				searchConditionSample.getTitle().substring(titleSubstr[0],titleSubstr[1]),
	    				book.getTitle().substring(titleSubstr[0],titleSubstr[1]));
	    		assertEquals(
	    				searchConditionSample.getPrice(),
	    				book.getPrice());
	     		assertEquals(
	     				searchConditionSample.getDescription().substring(descSubstr[0],descSubstr[1]),
	     				book.getDescription().substring(descSubstr[0],descSubstr[1]));
	    		assertEquals(
	    				searchConditionSample.getIsbn(),
	    				book.getIsbn());
	    	}
	    }
	    
	    
	    private final String SAMPLE_BOOK_TITLE_PREFIX = "SampleBook";
	    private List<Book> createTestData(int size) {
	    	List<Book> inserted = new ArrayList<Book>();
	    	for (int i=0; i<size; i++) {
	    		Book book = createTestBook(i);
	    		inserted.add(book);
	    		bookService.insertBook(book);
	    	}
	    	
	    	return inserted;
	    }
	    private Book createTestBook(int seed) {
	    	return new Book(
    				String.format("%s%02d", SAMPLE_BOOK_TITLE_PREFIX, seed),
    				seed * 100f,
    				String.format("Description about %s%02d.", SAMPLE_BOOK_TITLE_PREFIX, seed),
    				String.format("isbn-%05d",seed),
    				(int)(Math.random() * 100d),
    				(seed % 2 == 0)
    			);
	    }
}
