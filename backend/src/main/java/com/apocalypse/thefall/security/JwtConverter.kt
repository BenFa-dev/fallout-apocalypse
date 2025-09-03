package com.apocalypse.thefall.security

import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.stereotype.Component

@Component
class JwtConverter : Converter<Jwt, AbstractAuthenticationToken> {

    private val jwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()

    override fun convert(jwt: Jwt): AbstractAuthenticationToken {
        val defaultAuthorities: Collection<GrantedAuthority> =
            jwtGrantedAuthoritiesConverter.convert(jwt) ?: emptyList()

        val realmAuthorities = extractRealmRoles(jwt)

        val allAuthorities = (defaultAuthorities + realmAuthorities).toSet()
        return JwtAuthenticationToken(jwt, allAuthorities)
    }

    /**
     * Extracts the roles from the `realm_access.roles` claim and prefixes them with `ROLE_`.
     */
    private fun extractRealmRoles(jwt: Jwt): Collection<GrantedAuthority> {
        val roles = (jwt.getClaimAsMap("realm_access")?.get("roles") as? Collection<*>) ?: emptySet<Any>()
        return roles
            .mapNotNull { it?.toString() }
            .map { role -> SimpleGrantedAuthority("ROLE_$role") }
            .toSet()
    }
}
