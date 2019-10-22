package flattener.utilities;

public class ObjectInfo {
	///reference to the object that can be accessed through inspectorFullName
	private Object object;

	///name of the inspector
	private String inspectorFullName;

	
	protected ObjectInfo ( Object obj, String name ){
		object = obj;
		inspectorFullName = name;
	}

	public String getInspectorFullName() {
		return inspectorFullName;
	}

	public Object getObject() {
		return object;
	}
}
