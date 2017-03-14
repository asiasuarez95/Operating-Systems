import java.util.*;

import org.apache.commons.lang3.ArrayUtils;

public class LinkerHW {

	public static void main(String[] args) {
		System.out.println("Hello. This is the linker program. This program will produce a Memory Map and Symbol Table.");
		System.out.println("Please enter the desired input: ");
		
	//Create new Scanner object.
			Scanner in = new Scanner(System.in);
	//Read in the number of Modules
			int numModules = in.nextInt();
			String blank = in.nextLine();
	//Create array of module objects		
			ModuleHW [] module = new ModuleHW[numModules];

			int defNum = 0; // save first int of line 1
			String[] definition = new String[2]; //save rest of string of line 1
			String tempDef="";
			int useNum = 0; //save first int of line 2
			String[] use = new String[2]; //save rest of string of line 1
			String tempUse = "";
			int textNum	= 0;	
			
	//Store values in object
			for(int i = 0; i < numModules; i++){
				//Read in values
				defNum = in.nextInt();
				tempDef = in.nextLine();
				tempDef= tempDef.trim();
				definition = tempDef.split("\\s+");

				useNum = in.nextInt();
				tempUse = in.nextLine();
				tempUse = tempUse.trim();
				use = tempUse.split("\\s+");
				textNum = in.nextInt();
				int[] text= new int[textNum];
				for(int j=0; j<textNum; j++){
					text[j] = in.nextInt();
				}
				

				//Store values in object 
				module[i] = new ModuleHW(); 
				module[i].setDefNum(defNum);
				module[i].setDefinition(definition);
				module[i].setUseNum(useNum);
				module[i].setUse(use);
				module[i].setTextNum(textNum);
				module[i].setText(text);	
			}

//			System.out.println("\n" + numModules);
//			for(int i=0; i<numModules; i++){
//				module[i].moduleToString(i);
//			}
			
			System.out.println("Passed print modules");
			
			//Deal with Variables and definition
			
			//Determine number of variables
			int numVariables = 0;
			for(int i = 0; i < numModules; i++){
				numVariables = numVariables + module[i].getDefNum();
			}
			//Create variable objects
			variables [] variable = new variables[numVariables];
			for(int i = 0; i < numVariables; i++){
				variable[i] = new  variables();
			}
	//Handle and Clean  string arrays for variable name and value
			//Determine name
			ArrayList<String> convert = new ArrayList<String>();
			for(int i = 0;i<numModules;i++){
				for (int k = 0; k < module[i].getDefinition().length; k++){
					convert.add(module[i].getDefinition()[k]);
				}
			}
			convert.removeAll(Arrays.asList("", null));
			String[] finalDef= convert.toArray(new String[convert.size()]);

			int counter = 0;
			for(int i = 0; i < numVariables; i++){
					if(counter%2 ==0){
						variable[i].setName(finalDef[counter]);
				
					}
					counter = counter +2;
			}
			//Determine value
			ArrayList<String> convertValue = new ArrayList<String>();
			for(int i = 0;i<numModules;i++){
				for (int k = 0; k < module[i].getDefinition().length; k++){
					convertValue.add(module[i].getDefinition()[k]);
				}
			}
			convertValue.removeAll(Arrays.asList("", null));
			String[] finalValue= convertValue.toArray(new String[convertValue.size()]);

			int counterValue = 1;
			for(int i = 0; i < numVariables; i++){
					if(counterValue%2 ==1){
						variable[i].setValue(Integer.parseInt(finalValue[counterValue]));
				
					}
					counterValue = counterValue +2;
			}
			ArrayList<String> errorList = new ArrayList<String>();
			for(int i = 0; i < numModules; i++){
				for(int j = 0; j <numVariables; j++){
					if(ArrayUtils.contains(module[i].getDefinition(), variable[j].getName())){
						variable[j].setModuleNum(i);
						int tempValue =0;
						if(i > 0 ){
							for(int k = 0; k < variable[j].getModuleNum(); k++){
								tempValue = tempValue + module[k].getTextNum();
							}
						}
						else{
							tempValue=0;
						}
						
						
						variable[j].setRelativeValue(tempValue);
						variable[j].setValue(variable[j].getValue()+variable[j].getRelativeValue());
					}
					for(int k = 0; k < numVariables; k++){
						if( j != k){
							if(variable[j].getName().equals(variable[k].getName())){
								variable[j].setOriginal(true);
								variable[k].setDuplicate(true);
								variable[k].setName("");
								variable[k].setValue(0);
								errorList.add("Variable " + variable[j].getName() + " is multiply defined;"
										+ " first value is used.");
							}
						}
					}
				}
			}
			for(int i = 0; i < numVariables; i++){
				if(variable[i].getOriginal()){
					variable[i].setValue(variable[i].getValue()-variable[i].getRelativeValue());
				}
			}
			ArrayList<String> variableList = new ArrayList<String>();
			//Handle and Clean  string arrays for variable usage
			ArrayList<String> convertUse = new ArrayList<String>();
			for(int i = 0;i<numModules;i++){
				for (int k = 0; k < module[i].getUse().length; k++){
					convertUse.add(module[i].getUse()[k]);
				}
			}
			convertUse.removeAll(Arrays.asList("", null));
			String[] finalUse= convertUse.toArray(new String[convertUse.size()]);
			String defs = "";
			for(int i = 0; i< numModules; i++){
				for(int j = 0; j< module[i].getDefinition().length; j++){
					defs= defs + module[i].getDefinition()[j];                 
				}
			}
			//defs = defs.replaceAll("\\d","");

				for(int j = 0; j < finalUse.length; j++){
					if(!defs.contains(finalUse[j])){
							errorList.add("Variable " + finalUse[j] + " is not defined; zero used.");
							variableList.add(finalUse[j] + " = 0");
					}
				}
				
			String uses = "";
			for( int i = 0; i < finalUse.length; i=i+2){
				uses = uses + finalUse[i]; 
			}
			for(int j = 0; j < finalDef.length; j= j+2){
				if(!uses.contains(finalDef[j])){
					errorList.add(finalDef[j] + " was defined but never used.");
				}
			}
			
			
			
			System.out.println("Symbol Table:");
			for(int i = 0; i< numVariables; i++){
				if(!variable[i].getName().equals("")){
					variable[i].varToString();
				}
			}
			for(int i = 0; i< variableList.size(); i++){
				System.out.println(variableList.get(i));
			}
			
			//Create Memory Map
			System.out.println("\n"
					+ "Memory Map");
			int baseAddress = 0;
			System.out.println("+" + baseAddress);
			for(int i=0; i<numModules; i++){ // i only refers to # of modules
				for(int j= 0; j<module[i].getTextNum(); j++){ // j only refers to length of text instructions
					System.out.print(j + ":" + "\t"+ module[i].getText()[j]);
					
					//If text instruction is Immediate (aka ending in 1)
					if(module[i].getText()[j] % 10 == 1){
						String temp = String.valueOf(module[i].getText()[j]);
						String temp2 = temp.substring(0, (temp.length()-1));
						int replace = Integer.parseInt(temp2);
						//replace current value stored in module's text array
						module[i].setTextElement(j, replace);
						System.out.print("\t\t\t\t" + module[i].getText()[j]);
					}
					
					//If text instruction is Absolute (aka ending in 2)
					else if(module[i].getText()[j] % 10 == 2){
						String temp = String.valueOf(module[i].getText()[j]);
						String temp2 = temp.substring(0, (temp.length()-1));
						String temp3 = temp.substring(1, (temp.length()-1));
						int memorySize = Integer.parseInt(temp3);
						if (memorySize < 599){
							int replace = Integer.parseInt(temp2);
							//replace current value stored in module's text array
							module[i].setTextElement(j, replace);
							System.out.print("\t\t\t\t" + module[i].getText()[j]);
						}
						else{
							String digit = temp.substring(0,1);
							int replace = Integer.parseInt(digit)*1000;
							errorList.add(module[i].getText()[j] + ": Absolute address exceeds machine size; zero used.");
							module[i].setTextElement(j, replace);
							System.out.print("\t\t\t\t" + module[i].getText()[j]);
						}
					}
					
					//If text instruction is Relative (aka ending in 3)
					else if(module[i].getText()[j] % 10 == 3){
						String temp = String.valueOf(module[i].getText()[j]);
						String temp2 = temp.substring(0, (temp.length()-1));
						String temp3 = temp.substring(1, (temp.length()-1));
						int memorySize = Integer.parseInt(temp3);
						if (memorySize < 599){
							int replace = Integer.parseInt(temp2) + baseAddress;
							module[i].setTextElement(j, replace);
							System.out.print("\t" + temp2 + " + " + baseAddress + " = " +"\t\t" + module[i].getText()[j]);
						}
						else{
							String digit = temp.substring(0,1);
							int replace = Integer.parseInt(digit)*1000;
							errorList.add(module[i].getText()[j] + ": Relative address exceeds machine size; zero used.");
							module[i].setTextElement(j, replace);
							System.out.print("\t\t\t\t" + module[i].getText()[j]);
						}
						
					}
					
					//If text instruction is External (aka ending in 4) Go home and cry...
					else if(module[i].getText()[j] % 10 == 4){
						String temp = String.valueOf(module[i].getText()[j]);
						String temp2 = temp.substring(0, (temp.length()-1));
						int replace = Integer.parseInt(temp2);
						int relativeValue = 0;
						String tempVariable  = "";
						if(replace % 10 > module[i].getUse().length){
							errorList.add(module[i].getText()[j] + ": External address exceeds length of "
									+ "use list; treated as immediate.");
							module[i].setTextElement(j, replace);
							System.out.print("\t\t\t\t" + module[i].getText()[j]);
							
						}
						else{
							for(int k = 0; k < module[i].getUse().length; k ++){
								int tempNumber = replace % 10;
								String tempVar = module[i].getUse()[tempNumber];
								for(int m = 0; m < numVariables; m++){
									if(tempVar.equals(variable[m].getName())){
										relativeValue = variable[m].getValue();
										tempVariable = variable[m].getName();
									}
								}
								for(int n = 0; n < convertUse.size(); n++){
									if (tempVar.equals(convertUse.get(n))){
										tempVariable = convertUse.get(n);
									}
								}
							}
							replace = replace + relativeValue;
							module[i].setTextElement(j, replace);
							System.out.print("\t->"+ tempVariable + "\t\t\t" + module[i].getText()[j]);
						}
					}
					
					//End of Symbol Table Line!
					System.out.print("\n");
					if (j == (module[i].getTextNum() -1) && i != (numModules-1)){
						baseAddress= baseAddress+ module[i].getTextNum();
						System.out.println("+" + baseAddress);
					}
				}
			}
			System.out.println("Test");
			
			for(int i = 0; i < errorList.size(); i++){
				System.err.println(errorList.get(i));
			}
			
	}

}

