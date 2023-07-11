package com.vojvoda.ecomerceapi.configurations.tenant;

import com.vojvoda.ecomerceapi.core.tenant.Tenant;
import com.vojvoda.ecomerceapi.core.tenant.TenantRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
@RequiredArgsConstructor
@Log4j2
class TenantFilter implements Filter {

    private final TenantRepository tenantRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String tenantFromRequest = req.getHeader("X-TenantID");

        Tenant tenant = tenantRepository.findTenantByName(tenantFromRequest).orElseThrow(
                () -> new TenantNotFoundException("Tenant not found: "+tenantFromRequest));

        if(tenant.getName().equals(tenantFromRequest) &&
                tenant.getDomain().equals(req.getHeader("Host")))
            TenantContext.setCurrentTenant(tenantFromRequest);
        else log.error("Error setting tenant!");
        try {
            chain.doFilter(request, response);
        } finally {
            TenantContext.setCurrentTenant("");
        }
    }
}
