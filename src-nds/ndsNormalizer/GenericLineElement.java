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
package ndsNormalizer;
import java.util.ArrayList;
import java.util.List;


public class GenericLineElement implements LineElement {

	private String name;
	private List<LineData> data = new ArrayList<LineData>();
	
	public GenericLineElement(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addData(LineData elementData ) {
		data.add(elementData);
	}

	public List<LineData> getData() {
		return new ArrayList(data);
	}

	public String toString(){
		StringBuffer msg = new StringBuffer(name);
		if (  data.size() > 0)
			msg.append("_");
		for ( LineData lineData : data ){
			msg.append( "_" );
			msg.append( lineData.toString() );
		}
		return msg.toString();
	}
}

