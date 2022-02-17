package com.batchTests;

import com.WebScrappingTarlaDala.TestCases.RecipeScraperBase;
import org.testng.annotations.Test;


public class RecipeTestBatch_J extends RecipeScraperBase
{

	@Test
	public void TestBatch_S()
	{
		//Call base method
		ExcelFileName = "RecipeBook_J.xls"; //Read from Property file
		//Scrape(21,26);//Read from Property file
		int a = (int)('A');
		int j = (int)('J');
		System.out.println("Executing TestBatch_J");
		Scrape(j-a,j-a);
	}
	
}
