import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyFirstClass {
	public static String a;
	public static String b;
/*
 * For finding the unifying set for two items of the form f(x) and f(y)
 * Here the algorithm will take input in the form of (f,x) if the actual function is f(x) and (f,(g,x)) if the actual function
 * is of the form f(g(x)).
 * The function will the input and will output the result as a set which unifies the given set of items of functions.
 * The algorithm assumes constats to be capital letters like A,B,C..
 * and variables to be small letters like a,b,c..
 * For example : if the input is f(x) and f(y) then the unifying set will be {y/x}
 *
*/
	
	public static boolean isconstant(String a){
	/*
	* This function takes a string (possibly a character) and checks whether it is a constant or not.	
	* It will return true if it is a constant else return false
	*
	*/
		if(a.length()>1)
			return false;

		char ans = a.charAt(0);
		if(Character.isUpperCase(ans))
			return true;
		else 
			return false;
	}
	
	public static boolean isvar(String a){
	/*
	* This function takes a string (possibly a character) and checks whether it is a variable or not.	
	* It will return true if it is a constant else return false
	*
	*/
		
		if(a.length()>1)
			return false;
		char ans = a.charAt(0);
		if(Character.isLowerCase(ans))
			return true;
		else
			return false;
	}


	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);	//Taking the first item in a
		
		 a = in.nextLine();
		
		 b = in.nextLine();		//Taking the secong item in b
		
		String result = recur(a,b);		/*Calling the procedure recur and passing a and b, which will return
							the result of unification as a string which is stored in the result variable
								*/
		
		//If the result contains FAIL got during recursion, it will print an error

		if(result.contains("FAIL"))			
			System.out.println("NOT POSSIBLE");
		
		else
		System.out.println("final ans is {" + result + "}");
		
	}

	private static String recur(String a, String b) {

		/*
		*Function which recurively calls itself for unifying each of the components of the strings.
		*
		*/

		String p=a.substring(1,a.length()-1);
		String q=b.substring(1,b.length()-1);
		
		String[] x= spliti(p);
		String[] y =spliti(q);

	       /* x and y are string arrays which stores the components of the string inputs a and b obtained by parsing the 
		* strings using the spliti function.
		*
		*/			

		String res="";

		if(!(x[0].equals(y[0])))
			{			//Checking whether function names matches, else concatenate FAIL to the result
			res=res+"FAIL";
			return res;
				
				}
		
		
		
		if(x.length != y.length)	/*Checking whether the number of components in the two string matches otherwise 						concatenate FAIL to the result*/ 
			{res+="FAIL";
			 return res;				
				}		 		
		else{
			int i=0;
			while(i<x.length){
		
		if(isconstant(x[i]) && isconstant(y[i]))
		{
			/*
			*Check whether if both the components of the string considered are constants.
			*If they are constants and both are not equal, no need to unify those components
			*otherwise add FAIL to the result
			*/
			if(!x[i].equals(y[i])){
			
				res+="FAIL"; 
				
		}
		}else if(isvar(x[i])){
			int flag=0;
			/*
			*If the first component is a variable, check whether it is presnt in the second string, if so 
			*add FAIL to the result. Otherwise add unification of changing this component by the other component.
			*Apply the changes to the entire string a and b and call recur to the new string a and b
			*
			*/

			if(!y[i].equals(x[i])){
			

					if(ispresent(x[i],y[i]))
						flag=1;
				if(flag==1){

					res+= "FAIL";

					}
				else{
				res+=y[i] + "/" + x[i] + " ";

				a=apply(a,y[i],x[i]);

				b=apply(b,y[i],x[i]);

				p=a.substring(1,a.length()-1);
				q=b.substring(1,b.length()-1);
		
				x= spliti(p);
				y =spliti(q);

			

				
				
				}
					
				}

			
		}else if(isvar(y[i])){

			/*
			*If the second component is a variable, check whether it is presnt in the second string, if so 
			*add FAIL to the result. Otherwise add unification of changing this component by the other component.
			*Apply the changes to the entire string a and b and call recur to the new string a and b
			*
			*/
			int flag=0;

			
			if(!y[i].equals(x[i])){			

				if(ispresent(y[i],x[i]))
					flag=1;
			if(flag==1){

				res+= "FAIL";

				}
			else{
			res+=x[i] + "/" + y[i] + " ";
			a=apply(a,x[i],y[i]);
			b=apply(b,x[i],y[i]);

			p=a.substring(1,a.length()-1);
			q=b.substring(1,b.length()-1);
		
			x= spliti(p);
			y =spliti(q);
	
			
			}
			}

			
		}else if(isfun(x[i])){

			/*If it is function, check whether both have the same names and if so call the recur function recursively on 
			 *these functions and add the result to the result. Following that apply the changes to the entire string a and b
			 *and continue with unification of the next two components.
			 *
			 */

			String r1=x[i].substring(1,x[i].length());
			String r2=y[i].substring(1,y[i].length());
			
			String r3[] = spliti(r1);
			String r4[]= spliti(r2);
			
			
			if(r3[0].equals(r4[0])){
			String type= recur(x[i],y[i]);
			res+= type;
			if(type!=""){

			String tity[] = type.split("/");

			a=apply(a,tity[0],tity[1]);
			b=apply(b,tity[0],tity[1]);
				}
			p=a.substring(1,a.length()-1);
			q=b.substring(1,b.length()-1);
		
			x= spliti(p);
			y =spliti(q);			
			
		
		}
			else
				return "FAIL";
		}
		
		i++;
		}
	
	
	}
		return res;

	}
	

	private static boolean isfun(String string) {

		/*
		* This is a function to check whether the given string represents a function or not
		* If it is a function, then the size will be greater than 1 ie it will be of the form
		* (f,r,t) where f is the name of the function and r and t are arguments. They themselves can
		* be functions. In that case we will send them back recursively into the recur
		* function and find the answer recursively.
		*/
		if(string.length()>1)
			return true;
		else
			return false;
	}

	private static String apply(String x1, String x2, String x3) {

		/*
		* This is the function to apply the changes made in the recur function to both of the 
		* strings so that it will be reflected in the later iterations.
		*/
	
		
		String x = "";
		for(int i = 0; i<x1.length();i++)
			if(x1.charAt(i)==(x3.charAt(0))){
				x+= x2.charAt(0);

				}
			else
				x+=x1.charAt(i);



		return x;
	}


	private static boolean ispresent(String a, String b) {
		//String parts[] = spliti(b);
		
		//for(String temp:parts)
			if(b.contains(a))
				return true;
		return false;
	}
	public static String[] spliti(String s){

	/*
	* This function is used for splitting the given string in accordance with the input of the 
	* program. For example if the input is (f,(g,x),k) then the output will be a string array of 
	* values f (g,x) and k.
	*/
		int i=0;
		int b=0;
		StringBuilder buff = new StringBuilder();
		List<String> args = new ArrayList<String>();
		for(i=0;i<s.length();i++){
			if(s.charAt(i)=='('){
				b++;
				buff.append(s.charAt(i));
			}
			else if(s.charAt(i)==')'){
				b--;
				buff.append(s.charAt(i));
			}
			else if((s.charAt(i)==',') && (b==0)){

				args.add(buff.toString());
				buff.setLength(0);
				}
			else{
				buff.append(s.charAt(i));
			}
		}
		
		args.add(buff.toString());
		
		String[] argsst = new String[args.size()];
		argsst = args.toArray(argsst);
		
		return argsst;
		
	}
}
