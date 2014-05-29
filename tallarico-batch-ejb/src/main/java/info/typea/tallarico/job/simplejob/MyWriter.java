package info.typea.tallarico.job.simplejob;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.List;

import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * ItemWriterの実装クラスはItemProcessorを通過したデータをファイルへ出力します。
 * もしチェックポイントが無ければ出力ファイルを上書きし、そうでなければ、ファイルのEOFへの書き込みを再開します。
 * アイテムはチャンクごとに書き込まれます。
 * @see http://docs.oracle.com/javaee/7/tutorial/doc/batch-processing003.htm
 * @see http://kagamihoge.hatenablog.com/entry/2014/04/10/205918
 */
@Dependent
@Named("MyWriter")
public class MyWriter implements javax.batch.api.chunk.ItemWriter {
    private BufferedWriter bwriter;
    
    @Inject
    private JobContext jobCtx;

    @Override
    public void open(Serializable ckpt) throws Exception {
        String fileName = jobCtx.getProperties()
                                .getProperty("output_file");
        bwriter = new BufferedWriter(new FileWriter(fileName, 
                                                    (ckpt != null)));
    }

    @Override
    public void writeItems(List<Object> items) throws Exception {
        for (int i = 0; i < items.size(); i++) {
            String line = (String) items.get(i);
            bwriter.write(line);
            bwriter.newLine();
        }
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return new MyCheckpoint();
    }

	@Override
	public void close() throws Exception {
		bwriter.close();
	}
}