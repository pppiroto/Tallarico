package info.typea.tallarico.test;

import info.typea.tallarico.util.Resources;

import java.util.Set;
import java.util.logging.Logger;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
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
public class BeanManagerTest {
	
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Resources.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                ;
    }

    @Inject
    Logger log;
    
    @Inject
    BeanManager manager;
    
    @Test
    public void testListAllBeans() throws Exception {
        
     	@SuppressWarnings("serial")
		Set<Bean<?>> beans = manager.getBeans(Object.class, new AnnotationLiteral<Any>(){});
    	for (Bean<?> bean: beans) {
    		log.info(String.format(
    				"BeanName=%s,Scope=%s", 
    				bean.getBeanClass().getName(), 
    				bean.getScope().getName()));
    	}

    }
}
