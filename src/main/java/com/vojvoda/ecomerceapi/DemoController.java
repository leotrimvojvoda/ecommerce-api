package com.vojvoda.ecomerceapi;

import com.vojvoda.ecomerceapi.configurations.security.user.SecurityUser;
import com.vojvoda.ecomerceapi.configurations.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {

  @GetMapping("/hello")
  public String demo() {

    String tenant = TenantContext.getCurrentTenant();

    return "Hello, "+SecurityUser.getCurrentUser().getEmail() +", from "+ tenant;

  }
}
