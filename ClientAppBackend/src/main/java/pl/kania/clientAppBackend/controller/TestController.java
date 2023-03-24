package pl.kania.clientAppBackend.controller;

import com.amazonaws.auth.BasicSessionCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kania.clientAppBackend.aws.token.AwsSessionTokenProvider;

@Slf4j
@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class TestController {

    private final AwsSessionTokenProvider sessionTokenProvider;

    @GetMapping("/get")
    public ResponseEntity<String> get() {
        BasicSessionCredentials creds = sessionTokenProvider.getCredentials(); // TODO start here
        log.info(creds.toString());
        return ResponseEntity.ok("OK");
    }
}
