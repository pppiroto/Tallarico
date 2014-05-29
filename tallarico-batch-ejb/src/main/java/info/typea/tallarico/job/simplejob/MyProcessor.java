package info.typea.tallarico.job.simplejob;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 * このケースでは、ItemProcessorの実装クラスは読み込んだ行を大文字に変換するだけです。
 * より複雑な例では、様々なやり方でアイテムを処理したり、独自のJavaクラスへ変換したりします。
 * @see http://docs.oracle.com/javaee/7/tutorial/doc/batch-processing003.htm
 * @see http://kagamihoge.hatenablog.com/entry/2014/04/10/205918
 */
@Dependent
@Named("MyProcessor")
public class MyProcessor implements javax.batch.api.chunk.ItemProcessor {
    public MyProcessor() {}

    @Override
    public Object processItem(Object obj) throws Exception {
        String line = (String) obj;
        return line.toUpperCase();
    }
}