/*****************************************************************************************
CategoryParser.java

This class parses the '*_main_manifest' file and creates the '*_category_manifest' file.

This will load a user's saved '*_category_manifest' into working memory (creating objects, etc).

	Methods:
		boolean isPresent(String, String)
		void main(String[]) //unit test method		

*****************************************************************************************/

import java.util.*;
import java.io.*;

class CategoryParser extends Parser 
{
	public static void initialize(String fileName) throws IOException
	{
		CategoryCard.initialize();
		Parser category = new CategoryParser();
		category.load(fileName);
		//UiCli.neutralMessage(MessageType.CompletedParsingCategoryManifest); //concider not showing this alert to user? -- instead write it to a log file with date stamp?
	}

	public void load(String fileName) throws IOException
	{
		//overwrites Parser.load() implementation: doesnt iterate, just preps file

		File f;
  		f = new File(fileName);
  		
  		if (!f.exists())
  		{
  			f.createNewFile();
  		}
	}

	public void update(String line, LineStatus status, Scanner fileScan) 
	{
		Scanner lineScan;
		System.out.println();//eee+
		System.out.println("CategoryParser----in update -- LINE: " + line + "is : " + status.toString());//eee+
		
		lineScan = new Scanner (line);
		lineScan.useDelimiter(", ");

		while (lineScan.hasNext())
		{
			String word;

			word = lineScan.next();
			word = word.replace("[[/Category]]", "");
			word = word.replace("[[Category]]", "");
			System.out.println();//eee+
			System.out.println("CategoryParser---- line is scrubbed, now:" + word);//eee+

			if(!isPresent(word, BookmarkWorkbench.categoryManifest))
			{
				System.out.println();//eee+
		    	System.out.println("CategoryParser---- call add to category manifest.");//eee+
				add(word, BookmarkWorkbench.categoryManifest);
				System.out.println();//eee+
				System.out.println("CategoryParser---- calls invoke");//eee+
				invoke(word);
				//initializes object with category name//load into working memory: categorycard array like manifest (at the end of all entries, searches for Urls to connect)
			}
			else
			{
				if(!isDouble(word, BookmarkWorkbench.categoryManifest))
				{
					System.out.println();//eee+
					System.out.println("CategoryParser---- calls invoke on line already in the manifest.");//eee+
					invoke(word);
				}
			}
		}
	}

	public static ArrayList<String> clean(String line)
	{
		Scanner lineScan;
		ArrayList<String> categories = new ArrayList<String>();
		
		lineScan = new Scanner (line);
		lineScan.useDelimiter(", ");

		while (lineScan.hasNext())
		{
			String word;

			word = lineScan.next();
			word = word.replace("[[/Category]]", "");
			word = word.replace("[[Category]]", "");

			categories.add(word);
		}

		return categories;
	}

	private static void invoke(String title)
	{
		ArrayList<CategoryCard> categoryArray = CategoryCard.getAllCategory();
		System.out.println();//eee+
		System.out.println("CategoryParser---- creates array, which is: " + categoryArray.size());//eee+
		System.out.println();//eee+
		System.out.println("CategoryParser---- creates new category object");//eee+		
		categoryArray.add(new CategoryCard(title));
	}

	public static void associate(ArrayList<UrlCard> urlsList, ArrayList<CategoryCard> categoriesList)
	{
		for (int url = 0; url < urlsList.size(); url++)
		{
			ArrayList<String> urlCategoryList = urlsList.get(url).getCategory();
			
			for (int urlCategory = 0; urlCategory < urlCategoryList.size(); urlCategory++)
			{
				for (int category = 0; category < categoriesList.size(); category++)
				{
					if(categoriesList.get(category).getTitle().equals(urlCategoryList.get(urlCategory)));
						categoriesList.get(category).setUrl(urlsList.get(url));					
				}
			}			
		}

	}

	public static boolean isPresent(String arg, String fileName)
	{
		//scans entire manifest to compare words
		//.equals()
		return true; //finish this
	}

	public static boolean isDouble(String arg, String fileName)
	{
		//scans entire manifest to compare words
		//sets up increment, if it is > 1 --> return true
		return true; //finish this
	}

	//-------------------------------------------------------------------------------
	// CategoryParser.java Unit Tests:
	//-------------------------------------------------------------------------------

	public static void main(String[] args)
	{

	}
}