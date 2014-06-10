package info.typea.tallarico.rest;

import info.typea.tallarico.batch.rest.SimpleJobRestService;
import info.typea.tallarico.batch.util.WebResources;
import info.typea.tallarico.job.util.BatchResource;
import info.typea.tallarico.job.util.Resources;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class SimpleJobRestServiceTest {
	@Deployment
	public static WebArchive createDeployment() {
		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage("info.typea.tallarico.job.simplejob")
				.addPackage("info.typea.tallarico.rest")
				.addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"))
				.addAsResource("META-INF/batch-jobs/simpleJob.xml")
				.addClasses(Resources.class)
				.addClasses(WebResources.class)
				;
		System.out.println(war.toString(true));
		return war;
	}
	
	@Inject
	@BatchResource
	Logger logger;
	
	@Inject
	SimpleJobRestService simpleJobService;
	
	@Test
	public void executeTest() throws Exception {
		assertNotNull(simpleJobService);
		
		logger.info("\n== BATCH REST SERVICE RESULT ==\n" 
				+ simpleJobService.execute());
	}
}
