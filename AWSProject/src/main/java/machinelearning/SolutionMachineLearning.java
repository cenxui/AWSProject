package machinelearning;

import com.amazonaws.services.machinelearning.AmazonMachineLearningClient;

import cenxui.util.aws.AWS;

public class SolutionMachineLearning {

	public static void main(String[] args) {
	
		String trainingDataUrl = "s3://aml-sample-data/banking.csv";
		String schemaFilename = "banking.csv.schema";
		String recipeFilename = "recipe.json";
		String friendlyEntityName = "Java Marketing Sample";
		
		AmazonMachineLearningClient client = new AmazonMachineLearningClient(AWS.getCredentials());

	}

}
