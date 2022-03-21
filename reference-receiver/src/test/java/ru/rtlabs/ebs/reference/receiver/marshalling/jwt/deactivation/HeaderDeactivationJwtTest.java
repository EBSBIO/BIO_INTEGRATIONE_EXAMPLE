package ru.rtlabs.ebs.reference.receiver.marshalling.jwt.deactivation;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.testng.annotations.Test;

public class HeaderDeactivationJwtTest {
  @Test
  public void testEquals() {
    // WHEN/THEN
    EqualsVerifier.simple()
                  .forClass(HeaderDeactivationJwt.class)
                  .verify();
  }
}
