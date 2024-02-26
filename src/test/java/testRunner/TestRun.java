package testRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {".//FeatureFiles/"},
		//features = {"@target/rerun.txt"},
		glue = "stepDefinitions"
		)
public class TestRun {

}
