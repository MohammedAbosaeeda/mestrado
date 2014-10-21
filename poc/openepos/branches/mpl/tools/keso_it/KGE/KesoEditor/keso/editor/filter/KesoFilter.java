package keso.editor.filter;

import java.util.Vector;
import keso.editor.data.IKesoData;
import keso.editor.filter.manager.KesoFilterManager;
import keso.editor.filter.operation.*;

/**
 * @author  Wilhelm Haas
 */
public class KesoFilter {

	public static final String GET_ALL = "GET_ALL";
	public static final String GET_ALL_CHILDREN = "GET_ALL_CHILDREN";
	public static final String GET_NEXT_CHILDREN = "GET_NEXT_CHILDREN";
	public static final String GET_NEXT_PARENT = "GET_NEXT_PARENT";
	public static final String GET_PARENT_OF_CLASSTYPE = "GET_PARENT_OF_CLASSTYPE";
	public static final String DELETE_REDUNDANT_OBJECT = "DELETE_REDUNDANT_OBJECT";
	public static final String DELETE_REDUNDANT_NAMES = "DELETE_REDUNDANT_NAMES";
	public static final String DELETE_REDUNDANT_NAMES_OF_SAME_CLASSGROUP = "DELETE_REDUNDANT_NAMES_OF_SAME_CLASSGROUP";
	public static final String UNITE = "UNITE";
	public static final String SUBSTRACT = "SUBSTRACT";
	public static final String FILTER_BY_CLASSTYPE = "FILTER_BY_CLASSTYPE";
	
	private boolean isroot = false;
	private String firstfilter;
	private String secondfilter;
	private String operation;
	private Vector classtypes = new Vector();
	
	public KesoFilter() {
		
	}
	
	public void setRoot() {
		this.isroot = true;
	}
	
	public boolean isRoot() {
		return this.isroot;
	}
	
	public void setFirstFilter(String firstfilter) {
		this.firstfilter = firstfilter;
	}
	
	public String getFirstFilter() {
		return this.firstfilter;
	}
	
	public void setSecondFilter(String secondfilter) {
		this.secondfilter = secondfilter;
	}
	
	public String getSecondFilter() {
		return this.secondfilter;
	}
	
	public void addClassType(String classtype) {
		this.getClassTypes().add(classtype);
	}
	
	public Vector getClassTypes() {
		return this.classtypes;
	}
	
	/**
	 * @param operation  the operation to set
	 * @uml.property  name="operation"
	 */
	public  void setOperation(String operation) {
		this.operation = operation;
	}
	
	/**
	 * @return  the operation
	 * @uml.property  name="operation"
	 */
	public String getOperation() {
		return this.operation;
	}
	
	public Vector filter(IKesoData data) {
		Vector output = new Vector();
		if (this.getFirstFilter() == null && this.isRoot() == false) {
			return new Vector();
		}
		if (this.getSecondFilter() == null) {
			Vector input;
			if (this.isRoot()) {
				input = new Vector();
				input.add(data);
			} else {
				input = KesoFilterManager.get(this.getFirstFilter()).filter(data);
			}
			
			if (this.getOperation().equals(KesoFilter.GET_ALL)) {
				output = GetAllKesoFilterOperation.execute(input);
			} else if (this.getOperation().equals(KesoFilter.GET_NEXT_PARENT)) {
				output = GetNextParentKesoFilterOperation.execute(input);
			} else if (this.getOperation().equals(KesoFilter.GET_ALL_CHILDREN)) {
				output = GetAllChildrenKesoFilterOperation.execute(input);
			} else if (this.getOperation().equals(KesoFilter.GET_NEXT_CHILDREN)) {
				output = GetNextChildrenKesoFilterOperation.execute(input);
			} else if (this.getOperation().equals(KesoFilter.GET_PARENT_OF_CLASSTYPE)) {
				output = GetParentOfTypeKesoFilterOperation.execute(input, (String) this.getClassTypes().get(0));
			} else if (this.getOperation().equals(KesoFilter.DELETE_REDUNDANT_OBJECT)) {
				output = DeleteRedundantObjectsKesoFilterOperation.execute(input);
			} else if (this.getOperation().equals(KesoFilter.DELETE_REDUNDANT_NAMES)) {
				output = DeleteRedundantNamesKesoFilterOperator.execute(input);
			} else if (this.getOperation().equals(KesoFilter.DELETE_REDUNDANT_NAMES_OF_SAME_CLASSGROUP)) {
				output = DeleteRedundantNamesInSameClassGroupKesoFilterOperation.execute(input);
			} else if (this.getOperation().equals(KesoFilter.FILTER_BY_CLASSTYPE)) {
				output = FilterByClassTypeKesoFilterOperation.execute(input, this.getClassTypes());
			}
		} else {
			Vector firstinput = KesoFilterManager.get(this.getFirstFilter()).filter(data);
			Vector secondinput = KesoFilterManager.get(this.getSecondFilter()).filter(data);
			if (this.getOperation().equals(KesoFilter.UNITE)) {
				output = UniteKesoFilterOperation.execute(firstinput, secondinput);
			} else if (this.getOperation().equals(KesoFilter.SUBSTRACT)) {
				output = SubstractKesoFilterOperation.execute(firstinput, secondinput);
			}
		}
		return output;
	}
	
	
}
