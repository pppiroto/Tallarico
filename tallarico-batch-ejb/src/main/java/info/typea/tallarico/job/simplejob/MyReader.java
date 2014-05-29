package info.typea.tallarico.job.simplejob;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;

import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * 以下のItemReader実装クラスは、ジョブが再開された場合には与えられたチェックポイントから入力ファイルの読み込みを続行します。
 * アイテムはテキストファイルの各行に対応します。より複雑なシナリオでは、アイテムは独自のJavaクラスで入力元はデータベース等になります。
 * @see http://docs.oracle.com/javaee/7/tutorial/doc/batch-processing003.htm
 * @see http://kagamihoge.hatenablog.com/entry/2014/04/10/205918
 */
@Dependent
@Named("MyReader")
public class MyReader implements javax.batch.api.chunk.ItemReader {
    private MyCheckpoint checkpoint;
    private BufferedReader breader;
    
    @Inject
    JobContext jobCtx;
    
    public MyReader() {}

    /*
     * Itemを読むためのReaderを準備する
     * 仮引数は、ジョブインスタンスの最終チェックポイント
     */
    @Override
    public void open(Serializable ckpt) throws Exception {
        if (ckpt == null) {
            checkpoint = new MyCheckpoint();
        } else {
            checkpoint = (MyCheckpoint) ckpt;
        }
        String fileName = jobCtx.getProperties()
                                .getProperty("input_file");
        breader = new BufferedReader(new FileReader(fileName));
        for (long i = 0; i < checkpoint.getLineNum(); i++) {
            breader.readLine();
        }
    }

    @Override
    public void close() throws Exception {
        breader.close();
    }

    @Override
    public Object readItem() throws Exception {
    	String line = breader.readLine();
        return line;
    }

	/* 
	 * Readerに対して、、現在のチェックポイントを返す
	 */
	@Override
	public Serializable checkpointInfo() throws Exception {
		return checkpoint;
	}
}