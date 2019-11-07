package lt.vv.risk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RiskApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContextTest {

	@Test
	public void contextStarts() {
		System.out.println("ads");
	}

}
