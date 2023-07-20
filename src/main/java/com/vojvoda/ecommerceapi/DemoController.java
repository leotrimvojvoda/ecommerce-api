package com.vojvoda.ecommerceapi;

import com.vojvoda.ecommerceapi.configurations.security.user.SecurityUser;
import com.vojvoda.ecommerceapi.configurations.tenant.TenantContext;
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
