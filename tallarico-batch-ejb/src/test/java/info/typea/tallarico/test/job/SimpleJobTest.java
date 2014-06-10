package info.typea.tallarico.test.job;

import info.typea.tallarico.job.util.BatchResource;
import info.typea.tallarico.job.util.Resources;
import info.typea.tallarico.test.util.BatchTestHelper;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.Metric;
import javax.batch.runtime.StepExecution;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SimpleJobTest {

	@Deployment
	public static WebArchive createDeployment() {
		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
				.addClass(BatchTestHelper.class)
				.addPackage("info.typea.tallarico.job.simplejob")
				.addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"))
				.addAsResource("META-INF/batch-jobs/simpleJob.xml")
				.addClass(Resources.class)
				;
		System.out.println(war.toString(true));
		return war;
	}
	
	@Inject
	@BatchResource
	Logger logger;
	
	@Test
	public void simpleJobTest() throws Exception {
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		Long executionId = jobOperator.start("simplejob", new Properties());
		JobExecution jobExecution = jobOperator.getJobExecution(executionId);
		
		BatchTestHelper.keepTestAlive(jobExecution);
		
		List<StepExecution> stepExecutions = jobOperator.getStepExecutions(executionId);
		for (StepExecution stepExecution : stepExecutions) {
			String stepName = stepExecution.getStepName();
			
			switch(stepName) {
			case "mychunk":
			case "mytask":
				Map<Metric.MetricType, Long> metricsMap 
					= BatchTestHelper.getMetricsMap(stepExecution.getMetrics());
                
				logger.info("===== STEP NAME:" + stepName + "=====");
				logger.info("READ COUNT:" + metricsMap.get(Metric.MetricType.READ_COUNT).longValue());
				logger.info("WRITE COUNT:" + metricsMap.get(Metric.MetricType.WRITE_COUNT).longValue());
				logger.info("COMMIT COUNT:" + metricsMap.get(Metric.MetricType.COMMIT_COUNT).longValue());
				break;
			}
		}
	}
}
