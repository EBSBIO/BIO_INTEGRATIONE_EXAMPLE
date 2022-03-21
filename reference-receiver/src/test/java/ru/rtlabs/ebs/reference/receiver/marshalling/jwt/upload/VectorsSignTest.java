package ru.rtlabs.ebs.reference.receiver.marshalling.jwt.upload;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.testng.annotations.Test;

public class VectorsSignTest {
  @Test
  public void testEquals() {
    // WHEN/THEN
    EqualsVerifier.simple()
                  .forClass(VectorsSign.class)
                  .verify();
  }
}
