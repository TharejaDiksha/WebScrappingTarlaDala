package com.batchTests;

import com.WebScrappingTarlaDala.TestCases.RecipeScraperBase;
import org.testng.annotations.Test;


public class RecipeTestBatch_E extends RecipeScraperBase
{

	@Test
	public void TestBatch_E()
	{
		//Call base method
		ExcelFileName = "RecipeBook_E-e.xls";//Read from Property File
		//Scrape(3,3);//Read from Property File
		int a = (int)('A');
		int e = (int)('E');
		System.out.println("Executing TestBatch_E");
		Scrape(e-a,e-a);
	}
	
}
