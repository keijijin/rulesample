package com.sample;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sample.fact.ChildFact;
import com.sample.fact.ChildWorkFact;
import com.sample.fact.Contract;
import com.sample.fact.RootFact;

public class FromSampleTest {
	KieSession kSession;
	KieRuntimeLogger logger;

	@Before
	public void setUp() throws Exception {
        // load up the knowledge base
        KieServices ks = KieServices.Factory.get();
	    KieContainer kContainer = ks.getKieClasspathContainer();
    	kSession = kContainer.newKieSession("ksession-rules");
    	
    	logger = ks.getLoggers().newFileLogger(kSession, "audit");
	}
	
	@After
	public void tearDown() {
		logger.close();
	}

	@Test
	public void test() {
		RootFact fact = new RootFact();
		int NUM = 3;
		
		for (int n = 0; n < NUM; n++) {
			ChildFact child = new ChildFact();
			child.setId(n+1);
			child.setAmount(5000 - n);
			ChildWorkFact childWork = new ChildWorkFact();
			childWork.setId(n+1);
			
			fact.getChildFactList().add(child);
			fact.getChildWorkFactList().add(childWork);
		}
		
		Contract c1 = new Contract(19991231, 200);
		Contract c2 = new Contract(19991231, 300);
		Contract c3 = new Contract(20190301, 100);
		Contract c4 = new Contract(20180603, 100);
		Contract c5 = new Contract(20000101, 100);
		//Contract c6 = new Contract(19900101, 250);
		
		List<Contract> list = Arrays.asList(c1, c2, c3, c4, c5);
		
		for(int n = 0; n < NUM; n++) {
			fact.getChildFactList().get(n).setContractList(list);
		}
		
		System.out.println("Before:");
		for (int n = 0; n < fact.getChildFactList().size(); n++) {
			System.out.println("\tSrc => " +fact.getChildFactList().get(n).getId() + "," + fact.getChildFactList().get(n).getAmount());
			for (int i = 0; i < fact.getChildFactList().get(n).getContractList().size(); i++)
				System.out.println("\t\t" + fact.getChildFactList().get(n).getContractList().get(i));
		}
		for (int n = 0; n < fact.getChildWorkFactList().size(); n++) {
			System.out.println("\tWrk => " +fact.getChildWorkFactList().get(n));
		}
		for (int n = 0; n < fact.getChildSortFactList().size(); n++) {
			System.out.println("\tSrt => " +fact.getChildSortFactList().get(n));
		}
		
		kSession.insert(fact);
		kSession.fireAllRules();

		System.out.println("After:");
		for (int n = 0; n < fact.getChildWorkFactList().size(); n++) {
			System.out.println("\t" + fact.getChildWorkFactList().get(n));
		}
		
		assertEquals(3, fact.getChildSortFactList().size());
		assertEquals(200,fact.getChildWorkFactList().get(0).getMinNumber());
	}

	
}
