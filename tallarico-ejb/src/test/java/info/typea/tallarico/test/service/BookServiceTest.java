package info.typea.tallarico.test.service;

import info.typea.tallarico.model.Book;
import info.typea.tallarico.service.BookService;
import info.typea.tallarico.util.Resources;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class BookServiceTest {
	   @Deployment
	    public static Archive<?> createTestArchive() {
	        return ShrinkWrap.create(WebArchive.class, "test.war")
	                .addClass(Resources.class)
	                .addClass(Book.class)
	                .addClass(BookService.class)
	                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
	                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
	                ;
	    }

	    @Inject
	    Logger log;

	    @Inject
	    BookService bookService;
	    
	    @Test
	    public void insertBookTest() {
	    	Book book = new Book();
	    	
	    	book.setTitle("Test Book");
	    	book.setPrice(150f);
	    	book.setDescription("this is test book.");
	    	
	    	bookService.insertBook(book);
	    	
	    }
}
