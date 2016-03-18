

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyFirstClass {
	
	
	public static boolean isconstant(String a){
		if(a.length()>1)
			return false;

		char ans = a.charAt(0);
		if(Character.isUpperCase(ans))
			return true;
		else 
			return false;
	}
	
	public static boolean isvar(String a){
		
		if(a.length()>1)
			return false;
		char ans = a.charAt(0);
		if(Character.isLowerCase(ans))
			return true;
		else
			return false;
	}


	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String a = in.nextLine();
		
		String b = in.nextLine();

		//System.out.println(a);
		//System.out.println(b);
		
		//spliti(a);
		
		String result = recur(a,b);
		
		if(result.contains("FAIL"))
			System.out.println("NOT POSSIBLE");
		
		else
		System.out.println("final ans is {" + result + "}");
		
	}

	private static String recur(String a, String b) {
		
		String p=a.substring(1,a.length()-1);
		String q=b.substring(1,b.length()-1);
		//System.out.println(p + " btw " + q);
		
		String[] x= spliti(p);
		String[] y =spliti(q);
		
		String res="";

		if(!(x[0].equals(y[0])))
			{
			res=res+"FAIL";
				
				}
		
		
		
		if(x.length != y.length)
			res+="FAIL";
		else{
			int i=0;
			while(i<x.length){
				
			
			//System.out.println("to be checked " + x[i] + y[i]);
		
		if(isconstant(x[i]) && isconstant(y[i])){
			if(!x[i].equals(y[i])){
			
				res+="FAIL"; 
				return res;
		}
		}else if(isvar(x[i])){
			int flag=0;
			//System.out.println("inside1");
			if(!y[i].equals(x[i])){			
				for(String temp : y)
					if(ispresent(x[i],temp))
						flag=1;
				if(flag==1){
					res+= "FAIL";
					return recur(b,a);}
				else{
				res+=y[i] + "/" + x[i] + " ";
				//System.out.println("applying\n");
				a=apply(a,y[i],x[i]);
				b=apply(b,y[i],x[i]);
				//System.out.println("wats");
				//System.out.println(a);
				//System.out.println(b);
				String rew = recur(a,b);
				return res+rew;
				
				}
				}
			
		}else if(isvar(y[i])){
			int flag=0;
			//System.out.println("inside2");
			
			if(!y[i].equals(x[i])){			
			for(String temp : x)
				if(ispresent(y[i],temp))
					flag=1;
			if(flag==1){
				res+= "FAIL";
				return recur(b,a);}
			else{
			res+=x[i] + "/" + y[i] + " ";
			a=apply(a,x[i],y[i]);
			b=apply(b,x[i],y[i]);
			//System.out.println("wats");
			//System.out.println(a);
			//System.out.println(b);
			String rew = recur(a,b);
			
			res+= rew;
			return res;
			

		
			
			}
			}
			
		}else if(isfun(x[i])){
			//System.out.println("inside function");
			//System.out.println("in uns" + x[i] + y[i]);
			String r1=x[i].substring(1,x[i].length());
			String r2=y[i].substring(1,y[i].length());
			
			String r3[] = spliti(r1);
			String r4[]= spliti(r2);
			
			//System.out.println(r3[0] + r4[0]);
			
			if(r3[0].equals(r4[0])){
			String type= recur(x[i],y[i]);
			res+= type;
			a=apply(a,x[i],y[i]);
			b=apply(b,x[i],y[i]);
		
		}
			else
				return "FAIL";
		}
		
		i++;
		}
	
	
	}
		//System.out.println(res);
		return res;

	}
	

	private static boolean isfun(String string) {
		if(string.length()>1)
			return true;
		else
			return false;
	}

	private static String apply(String x1, String x2, String x3) {
		
		String xo = x1.substring(1,x1.length()-1);
		
		String[] x5 = spliti(xo);
		String x="(";
		for(int i=0;i<x5.length;i++)
			if(x3.equals(x5[i]))
				x5[i]=x2;
				int i;
				
		for(i=0;i<x5.length-1;i++)
			x=x+x5[i]+",";
		x=x+x5[i];
		x=x+")";
		
		x1=x;
		//System.out.println("new thing is " + x);
		return x;
	}

	private static String next(String a) {
		
		return null;
		
			
	}

	private static boolean ispresent(String a, String b) {
		String parts[] = spliti(b);
		
		for(String temp:parts)
			if(a.equals(temp))
				return true;
		return false;
	}
	public static String[] spliti(String s){
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
				//System.out.println(buff.toString());
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
