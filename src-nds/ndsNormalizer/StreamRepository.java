package ndsNormalizer;


public class StreamRepository extends ElementRepositoryBase<StreamData> {

	public StreamRepository(Integer startLevel) {
		super(startLevel,2);
	}

	
	public StreamData get(String value){
		if ( value.equals("0") || value.equals("1") )
			return new StreamData ( value );
		
		StreamData res = super.get(value);
		if ( res != null )
			return res;
		res = new StreamData(nextKey());
		super.put(value,res);
		return res;
	}
}
