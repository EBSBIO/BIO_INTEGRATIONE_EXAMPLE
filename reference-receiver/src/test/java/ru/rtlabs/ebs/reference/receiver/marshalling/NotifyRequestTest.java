package ru.rtlabs.ebs.reference.receiver.marshalling;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.testng.annotations.Test;

public class NotifyRequestTest {
  @Test
  public void testEquals() {
    // WHEN/THEN
    EqualsVerifier.simple()
                  .forClass(NotifyRequest.class)
                  .verify();
  }
}
