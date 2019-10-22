/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
package preprocessing;

import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerGlobal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;


public class STideExporter {

	private int[] parameters;
	private String separator=",";
	ValueTransformerGlobal vtg = new ValueTransformerGlobal("");
	
	public STideExporter(int[] parametersPos) {
		parameters = parametersPos;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String[] parameters = args[0].split(",");
		int parametersPos[] = new int[parameters.length];
		for( int i = 0; i < parameters.length; ++i ){
			parametersPos[i]=Integer.valueOf(parameters[i]);
		}
		
		String src = args[1];
		
		String dstPrefix = args[2];
		
		
		
		STideExporter exporter = new STideExporter(parametersPos);
		
		

		try {
			
			if ( args.length == 4 ){
				Properties p = new Properties();
				System.out.println("loading from "+args[3]);
				p.load(new FileInputStream(args[3]));
				Map<String,String> map = new HashMap<String, String>();
				for ( Entry<Object,Object> e : p.entrySet()){
					map.put((String)e.getKey(),(String)e.getValue());
				}
				exporter.loadKeys(map);
			}
			
			exporter.export(src,dstPrefix);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadKeys(Map<String,String> p) {
		vtg.addKeys(p);
	}

	private void export(String src, String dstPrefix) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(src));
		String line;
		BufferedWriter bw = new BufferedWriter(new FileWriter(dstPrefix+".stide.dat"));
		
		while( ( line = br.readLine() ) != null ){
			if ( line.equals("|")){
				 bw.write("1 -1");
			} else{
				String[] elements = line.split(separator);
				StringBuffer sb = new StringBuffer();
				for ( int pos : parameters ){
					sb.append(elements[pos]);
				}
				String parsedValue = vtg.getTransformedValue(sb.toString());
				bw.write("1 "+parsedValue);
			}
			bw.write("\n");
		}
		
		bw.close();
		br.close();
		
		Map<String, String> keys = vtg.getKeys();
		Properties p = new Properties();
		p.putAll(keys);
		FileOutputStream fos = new FileOutputStream(dstPrefix+".stide.trans");
		p.store(fos, "");
		
		fos.close();
	}

}
