package pl.snz.pubweb.user.web.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.user.dto.error.ApiError;
import pl.snz.pubweb.user.security.RequestSecurityContextProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler  {

    private final ObjectMapper objectMapper;
    private final RequestSecurityContextProvider requestSecurityContextProvider;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        ApiError apiError = ApiError.securityError("No access to: " + httpServletRequest.getRequestURL());
        final Long id = requestSecurityContextProvider.getPrincipal().getId();
        log.warn("Security event, user " +  id + " tried to invoke " + httpServletRequest.getRequestURL() + " without required privileges" );
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(apiError));
        httpServletResponse.setStatus(403);
    }
}
