package ar.edu.fiuba.is1.tpg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DocsController {

    @GetMapping({"/docs", "/docs/"})
    public String docs() {
        return "redirect:/swagger-ui.html";
    }
}
