package info.typea.tallarico.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import info.typea.tallarico.model.Book;
import info.typea.tallarico.service.BookService;
import info.typea.tallarico.util.Resources;

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
	    	Book book = new Book("Insert Test",100f,"Test");
	    	bookService.insertBook(book);
	    	log.info(book.toString());
	    	assertNotNull(book.getId());
	    }
	    
	    @Test
	    public void findBookByIdTest() {
	    	createTestData();

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
	    	createTestData();
	    	
	    	List<Book> books = bookService.selectBooksByTitle(SAMPLE_BOOK_TITLE_PREFIX);
	    	for (Book book : books) {
	    		log.info(book.toString());
	    		assertTrue(book.getTitle().startsWith(SAMPLE_BOOK_TITLE_PREFIX));
	    	}
	    }
	    
	    @Test
	    public void selectMoreExpensiveBooksTest() {
	    	createTestData();
	    	
	    	final float BASE_PRICE = 300f;
	    	
	    	List<Book> books = bookService.selectMoreExpensiveBooks(BASE_PRICE);
	    	for (Book book : books) {
	    		log.info(book.toString());
	    		assertTrue(book.getPrice() > BASE_PRICE);
	    	}
	    }
	    
	    private final String SAMPLE_BOOK_TITLE_PREFIX = "SampleBook";
	    private void createTestData() {
	    	for (int i=0; i<5; i++) {
	    		bookService.insertBook(
	    			new Book(
	    				String.format("%s%02d", SAMPLE_BOOK_TITLE_PREFIX, i),
	    				i * 100f,
	    				String.format("Description about %s%02d.", SAMPLE_BOOK_TITLE_PREFIX, i))
	    		);
	    	}
	    }
}
