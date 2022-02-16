package com.batchTests;

import com.WebScrappingTarlaDala.TestCases.RecipeScraperBase;
import org.testng.annotations.Test;


public class RecipeTestBatch_B extends RecipeScraperBase
{

	@Test //(priority=2, dependsOnMethods={"createDriver"}  )
	public void TestBatch_002()
	{
		//Call base method
		ExcelFileName = "RecipeBook_B.xls";//Read from Property File
		//Scrape(6,10);//Read from Property File
		int a = (int)('A');
		int start = (int)('B') - a;
		int end = (int)('B') - a;
		System.out.println("Executing TestBatch_002");
		Scrape(start,end);
	}
	
}
