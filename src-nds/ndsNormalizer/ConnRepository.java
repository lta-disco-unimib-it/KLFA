package ndsNormalizer;

public class ConnRepository extends ElementRepositoryBase<ConnData> {

	public ConnRepository(Integer startLevel) {
		super(startLevel,0);
	}
	
	public ConnData get(String value){
		ConnData res = super.get(value);
		if ( res != null )
			return res;
		res = new ConnData(""+elements.size());
		super.put(value,res);
		return res;
	}

}
