package cn.edu.cug.cs.gtl.spring.boot.demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class MyRestController {
    @GetMapping("/resthello")
    public String restHello(){
        return "hello world";
    }
    @RequestMapping(value = "/person/{personId}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Person findPerson(@PathVariable("personId") int pid){
        return new Person(pid,"hzw",18);
    }

}
