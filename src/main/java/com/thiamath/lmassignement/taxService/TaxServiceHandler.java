package com.thiamath.lmassignement.taxService;

import com.thiamath.lmassignement.taxService.model.LineFormatException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaxServiceHandler {

    private final TaxServiceManager manager;

    public TaxServiceHandler(TaxServiceManager manager) {
        this.manager = manager;
    }

    @PostMapping("process")
    public @ResponseBody
    String process(@RequestBody String input) {
        String output;
        try {
            output = manager.process(input);
        } catch (LineFormatException e) {
            return e.getLocalizedMessage();
        }
        return output;
    }
}
