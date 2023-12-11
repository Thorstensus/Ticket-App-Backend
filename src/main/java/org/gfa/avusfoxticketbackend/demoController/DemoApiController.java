package org.gfa.avusfoxticketbackend.demoController;

import org.gfa.avusfoxticketbackend.exeption.ApiRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoApiController {

    @GetMapping("/{exception}")
    public ResponseEntity<Object> throwsException(@PathVariable int exception) {
        if (exception == 0) {
            return ResponseEntity.ok().build();
        } else throw new ApiRequestException("/", "Hehe");
    }
}
