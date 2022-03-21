package ru.rtlabs.ebs.reference.receiver.marshalling;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.testng.annotations.Test;

public class MatcherRequestTest {
  @Test
  public void testEquals() {
    // WHEN/THEN
    EqualsVerifier.simple()
                  .forClass(MatcherRequest.class)
                  .verify();
  }
}
