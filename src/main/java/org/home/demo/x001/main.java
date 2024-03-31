package org.home.demo.x001;

import org.home.demo.x001.classes.KieSessionUtil;
import org.home.demo.x001.classes.TestCases;
import org.home.demo.x001.models.RuleDefinition;
import org.kie.api.runtime.KieSession;

import java.io.IOException;
import java.util.List;

import static org.home.demo.x001.classes.TestCases.TestFieldsAll;

public class main {
    private static KieSessionUtil kieSessionUtil = new KieSessionUtil();

    private static String drlFileNameTest = "org/home/demo/x001/TestFields2.drl";
    private static String drlFileNameTaxi = "org/home/demo/x001/TaxiRideFare.drl";
    private static String xlsFileNameTest = "org/home/demo/x001/TestFieldsTable.drl.xls";

    public static void main( String[] args ) throws Exception {
        System.out.println( "App starting" );
        //TaxiFareTests();
        //TestFieldsTestsDrl();
        TestFieldsTestsXls();
        System.out.println( "App completed" );
    }

    public static void TestFieldsTestsXls() throws IOException {
        String drlString = kieSessionUtil.getDrlFromExcel(xlsFileNameTest, "Sheet");
        System.out.println( "Drl String:\n" + drlString);
        KieSession kieSession1 = kieSessionUtil.getKieSession1(xlsFileNameTest);
        List<RuleDefinition> rulesList = kieSessionUtil.GetAllRulesNames(kieSession1);
        System.out.println( "rulesList: \n" + rulesList );
        TestFieldsAll(kieSession1);
    }

    public static void TestFieldsTestsDrl() throws IOException {
        KieSession kieSession1 = kieSessionUtil.getKieSession1(drlFileNameTest);
        List<RuleDefinition> rulesList = kieSessionUtil.GetAllRulesNames(kieSession1);
        System.out.println( "rulesList: \n" + rulesList );
        TestFieldsAll(kieSession1);
    }

    public static void TaxiFareTests() throws IOException {
        KieSession kieSession1 = kieSessionUtil.getKieSession1(drlFileNameTaxi);
        List<RuleDefinition> rulesList = kieSessionUtil.GetAllRulesNames(kieSession1);
        System.out.println( "rulesList: \n" + rulesList );
        TestCases.TestTaxiRide01(kieSession1);
    }

}
