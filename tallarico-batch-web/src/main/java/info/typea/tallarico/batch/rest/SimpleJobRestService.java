package info.typea.tallarico.batch.rest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;
import java.util.logging.Logger;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/simplejob")
@RequestScoped
public class SimpleJobRestService {
    @Inject
    private Logger log;

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String execute() throws Exception {
    	
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		long executionId = jobOperator.start("simplejob", new Properties());
		
		BufferedReader reader = new BufferedReader(new FileReader("c:\\work\\test_out.txt"));
		StringBuilder buf = new StringBuilder();
		String line = null;
		while((line = reader.readLine()) != null) {
			buf.append(line).append("\n");
		}
		return buf.toString();
    }
}
