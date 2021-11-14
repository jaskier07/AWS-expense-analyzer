package pl.kania.csvDownloader.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
    public class CsvDownloaderRequestHandler implements RequestHandler<Map<String, String>, byte[]> {

    private final static String KEY_FILENAME = "filename";
    private final static CsvDownloader csvDownloader = new CsvDownloader();

    @Override
    public byte[] handleRequest(Map<String, String> input, Context context) {
        input.forEach((key, value) -> log.info(key + " : " + value));

        String filename = Optional.ofNullable(input.get(KEY_FILENAME))
                .orElseThrow(() -> new IllegalArgumentException("Expected key " + KEY_FILENAME + " in request input"));

        log.info("Received request to download object {}", filename);
        return csvDownloader.download(filename)
                .orElseThrow(() -> new IllegalStateException("Cannot download object " + filename));
    }
}
