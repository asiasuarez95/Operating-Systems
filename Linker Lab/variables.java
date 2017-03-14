
public class variables {
	String name= "";
	int value =0;
	int relativeValue = 0;
	int moduleNum = 0;
	boolean original = false;
	boolean duplicate = false;
	
	public variables(){
		moduleNum = this.moduleNum;
		name = this.name;
		relativeValue = this.relativeValue;
		value =this.value;
		original = this.original;
		duplicate = this.duplicate;
		
		if(original){
			value = value - relativeValue;
		}
		if(duplicate){
			this.value = value + relativeValue;
		}
	}
	
	//Setters
	public void setName(String newName){
		name = newName;
	}
	public void setValue(int newValue){
		value = newValue;
	}
	public void setRelativeValue(int newRelativeValue){
		relativeValue = newRelativeValue;
	}
	public void setModuleNum(int newModuleNum){
		moduleNum = newModuleNum;
	}
	public void setOriginal(boolean orig ){
		original = orig;
		this.value = this.value - this.relativeValue;
	}
	public void setDuplicate(boolean dup){
		duplicate = dup;
	}

	//Getters
	public String getName(){
		return name;
	}
	public int getValue(){
		return value;
	}
	public int getRelativeValue(){
		return relativeValue;
	}
	public int getModuleNum(){
		return moduleNum;
	}
	public boolean getOriginal(){
		return original;
	}
	public boolean getDuplicate(){
		return duplicate;
	}
	
	public void varToString(){
		System.out.println(name + " = " + value);
	}
	
}
