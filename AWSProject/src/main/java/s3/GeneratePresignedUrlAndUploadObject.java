package s3;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

public class GeneratePresignedUrlAndUploadObject {
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
	
	public static void main(String[] args) throws IOException {
		AWSCredentials credentials = getCredentials();
	
		AmazonS3 s3client = new AmazonS3Client(credentials);
		
		try {
		System.out.println("Generating pre-signed URL.");
		java.util.Date expiration = new java.util.Date();
		long milliSeconds = expiration.getTime();
		milliSeconds += 1000 * 60 * 60 * 24 * 10; // Add 2 day.
		expiration.setTime(milliSeconds);
		
		GeneratePresignedUrlRequest generatePresignedUrlRequest = 
			    new GeneratePresignedUrlRequest("awsconsoletest-cenxui-log", "cenxui2.zip");
		
		generatePresignedUrlRequest.setMethod(HttpMethod.PUT); 
		generatePresignedUrlRequest.setExpiration(expiration);
		
		URL url = s3client.generatePresignedUrl(generatePresignedUrlRequest); 
		UploadObject(url);
		System.out.println("Pre-Signed URL = " + url.toString());
		} catch (AmazonServiceException exception) {
			System.out.println("Caught an AmazonServiceException, " +
					"which means your request made it " +
					"to Amazon S3, but was rejected with an error response " +
			"for some reason.");
			System.out.println("Error Message: " + exception.getMessage());
			System.out.println("HTTP  Code: "    + exception.getStatusCode());
			System.out.println("AWS Error Code:" + exception.getErrorCode());
			System.out.println("Error Type:    " + exception.getErrorType());
			System.out.println("Request ID:    " + exception.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("Caught an AmazonClientException, " +
					"which means the client encountered " +
					"an internal error while trying to communicate" +
					" with S3, " +
			"such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}
	}
	

	public static void UploadObject(URL url) throws IOException
	{
		HttpURLConnection connection=(HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("PUT");
		OutputStreamWriter out = new OutputStreamWriter(
				connection.getOutputStream());
		out.write("This text uploaded as object.");
		out.close();
		int responseCode = connection.getResponseCode();
		System.out.println("Service returned response code " + responseCode);

	}
		
}
