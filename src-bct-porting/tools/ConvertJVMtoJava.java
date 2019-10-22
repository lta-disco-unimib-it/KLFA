package tools;
//classe per la conersione di nome file scritti in formato JVM nel corrispondente nome file java
public class ConvertJVMtoJava {
	public static String convert(String value)
	{
		//copio nel vettore caratteri la stringa
		char[] stringa = new char[value.length()];
		for(int index=0;index<value.length();index++)
		{
			stringa[index]=value.charAt(index);
		}
		//sostituisco la parte di stringa "[*" dove * = I o D o F o L o B o C o J o Z con il carattere v
		//in modo da ottenere "v*" dove * = I o D o F o Lo B o C o J o Z con il carattere v
		for(int index=0;index<value.length();index++)
		{
			if((stringa[index]=='I')&&( stringa[index-1]=='['))
			{
				stringa[index-1]='v';
			}
			else if((stringa[index]=='D')&&( stringa[index-1]=='['))
			{
				stringa[index-1]='v';
			}
			else if((stringa[index]=='F')&&( stringa[index-1]=='['))
			{
				stringa[index-1]='v';
			}
			else if((stringa[index]=='L')&&( stringa[index-1]=='['))
			{
				stringa[index-1]='v';
			}
			else if((stringa[index]=='B')&&( stringa[index-1]=='['))
			{
				stringa[index-1]='v';
			}
			else if((stringa[index]=='C')&&( stringa[index-1]=='['))
			{
				stringa[index-1]='v';
			}
			else if((stringa[index]=='J')&&( stringa[index-1]=='['))
			{
				stringa[index-1]='v';
			}
			else if((stringa[index]=='Z')&&( stringa[index-1]=='['))
			{
				stringa[index-1]='v';
			}
		}
		//copio la modifica in una nuova stringa str
		String str = new String(stringa);
		//nei casi previsti sostituisco "v*" dove * = I o D o F o L o B o C o J o Z, con tipo di variabile primitiva
		for(int index=0;index<str.length();index++)
		{
			if(str.charAt(index)=='I'&&(str.charAt(index-1)=='v'))
			{
				str=str.replaceAll("vI","int");
			}
			else if(str.charAt(index)=='D'&&(str.charAt(index-1)=='v'))
			{
				str=str.replaceAll("vD","double");
			}
			else if(str.charAt(index)=='F'&&(str.charAt(index-1)=='v'))
			{
				str=str.replaceAll("vF","float"); 
			}
			else if(str.charAt(index)=='L'&&(str.charAt(index-1)=='v'))
			{
				str=str.replaceAll("vL","long");
			}
			else if(str.charAt(index)=='B'&&(str.charAt(index-1)=='v'))
			{
				str=str.replaceAll("vB","byte");
			}
			else if(str.charAt(index)=='L'&&(str.charAt(index-1)=='v'))
			{
				str=str.replaceAll("vL","long");
			}
			else if(str.charAt(index)=='C'&&(str.charAt(index-1)=='v'))
			{
				str=str.replaceAll("vC","char");
			}
			else if(str.charAt(index)=='J'&&(str.charAt(index-1)=='v'))
			{
				str=str.replaceAll("vJ","long");
			}
			else if(str.charAt(index)=='Z'&&(str.charAt(index-1)=='v'))
			{
				str=str.replaceAll("vZ","boolean");
			}
		}
		return str;
	}
}
