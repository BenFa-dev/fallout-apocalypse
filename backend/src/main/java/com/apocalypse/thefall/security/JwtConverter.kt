package com.apocalypse.thefall.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Convertit un JWT en un token d'authentification avec ses rôles.
 */
@Component
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> defaultAuthorities = jwtGrantedAuthoritiesConverter.convert(jwt);
        Collection<GrantedAuthority> realmAuthorities = extractRealmRoles(jwt);

        Set<GrantedAuthority> allAuthorities = new HashSet<>();
        allAuthorities.addAll(defaultAuthorities);
        allAuthorities.addAll(realmAuthorities);

        return new JwtAuthenticationToken(jwt, allAuthorities);
    }

    /**
     * Extrait les rôles du JWT.
     */
    private Collection<GrantedAuthority> extractRealmRoles(Jwt jwt) {
        return Optional.ofNullable(jwt.getClaimAsMap("realm_access"))
                .map(realmAccess -> realmAccess.get("roles"))
                .filter(Collection.class::isInstance)
                .map(roles -> (Collection<?>) roles)
                .orElseGet(Collections::emptySet)
                .stream()
                .map(Object::toString)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }
}