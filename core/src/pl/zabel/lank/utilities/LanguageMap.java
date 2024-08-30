package pl.zabel.lank.utilities;

import java.util.HashMap; 
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class LanguageMap {
	
	public static HashMap<String, String> stringMap;
	
	public static void init()
	{
		stringMap = JsonHandler.loadStrings();
		
	}
	public static String findString(String key)
	{
		 String s = stringMap.getOrDefault(key, " - - ");
		 return s;
	}
	

}
