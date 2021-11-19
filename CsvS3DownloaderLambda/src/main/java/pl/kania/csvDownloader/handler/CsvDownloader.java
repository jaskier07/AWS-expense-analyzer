package pl.kania.csvDownloader.handler;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PresignedUrlDownloadRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class CsvDownloader {

    private static final String BUCKET_NAME = "expenses-counter-pdf-dataset";
    private final AmazonS3 s3Client;

    public CsvDownloader() {
        log.info("Initializing csv downloader");
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.EU_CENTRAL_1)
                .build();
    }

    Optional<String> download(String filename) {
        try (
                S3Object object = s3Client.getObject(BUCKET_NAME, filename);
                S3ObjectInputStream contentStream = object.getObjectContent()
        ) {
            byte[] contentBytes = IOUtils.toByteArray(contentStream);
            log.info("Downloaded object {} from S3", filename);

            String contentString = new String(contentBytes, Charset.forName("Windows-1250"));
            log.debug(contentString);

            return Optional.of(contentString);
        } catch (Exception e) {
            log.error("Error downloading object " + filename + " from S3", e);
            return Optional.empty();
        }
    }
}
