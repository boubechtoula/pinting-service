package com.abesoft.printingservice.controllers;

import com.abesoft.printingservice.services.JobsService;
import com.abesoft.printingservice.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author b.walid
 */
@RestController
public class PrintingController {

    private final Logger logger = LoggerFactory.getLogger(PrintingController.class);

    @Autowired
    JobsService jobsService;

    /**
     *
     * @param json
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/repportGenerator", method = RequestMethod.POST)
    public ResponseEntity<String> json(@RequestBody String json) throws Exception {
        if (Util.checkMandatory(json)) {
            return ResponseEntity.badRequest()
                    .body("Format Json incorrect !");
        }
        return jobsService.generate(json);
    }

}
