package lt.vu.ads.interceptor;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lt.vu.ads.models.LogEntry;
import lt.vu.ads.models.address.Address;
import lt.vu.ads.repositories.AddressRepository;
import lt.vu.ads.repositories.LogRepository;
import lt.vu.ads.service.SpringContext;
import org.springframework.boot.SpringApplication;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Enumeration;

public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        HandlerMethod method = (HandlerMethod) handler;
        String methodName = method.getMethod().toString();
        String ipAddress = getRemoteAddr(request);
        String requestTargetUri = request.getRequestURI();
        LocalDateTime requestTime = LocalDateTime.now();

        LogRepository logRepository = SpringContext.getBean(LogRepository.class);
        logRepository.save(LogEntry.builder()
                .requestTime(requestTime)
                .ipAddress(ipAddress)
                .requestTarget(requestTargetUri)
                .methodName(methodName)
                .build());
        return true;
    }

    private String getRemoteAddr(HttpServletRequest request) {
        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
        if (ipFromHeader != null && ipFromHeader.length() > 0) {
            System.out.println("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }
}