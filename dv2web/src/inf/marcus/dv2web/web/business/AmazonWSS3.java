package inf.marcus.dv2web.web.business;

public final class AmazonWSS3 {
	private String filename;
	public static final String AWS_S3_BUCKET_NAME = "dv2web";
	public static final String AWS_S3_BUCKET_ORIGINAL_SUBDIR = "original";
	public static final String AWS_S3_BUCKET_ENCODED_SUBDIR = "convertido";
	public static final String AWS_S3_KEY = "AKIAJ2PUD2ILBVJYQ5TA";
	public static final String AWS_S3_SECRET = "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";
	public static final String AWS_S3_SECRET_ENCODED = "Zp%2FZw+2SFbcD26pCu2dJs3f7SjrhGvwKjJKWMmDp";
	
	public AmazonWSS3(String fileName){
		this.filename = fileName;
	}
	
	public String getMediaSourceURL() {
//		return "http://" + AmazonWSS3.AWS_S3_KEY + ":" + AmazonWSS3.AWS_S3_SECRET + "@" + AmazonWSS3.AWS_S3_BUCKET_NAME + ".s3.amazonaws.com/" + AmazonWSS3.AWS_S3_BUCKET_ORIGINAL_SUBDIR + "/" + filename;
//		return "http://" + AmazonWSS3.AWS_S3_KEY + ":" + AmazonWSS3.AWS_S3_SECRET + "@" + AmazonWSS3.AWS_S3_BUCKET_NAME + ".s3.amazonaws.com/" + filename;
//		return "http://" + AmazonWSS3.AWS_S3_KEY + ":" + AmazonWSS3.AWS_S3_SECRET_ENCODED + "@" + AmazonWSS3.AWS_S3_BUCKET_NAME + ".s3.amazonaws.com/" + filename;
		return "http://"+AmazonWSS3.AWS_S3_BUCKET_NAME+".s3.amazonaws.com/" + filename;
	}
	
	public String getMediaDestinationURL() {
//		return "http://"+AmazonWSS3.AWS_S3_KEY + ":" + AmazonWSS3.AWS_S3_SECRET_ENCODED + "@" + AWS_S3_BUCKET_NAME + ".s3.amazonaws.com/" + AWS_S3_BUCKET_ENCODED_SUBDIR + "/" + filename + "?acl=public-read";
		return "http://"+AWS_S3_BUCKET_NAME+".s3.amazonaws.com/converted_"+filename+"?acl=public-read";
	}
}
