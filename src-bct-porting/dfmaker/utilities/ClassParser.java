package dfmaker.utilities;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * This class has the ability to parse a bytecode file and to give the package name.
 * It reads the file through an input stream.
 *
 */
public class ClassParser {
	protected static final int CONSTANT_Class = 7;
	protected static final int CONSTANT_Fieldref = 9;
	protected static final int CONSTANT_Methodref = 10;
	protected static final int CONSTANT_InterfaceMethodref = 11;
	protected static final int CONSTANT_String = 8;
	protected static final int CONSTANT_Integer = 3;
	protected static final int CONSTANT_Float = 4;
	protected static final int CONSTANT_Long = 5;
	protected static final int CONSTANT_Double = 6;
	protected static final int CONSTANT_NameAndType = 12;
	protected static final int CONSTANT_Utf8 = 1;
	
	/**
	 * Returns the package name of the class file that can be read by the input
	 * stream. Returns the empty string if the class is in the default package.
	 * @param ins The input stream.
	 * @throws IOException if the class file is corrupted.
	 * @return The package name.
	 */
	public static String getPackageName(InputStream ins) throws IOException {
		DataInputStream in = null;
		try {
			in = new DataInputStream(ins);
			int magic = in.readInt();
			if (magic != 0xCAFEBABE)
				throw new IOException("Bad magic");
			in.readShort(); // minor
			in.readShort(); // major
			int count = in.readUnsignedShort();
			Object[] pool = new Object[count];
			for (int i = 1; i < count; i++) {
				int tag = in.readUnsignedByte();
				switch (tag) {
					case CONSTANT_Class :
						pool[i] = new Integer(in.readUnsignedShort());
						// name_index
						break;
					case CONSTANT_Fieldref :
					case CONSTANT_Methodref :
					case CONSTANT_InterfaceMethodref :
						in.readShort(); // class index
						in.readUnsignedShort(); // name_and_type index
						break;
					case CONSTANT_String :
						in.readShort(); // string index
						break;
					case CONSTANT_Integer :
					case CONSTANT_Float :
						in.readInt(); // bytes
						break;
					case CONSTANT_Long :
					case CONSTANT_Double :
						i++;
						in.readInt(); // high bytes
						in.readInt(); // low bytes
						break;
					case CONSTANT_NameAndType :
						in.readShort(); // name index
						in.readUnsignedShort(); // descriptor index
						break;
					case CONSTANT_Utf8 :
						pool[i] = in.readUTF();
						break;
					default :
						throw new IOException("Error in class file");
				}
			}
			in.readUnsignedShort(); // access flags
			int thisClass = in.readUnsignedShort();
			Integer nameIndex = (Integer) pool[thisClass];
			String name = (String) pool[nameIndex.intValue()];
			name = name.replace('/', '.');
			int dot = name.lastIndexOf('.');
			if (dot == -1)
				return "";
			String packageName = name.substring(0, dot);
			return packageName;
		} catch (RuntimeException e) {
			throw new IOException("Class file corrupted: " + e.getMessage());
		} finally {
			if (in != null)
				in.close();
		}
	}
}