package org.home.demo.x001.classes;

import com.google.gson.Gson;
import org.drools.decisiontable.DecisionTableProviderImpl;

import org.home.demo.x001.models.RuleDefinition;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.DecisionTableInputType;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KieSessionUtil {
    private static Gson gson = new Gson();
    private KieServices kieServices = KieServices.Factory.get();

    public KieSession getKieSession1(String ruleFileName) throws IOException {
        KieFileSystem  kieFileSystem = getKieFileSystem(ruleFileName);
        KieBuilder     kieBuilder    = kieServices.newKieBuilder(kieFileSystem);
        Results        bldResults    = kieBuilder.buildAll().getResults();
        if (bldResults.hasMessages()) {
            for (Message bldrMessage : bldResults.getMessages()) {
                System.out.println(bldrMessage.getText());
            }
        }
        KieRepository  kieRepository      = kieServices.getRepository();
        ReleaseId      krDefaultReleaseId = kieRepository.getDefaultReleaseId();
        KieContainer   kieContainer       = kieServices.newKieContainer(krDefaultReleaseId);
        KieSession     kieSession         = kieContainer.newKieSession();
        kieSession.addEventListener( new DefaultAgendaEventListener() {
            public void afterMatchFired(AfterMatchFiredEvent event) {
                super.afterMatchFired( event );
                System.out.println( event );
            }
        });
        return kieSession;
    }
    private KieFileSystem getKieFileSystem(String ruleFileName) {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        Resource dt1 = ResourceFactory.newClassPathResource(ruleFileName,getClass());
        kieFileSystem.write(dt1);
        return kieFileSystem;
    }
    public static int ClearAllFacts(KieSession kieSession) {
        int factCount = kieSession.getFactHandles().size();
        Collection<FactHandle> sessionFacts = kieSession.getFactHandles();
        for (FactHandle factHandle : sessionFacts) {
            kieSession.delete(factHandle);
        }
        return factCount;
    }


    // -----------------------------------------------------------------------------------
    public String getDrlFromExcel(String excelFileName, String sheetName) {
        Resource dt1 = ResourceFactory.newClassPathResource(excelFileName,getClass());
        DecisionTableConfiguration dtconf = KnowledgeBuilderFactory.newDecisionTableConfiguration();
        dtconf.setInputType( DecisionTableInputType.XLS );
        dtconf.setWorksheetName(sheetName);
        DecisionTableProviderImpl decisionTableProvider = new DecisionTableProviderImpl();
        String discountXLSdrl = decisionTableProvider.loadFromResource(dt1, dtconf);
        return discountXLSdrl;
    }

    public List<RuleDefinition> GetAllRulesNames(KieSession ksession) {
        List<RuleDefinition> rtnList = new ArrayList<>();

        Collection<KiePackage> kiePackages = ksession.getKieBase().getKiePackages();

        for( KiePackage kPkg: kiePackages ){
            Collection<Rule> tmp_rules = kPkg.getRules();
            for ( Rule rule : tmp_rules) {
                RuleDefinition ruleDefinition = new RuleDefinition(rule);
                rtnList.add(ruleDefinition);
                System.out.println(gson.toJson(ruleDefinition));
            }
        }
        return rtnList;
    }

}
