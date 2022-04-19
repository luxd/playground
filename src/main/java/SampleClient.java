import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.CacheControlDirective;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import ca.uhn.fhir.util.StopWatch;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SampleClient {

    public static final String FILE_NAME_FOR_LASTNAMES = "lastNames.txt";

    public static void main(String[] theArgs) {

        // Create a FHIR client
        FhirContext fhirContext = FhirContext.forR4();
        IGenericClient client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4");
        client.registerInterceptor(new IClientInterceptorImpl());
        SampleClientHelper clientHelper = new SampleClientHelper();
        List<String> lastNames = clientHelper.getLastNamesFromFile(FILE_NAME_FOR_LASTNAMES);
        // Search for Patient resources
        for (int i = 1; i<=3; i++) {
            List<Long> responseTimeInMillis = new ArrayList<>();
            boolean isCachedDisabled = i == 3;
            lastNames.forEach(lastName -> {
                IClientInterceptorImpl interceptor = (IClientInterceptorImpl)client.getInterceptorService().getAllRegisteredInterceptors().get(0);
                long startTime = interceptor.getStopWatch().getMillis();
                client.search().forResource("Patient").where(Patient.FAMILY.matches().value(lastName))
                        .returnBundle(Bundle.class)
                        .cacheControl(new CacheControlDirective().setNoCache(isCachedDisabled))
                        .execute();
                long endTime = interceptor.getStopWatch().getMillis();
                responseTimeInMillis.add(endTime - startTime);
            });
            long[] responseTimeInMillisArray = responseTimeInMillis.stream().mapToLong(l->l).toArray();
            System.out.println("Array " + Arrays.toString(responseTimeInMillisArray));
            double averageTime = Arrays.stream(responseTimeInMillisArray).average().orElse(Double.NaN);
            System.out.println("Average Time in Loop " + i + " is : " + averageTime + "ms");
        }
    }


}