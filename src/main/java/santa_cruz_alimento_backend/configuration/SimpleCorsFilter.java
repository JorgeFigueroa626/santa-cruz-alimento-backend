package santa_cruz_alimento_backend.configuration;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter {

    @Value("${cors.allowed.origins}")
    private String corsAllowedOrigins;


    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;

        String originHeader = request.getHeader("Origin");

        // Convertimos la lista de orígenes permitidos en un ArrayList
        List<String> allowedOrigins = Arrays.asList(corsAllowedOrigins.split(","));

        // Si el origen del request está en la lista permitida, lo agregamos a la respuesta
        if (originHeader != null && allowedOrigins.contains(originHeader)) {
            response.setHeader("Access-Control-Allow-Origin", originHeader);
        }

        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true"); // Habilitar credenciales

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        }else {
            filterChain.doFilter(req, resp);
        }
    }

}
