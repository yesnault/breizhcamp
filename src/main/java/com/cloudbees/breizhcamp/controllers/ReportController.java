package com.cloudbees.breizhcamp.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Controller
public class ReportController {

    @RequestMapping(value = "/programme.pdf", method = RequestMethod.GET, produces = "application/pdf")
    protected ModelAndView programme() throws Exception {

        URL tmUrl = new URL("http://app.breizhcamp.cloudbees.net/?hide=true");
        BufferedReader reader = new BufferedReader(new InputStreamReader(tmUrl.openStream()));

        String line = null;
        String page="";
        while ((line = reader.readLine()) != null) {
            page+=line;
        }

        return new ModelAndView("pdfViewResolver", "data", page);

    }
}
