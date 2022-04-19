import ca.uhn.fhir.interceptor.api.Hook;
import ca.uhn.fhir.interceptor.api.Pointcut;
import ca.uhn.fhir.rest.client.api.IClientInterceptor;
import ca.uhn.fhir.rest.client.api.IHttpRequest;
import ca.uhn.fhir.rest.client.api.IHttpResponse;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import ca.uhn.fhir.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class IClientInterceptorImpl implements IClientInterceptor {
    private static final Logger ourLog = LoggerFactory.getLogger(LoggingInterceptor.class);
    private Logger myLog;
    private final StopWatch stopWatch = new StopWatch();

    public IClientInterceptorImpl() {
        this.myLog = ourLog;
    }

    @Override
    @Hook(Pointcut.CLIENT_REQUEST)
    public void interceptRequest(IHttpRequest theRequest) {
        this.myLog.info("Client request: {}", theRequest);
    }

    @Override
    @Hook(Pointcut.CLIENT_RESPONSE)
    public void interceptResponse(IHttpResponse theResponse) throws IOException {
        String message = "HTTP " + theResponse.getStatus() + " " + theResponse.getStatusInfo();
        String locationValue = " in " + theResponse.getRequestStopWatch().toString();
        this.myLog.info("Client response: {}{}", new Object[]{message, locationValue});
    }

    public StopWatch getStopWatch() {
        return stopWatch;
    }
}
