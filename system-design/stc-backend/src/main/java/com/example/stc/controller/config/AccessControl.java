package com.example.stc.controller.config;

import com.example.stc.database.entity.Permission;
import com.example.stc.database.entity.PermissionLevel;
import com.example.stc.database.repository.PermissionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*")
public class AccessControl extends HttpFilter {

    private final ObjectMapper objectMapper;
    private final PermissionRepository permissionRepository;

    public AccessControl(ObjectMapper objectMapper, PermissionRepository permissionRepository) {
        this.objectMapper = objectMapper;
        this.permissionRepository = permissionRepository;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        record AuthError(String message) {}
        String permissionId = request.getHeader("permissionId");
        if (permissionId == null) {
            response.setContentType("application/json");
            response.setStatus(403);
            response.getOutputStream().print(objectMapper.writeValueAsString(new AuthError("UN-AUTHENTICATED")));
        } else {
            Permission permission = permissionRepository.findById(Long.valueOf(permissionId)).get();
            if (permission.getLevel() == PermissionLevel.VIEW && !request.getMethod().equals("GET")) {
                response.setContentType("application/json");
                response.setStatus(401);
                response.getOutputStream().print(objectMapper.writeValueAsString(new AuthError("UN-AUTHORIZED")));
            } else {
                super.doFilter(request, response, chain);
            }
        }
    }

}
