
public class ModuleHW {
	int defNum = 0; // save first int of line 1
	String[] definition; // = new String[2]; //save rest of string of line 1
	int useNum = 0; //save first int of line 2
	String[] use; //= new String[2]; //save rest of string of line 1
	int textNum	= 0;
	int[] text;

	
	public ModuleHW(){
		defNum=this.defNum;
		definition= this.definition;
		useNum=this.useNum;
		textNum= this.textNum;
		text=this.text;
	}
	
	//Setter Methods:
	public void setDefNum( int newDefNum){
		defNum=newDefNum;
	}
	public void setDefinition (String [] newDefinition){
		definition = new String[newDefinition.length];
		for(int i=0; i < definition.length; i++){
			if(newDefinition[i] != ""){
			definition[i]=newDefinition[i];
			}
		}
	 }
	
	public void setUseNum(int newUseNum){
		useNum = newUseNum;
	}
	public void setUse(String [] newUse){
		use = new String [newUse.length];
		for(int i=0; i<use.length; i++){
			use[i] = newUse[i];
		}
	}
	
	public void setTextNum(int newTextNum){
		textNum = newTextNum;
	}

	public void setText(int [] newText){
		text =  new int [textNum];
		for(int i=0; i<textNum; i++){
			text[i] = newText[i];
		}
	}
	
	public void setTextElement(int index, int replacementNum){
		for(int i=0; i<textNum; i++){
			if(index == i){
				text[i] = replacementNum;
			}
		}
	}
	
	//Create getters
	public int getTextNum(){
		return textNum;                   
	}
	
	public int [] getText (){
		return text;
	}
	
	public int getDefNum(){
		return defNum;                   
	}

	public String [] getDefinition(){
		return definition;                   
	}
	
	public String [] getUse(){
		return use;
	}
	
	//Print out module
	public void moduleToString(int i){
		System.out.println(" Module #" + (i+1));
		System.out.print( defNum);
		for(int m = 0; m<definition.length; m++){
			System.out.print(" "+ definition[m] );
		}
		System.out.print("\n" + useNum);
		for(int m =0; m <use.length; m++){
			System.out.print(" "+ use[m] );
		}
		System.out.print("\n" + textNum);
		for(int m =0; m <text.length; m++){
			System.out.print(" "+ text[m] );
		}
		System.out.println("\n ====END MODULE===");
	}
	
}

