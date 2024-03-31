package org.home.demo.x001.classes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.home.demo.x001.models.TaxiRide;
import org.kie.api.runtime.KieSession;
import org.home.demo.x001.models.TestDataObject;
import org.kie.api.runtime.rule.FactHandle;

import java.time.LocalDate;
import java.util.Collection;

public class TestCases {
    static Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
            .create();

    public static void TestFieldsAll(KieSession kieSession) {
        TestFieldsSet01(kieSession);
        TestFieldsSet02(kieSession);
        TestFieldsSet03(kieSession);
    }

    public static void TestFieldsSet01(KieSession kieSession) {
        System.out.println( "---- TestFieldsSet01 starting" );
        KieSessionUtil.ClearAllFacts(kieSession);
        TestDataObject testDataObject = new TestDataObject();
        testDataObject.setTest_field1("X1");
        kieSession.insert(testDataObject);
        int rules_fired = kieSession.fireAllRules();
        System.out.println( "rules fired  : " + rules_fired );
        DisplayResults(kieSession, "Test fields rules");
        System.out.println( "---- TestFieldsSet01 completed" );
    }

    public static void TestFieldsSet02(KieSession kieSession) {
        System.out.println( "---- TestFieldsSet02 starting" );
        KieSessionUtil.ClearAllFacts(kieSession);
        TestDataObject testDataObject = new TestDataObject();
        testDataObject.setTest_field1("T2");
        testDataObject.setTest_field2(3);
        testDataObject.setTest_field4(LocalDate.parse("2024-12-31"));

        kieSession.insert(testDataObject);
        int rules_fired = kieSession.fireAllRules();
        System.out.println( "rules fired  : " + rules_fired );
        DisplayResults(kieSession, "Test fields rules");
        System.out.println( "---- TestFieldsSet02 completed" );
    }

    public static void TestFieldsSet03(KieSession kieSession) {
        System.out.println( "---- TestFieldsSet03 starting" );


        KieSessionUtil.ClearAllFacts(kieSession);

        TestDataObject testDataObject = new TestDataObject();
        testDataObject.setTest_field1("T3");
        testDataObject.setTest_field2(34);
        testDataObject.setTest_field4(LocalDate.parse("2024-01-01"));
        kieSession.insert(testDataObject);
        int rules_fired = kieSession.fireAllRules();

        System.out.println( "rules fired  : " + rules_fired );
        DisplayResults(kieSession, "Test fields rules");
        System.out.println( "---- TestFieldsSet03 completed" );
    }

    public static void TestTaxiRide01(KieSession kieSession) {
        System.out.println( "---- TestTaxiRide01 starting" );
        KieSessionUtil.ClearAllFacts(kieSession);
        TaxiRide taxiRide = new TaxiRide();
        taxiRide.setPerMileCharge(2L);
        taxiRide.setIsNightSurcharge(false);
        taxiRide.setDistanceInMile(9L);
        taxiRide.setCustomerName("John");

        kieSession.insert(taxiRide);
        int rules_fired = kieSession.fireAllRules();
        System.out.println("rules fired  : " + rules_fired);

        DisplayResults(kieSession, "Taxi rules");
        System.out.println( "---- TestTaxiRide01 completed" );
    }

    public static void DisplayResults(KieSession kieSession, String msg) {
        Collection<FactHandle> sessionFacts = kieSession.getFactHandles();
        for (FactHandle factHandle : sessionFacts) {
            Object sessionObject = kieSession.getObject(factHandle);
            String objectName    = sessionObject.getClass().getName();
            String className     = objectName.split("\\.")[objectName.split("\\.").length-1];
            System.out.println(msg + " : " + className + gson.toJson(sessionObject));
        }
    }
}
