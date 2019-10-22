package ndsNormalizer;


public class HandleRepository extends ElementRepositoryBase<HandleData> {

	public HandleRepository(Integer startLevel) {
		super(startLevel,0);
	}

	public HandleData get(String value){
		HandleData res = super.get(value);
		if ( res != null )
			return res;
		res = new HandleData(nextKey());
		super.put(value,res);
		return res;
	}
}
