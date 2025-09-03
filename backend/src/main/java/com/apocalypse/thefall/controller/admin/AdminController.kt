package com.apocalypse.thefall.controller.admin

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin")
class AdminController {

    @GetMapping("/test")
    @PreAuthorize("hasRole('ROLE_admin')")
    fun adminEndpoint(): String {
        return "Cet endpoint nécessite le rôle ADMIN"
    }
}
