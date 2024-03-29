package org.home.demo.x001.classes;

import com.google.gson.Gson;
import org.drools.decisiontable.DecisionTableProviderImpl;
import org.drools.drl.ast.descr.PackageDescr;
import org.drools.drl.parser.DrlParser;
import org.drools.drl.parser.DroolsError;
import org.drools.drl.parser.DroolsParserException;
//import org.example.model.RuleDefinition;
import org.drools.workbench.models.guided.dtable.backend.GuidedDTDRLPersistence;
import org.drools.workbench.models.guided.dtable.backend.GuidedDTXMLPersistence;
import org.drools.workbench.models.guided.dtable.shared.model.GuidedDecisionTable52;
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
import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.DecisionTableInputType;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KieSessionUtil {
    private static Gson gson = new Gson();
    private KieServices kieServices = KieServices.Factory.get();

    public KieSession getKieSession1() throws IOException {
        KieFileSystem  kieFileSystem = getKieFileSystem();
        KieBuilder     kieBuilder    = kieServices.newKieBuilder(kieFileSystem);
        Results        bldResults    = kieBuilder.buildAll().getResults();

        KieRepository  kieRepository      = kieServices.getRepository();
        ReleaseId      krDefaultReleaseId = kieRepository.getDefaultReleaseId();
        KieContainer   kieContainer       = kieServices.newKieContainer(krDefaultReleaseId);
        KieSession     kieSession           = kieContainer.newKieSession();
        kieSession.addEventListener( new DefaultAgendaEventListener() {
            public void afterMatchFired(AfterMatchFiredEvent event) {
                super.afterMatchFired( event );
                System.out.println( event );
            }
        });
        return kieSession;
    }

    private KieFileSystem getKieFileSystem() throws IOException {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

//        Resource dt1 = ResourceFactory.newClassPathResource("TaxiRideFare.drl",getClass());
//        kieFileSystem.write(dt1);
//        Resource dt2 = ResourceFactory.newClassPathResource("Discount.drl.xls",getClass());
//        kieFileSystem.write(dt2);

        Resource dt1 = ResourceFactory.newClassPathResource("org/home/demo/x001/TestFieldsTable.gdst",getClass());
        //Resource dt1 = ResourceFactory.newClassPathResource("org/home/demo/x001/TestFieldsTable.xls",getClass());
        kieFileSystem.write(dt1);

        return kieFileSystem;

    }

    public byte[] GetDrlBytes(String drl_file_name) throws IOException {
        try (InputStream ioStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(drl_file_name)) {
            byte[] drl_bytes = ioStream.readAllBytes();
            return drl_bytes;
        }
    }

    public String GetDrlFromGdst(String gdst_xml) {
        GuidedDecisionTable52 model = GuidedDTXMLPersistence.getInstance().unmarshal( gdst_xml );
        String droolsRules = GuidedDTDRLPersistence.getInstance().marshal( model );
        return droolsRules;
    }

    public static List<String> GetDrlString(String drl_file_name) throws IOException {
        Path filePath = Paths.get(drl_file_name);
        Charset charset = StandardCharsets.UTF_8;
        List<String> lines = Files.readAllLines(filePath, charset);
        return lines;
    }

    public String getDrlFromExcel(String excelFile) {
        DecisionTableConfiguration configuration = KnowledgeBuilderFactory.newDecisionTableConfiguration();
        configuration.setInputType(DecisionTableInputType.XLS);
        Resource dt = ResourceFactory.newClassPathResource(excelFile, getClass());
        DecisionTableProviderImpl decisionTableProvider = new DecisionTableProviderImpl();
        String drl = decisionTableProvider.loadFromResource(dt, configuration);
        return drl;
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

    public static PackageDescr GetDrlParts(String input) throws DroolsParserException {
        DrlParser drlParser = new DrlParser();
        PackageDescr descr = drlParser.parse(true, input);
        if (drlParser.getErrors().size() > 0) {
            for (DroolsError drlParseError : drlParser.getErrors()) {
                System.out.println(gson.toJson(drlParseError.getMessage()));
            }
            throw new DroolsParserException("Parse errors detected");
        }
        return descr;
    }

}
