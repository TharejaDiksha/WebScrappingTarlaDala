package com.batchTests;

import com.WebScrappingTarlaDala.TestCases.RecipeScraperBase;
import org.testng.annotations.Test;


public class RecipeTestBatch_C extends RecipeScraperBase
{

	@Test(priority=2, dependsOnMethods={"createDriver"}  )
	public void TestBatch_003()
	{
		//Call base method
		ExcelFileName = "RecipeBook_3.xls";//Read from Property File
		//Scrape(11,15);//Read from Property File
		//Scrape(2,2);
		int a = (int)('A');
		int c = (int)('C');
		System.out.println("Executing TestBatch_003");
		Scrape(c-a,c-a);
	}
	
}
