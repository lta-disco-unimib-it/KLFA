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

