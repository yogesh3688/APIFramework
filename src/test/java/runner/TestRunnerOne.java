package runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/java/features",
		glue = {"stepdefinations"},
		plugin = "json:target/jsonReports/cucumber-report.json",
		dryRun = false
		)

public class TestRunnerOne {

}
