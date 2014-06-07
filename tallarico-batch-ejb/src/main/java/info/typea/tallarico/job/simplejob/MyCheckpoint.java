package info.typea.tallarico.job.simplejob;

import java.io.Serializable;

/**
 * 多くの場合、チャンク指向ステップのためにチェックポイントクラスを実装すべきです。以下のクラスは、テキストファイルの行を保持します。
 * @see http://docs.oracle.com/javaee/7/tutorial/doc/batch-processing003.htm
 * @see http://kagamihoge.hatenablog.com/entry/2014/04/10/205918
 */
@SuppressWarnings("serial")
public class MyCheckpoint implements Serializable {
    
	private long lineNum = 0;
    
    public void increase() { 
    	lineNum++; 
    }
    public long getLineNum() { 
    	return lineNum; 
    }
}