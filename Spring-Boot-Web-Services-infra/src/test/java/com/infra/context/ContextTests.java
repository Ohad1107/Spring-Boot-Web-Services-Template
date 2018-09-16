package com.infra.context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
public class ContextTests {

	private ThreadContext threadContext;

	private UUID partnerId = UUID.randomUUID();
	private String transId = UUID.randomUUID().toString();
	private String userId = UUID.randomUUID().toString();
	private String origin = "TestComponent";
	private String api = "test";

	@Before
	public void setUp() {
		threadContext = new ThreadContext();
		threadContext.setPropertyValue(ContextPropertyType.ORIGIN, origin);
		threadContext.setPropertyValue(ContextPropertyType.API, api);
		threadContext.setPropertyValue(ContextPropertyType.PARTNER_ID, partnerId);
		threadContext.setPropertyValue(ContextPropertyType.EXTERNAL_TRANSACTION_ID, transId);
		threadContext.setPropertyValue(ContextPropertyType.USER_ID, userId);
	}

	@Test
	public void getPropertyValueSuccessTest() {
		ContextHandler.set(threadContext);
		Context context = ContextHandler.get();

		Assert.assertEquals(partnerId, context.getPropertyValue(ContextPropertyType.PARTNER_ID, UUID.class));
		Assert.assertEquals(transId,
		        context.getPropertyValue(ContextPropertyType.EXTERNAL_TRANSACTION_ID, String.class));
		Assert.assertEquals(userId, context.getPropertyValue(ContextPropertyType.USER_ID, String.class));
		Assert.assertEquals(api, context.getPropertyValue(ContextPropertyType.API, String.class));
		Assert.assertEquals(origin, context.getPropertyValue(ContextPropertyType.ORIGIN, String.class));
	}

	@Test
	public void unsetSuccessTest() {
		ContextHandler.set(threadContext);
		ContextHandler.unset();
		Assert.assertNull(ContextHandler.get());
	}

	@Test
	public void toStringSuccessTest() {
		ContextHandler.set(threadContext);
		Assert.assertEquals(String.format("TID=[%s]; API=[%s];", transId, api), ContextHandler.get().toString());
	}

	@Test
	public void toStringNonMandatoryPropertiesSuccessTest() {
		ContextHandler.set(threadContext);
		String toStringResult = ContextHandler.get().toStringNonMandatoryProperties();

		Assert.assertTrue(toStringResult.contains(String.format("UID=[%s];", userId)));
		Assert.assertTrue(toStringResult.contains(String.format("PID=[%s];", partnerId)));
		Assert.assertTrue(toStringResult.contains(String.format("Origin=[%s];", origin)));

		Assert.assertFalse(toStringResult.contains(String.format("TID=[%s];", transId)));
		Assert.assertFalse(toStringResult.contains(String.format("API=[%s];", api)));
	}

	@Test
	public void toStringAllPropertiesSuccessTest() {
		ContextHandler.set(threadContext);
		String toStringResult = ContextHandler.get().toStringAllProperties();

		Assert.assertTrue(toStringResult.contains(String.format("UID=[%s];", userId)));
		Assert.assertTrue(toStringResult.contains(String.format("TID=[%s];", transId)));
		Assert.assertTrue(toStringResult.contains(String.format("API=[%s];", api)));
		Assert.assertTrue(toStringResult.contains(String.format("PID=[%s];", partnerId)));
		Assert.assertTrue(toStringResult.contains(String.format("Origin=[%s];", origin)));
	}
}
