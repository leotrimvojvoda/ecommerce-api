package com.vojvoda.ecommerceapi.configurations.security.services.jwt;

import com.vojvoda.ecommerceapi.configurations.exceptions.models.TenantException;
import com.vojvoda.ecommerceapi.configurations.tenant.MultiTenantConfiguration;
import com.vojvoda.ecommerceapi.configurations.tenant.TenantContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    private final MultiTenantConfiguration multiTenantConfiguration;

    public JwtServiceImpl(MultiTenantConfiguration multiTenantConfiguration) {
        this.multiTenantConfiguration = multiTenantConfiguration;
    }

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails, String tenant) {
        final String userName = extractUserName(token);
        final String jwtTenant = extractTenant(token);

        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token) && jwtTenant.equals(tenant);
    }

    @Override
    public String extractTenant(String token) {
        return extractClaim(token, Claims::getAudience);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setAudience(TenantContext.getCurrentTenant())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 1000 * 60 * 24)) // 2069
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(getTenantJwtSigningKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getTenantJwtSigningKey() {
        String tenantId = TenantContext.getCurrentTenant();

        if (tenantId == null || tenantId.isBlank())
            throw new TenantException("Error getting tenant jwt signing key");

        return multiTenantConfiguration.getTenantProperties().stream()
                .filter(properties -> tenantId.equals(properties.getProperty("name")))
                .map(properties -> properties.getProperty("token.signing.key"))
                .findFirst()
                .orElse(null);
    }
}