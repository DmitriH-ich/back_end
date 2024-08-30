package de.ait_tr.g_36.hello;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// url.../hello

@RestController
@RequestMapping("/hello")
public class HelloController {

    private String hello;
    public HelloController(@Value("Hello from Digital Ocean!!!") String hello) {
        this.hello = hello;
    }

    @GetMapping
    public String sayHello() {
        return hello;
    }
}
