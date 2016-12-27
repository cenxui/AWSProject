package s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;

public class DualStackEndpoint {
	
	/*
	 * Before running the code: Fill in your AWS access credentials in the
	 * provided credentials file template, and be sure to move the file to the
	 * default location (/Users/cenxui/.aws/credentials) where the sample code
	 * will load the credentials from.
	 * https://console.aws.amazon.com/iam/home?#security_credential
	 *
	 * WARNING: To avoid accidental leakage of your credentials, DO NOT keep the
	 * credentials file in your source directory.
	 */
	
	private static AWSCredentials getCredentials() {
		/*
		 * The ProfileCredentialsProvider will return your [CKmates (Tokyo)]
		 * credential profile by reading from the credentials file located at
		 * (/Users/cenxui/.aws/credentials).
		 */
		AWSCredentials credentials = null;
		try {
			credentials = new ProfileCredentialsProvider("CKmates (Tokyo)").getCredentials();
		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
					+ "Please make sure that your credentials file is at the correct "
					+ "location (/Users/cenxui/.aws/credentials), and is in valid format.", e);
		}
		return credentials;
	}
	

	public static void main(String[] args) {
		AmazonS3 s3Client = new AmazonS3Client(getCredentials());
		s3Client.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));
		s3Client.setS3ClientOptions(S3ClientOptions.builder().enableDualstack().build());

	}

}
