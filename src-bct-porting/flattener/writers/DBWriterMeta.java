package flattener.writers;

import java.io.IOException;

import database.DataLayerException;
import database.MethodCallMetadata;

public class DBWriterMeta extends DBWriter {
	private String metaInfo;
	
	public DBWriterMeta(String methodSignature, String dbTable,String metaInfo) {
		super(methodSignature, dbTable);
		this.metaInfo = metaInfo;
	}
	
	public void close() throws IOException{
		super.close();
		if(dbTable == "Registration") {
			try {
				MethodCallMetadata.insert(methodSignature, metaInfo);
			} catch (DataLayerException e) {
				e.printStackTrace();
			}
		}
	}
	
}
