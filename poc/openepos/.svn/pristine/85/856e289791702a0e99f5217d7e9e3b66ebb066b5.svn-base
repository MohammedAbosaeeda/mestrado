package test;

abstract class Field {
	abstract boolean getBoolean(Object obj);
}

abstract public class VStack {

	public abstract Field getField(Class clazz, String fname);

	private boolean getBooleanField(Object obj, Class klass, String field_name) throws Exception {
		try
		{
			Field f = getField(klass, field_name);
			boolean b = f.getBoolean(obj);
			return b;
		}
		catch (Exception _)
		{
			throw new Exception("Unexpected exception " + _);
		}
	}
}
