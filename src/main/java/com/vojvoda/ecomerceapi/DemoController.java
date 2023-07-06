package com.vojvoda.ecomerceapi;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/demo")
@RequiredArgsConstructor
public class DemoController {

  @GetMapping("/hello")
  public String demo() {
    return "Hello";
  }
}
