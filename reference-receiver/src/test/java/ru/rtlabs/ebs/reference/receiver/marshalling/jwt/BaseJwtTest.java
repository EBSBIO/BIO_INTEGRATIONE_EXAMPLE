package ru.rtlabs.ebs.reference.receiver.marshalling.jwt;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.testng.annotations.Test;

public class BaseJwtTest {
  @Test
  public void testEquals() {
    // WHEN/THEN
    EqualsVerifier.simple()
                  .forClass(BaseJwt.class)
                  .verify();
  }
}
