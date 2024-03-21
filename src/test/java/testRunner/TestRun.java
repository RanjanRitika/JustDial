package testRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {".//FeatureFiles/"},
		//features = {"@target/rerun.txt"},
		glue = "stepDefinitions",
		plugin = {"pretty" , "html:reports/cucumberReport.html","rerun:target/rerun.txt"},		//to generate report
		dryRun = false,  		//checks the mapping/association between step definitions and step methods
		monochrome = true,   	//to avoid junk characters in the output
		publish = true  		//to share the cucumber report using URL and publish it on the server 
		)
public class TestRun {

}
