package ndsNormalizer;


public class XConRepository extends ElementRepositoryBase<XConData> {

	public XConRepository(Integer startLevel) {
		super(startLevel,0);
	}
	
	public XConData get(String value){
		XConData res = super.get(value);
		if ( res != null )
			return res;
		res = new XConData(""+elements.size());
		super.put(value,res);
		return res;
	}

}
