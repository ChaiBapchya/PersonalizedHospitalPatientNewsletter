package com.messengers.fhir;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.Quantity;
import org.hl7.fhir.dstu3.model.Reference;


public class Sample {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
    
    IGenericClient client = null;

    public Sample(String baseUrl) {
        FhirContext ctx = FhirContext.forDstu3();
        client = ctx.newRestfulGenericClient(baseUrl);
    }
    
        public String addObservation(String patientId, String loincCode, String loincDisplayName,
                                double value, String valueUnit, String valueCode) {
        //Place your code here
        Quantity q = new Quantity();
        Reference r = new Reference();
        r.setReference(patientId);
        //q.setUnit(valueUnit).setValue(value).setCode(valueCode);=
        Observation observation = new Observation();
        observation.getCode().addCoding().setCode(loincCode).setDisplay(loincDisplayName);
        observation.setSubject(r);
        observation.setValue(q);
        observation.setStatus(Observation.ObservationStatus.FINAL);
        MethodOutcome outcome = client.create()
        .resource(observation)
        .prettyPrint()
        .encodedJson()
        .execute();
        String id = outcome.getId().getValue();
        return id;
    }
        
    public String updateObservationValue(String observationId, double value) {
        //Place your code here
        //Observation observation = new Observation();
        Quantity q = new Quantity();
        q.setValue(value);
        //Place your code here
        Observation observation = new Observation();
        observation.setId(observationId);
        observation.getCode().addCoding().setCode(" ").setDisplay("f");
        observation.setValue(q);
        observation.setStatus(Observation.ObservationStatus.FINAL);
        MethodOutcome outcome = client.update()
        .resource(observation)
        .prettyPrint()
        .encodedJson()
        .execute();
        String id = outcome.getId().getValue();
        return id;
    }
        
    
}
