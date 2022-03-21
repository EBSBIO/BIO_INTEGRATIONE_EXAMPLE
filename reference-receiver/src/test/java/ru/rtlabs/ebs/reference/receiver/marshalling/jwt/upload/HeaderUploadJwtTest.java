package ru.rtlabs.ebs.reference.receiver.marshalling.jwt.upload;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.testng.annotations.Test;

public class HeaderUploadJwtTest {
  @Test
  public void testEquals() {
    // WHEN/THEN
    EqualsVerifier.simple()
                  .forClass(HeaderUploadJwt.class)
                  .verify();
  }
}
