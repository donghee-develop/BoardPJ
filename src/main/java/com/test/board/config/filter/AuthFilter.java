package com.test.board.config.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.test.board.domain.auth.UserPrincipal;
import com.test.board.domain.user.entity.Role;

public class AuthFilter extends OncePerRequestFilter {
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private final List<String> excludePatterns = List.of("/api/auth/**");

    private final Map<String, List<Role.Roles>> authorizationRules =
            Map.of(
                    "/api/admin/**", List.of(Role.Roles.SUPER_ADMIN),
                    "/api/users/me", List.of(Role.Roles.USER, Role.Roles.SUPER_ADMIN));

    private boolean pathMatches(String pattern, String path) {
        return pathMatcher.match(pattern, path);
    }

    private boolean isExcluded(String requestURI) {
        return excludePatterns.stream().anyMatch(pattern -> pathMatches(pattern, requestURI));
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String requestURI = request.getRequestURI();

        if (isExcluded(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (session == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
            return;
        }

        UserPrincipal userPrincipal = (UserPrincipal) session.getAttribute("user");
        if (userPrincipal == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
            return;
        }

        Optional<Map.Entry<String, List<Role.Roles>>> matchedEntry =
                authorizationRules.entrySet().stream()
                        .filter(entry -> pathMatches(entry.getKey(), requestURI))
                        .findFirst();

        if (matchedEntry.isPresent()) {
            List<Role.Roles> allowedRoles = matchedEntry.get().getValue();
            boolean hasPermission =
                    userPrincipal.getRoles().stream().anyMatch(allowedRoles::contains);

            if (!hasPermission) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "권한이 없습니다.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
