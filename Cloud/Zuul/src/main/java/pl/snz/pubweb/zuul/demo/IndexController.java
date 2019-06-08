package pl.snz.pubweb.zuul.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
    @RequestMapping
    public String refresh() {
        return "index.html";
    }
}
