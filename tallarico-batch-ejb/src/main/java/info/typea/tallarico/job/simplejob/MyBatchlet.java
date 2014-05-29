package info.typea.tallarico.job.simplejob;

import java.io.File;

import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
@Named("MyBatchlet")
public class MyBatchlet implements javax.batch.api.Batchlet {
    
	@Inject
    private JobContext jobCtx;
    
    @Override
    public String process() throws Exception {
        String fileName = jobCtx.getProperties()
                                .getProperty("output_file");
        System.out.println(""+(new File(fileName)).length());
        return "COMPLETED";
    }

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
	}
}