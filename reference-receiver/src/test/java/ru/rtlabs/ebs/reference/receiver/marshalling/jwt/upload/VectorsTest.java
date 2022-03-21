package ru.rtlabs.ebs.reference.receiver.marshalling.jwt.upload;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.testng.annotations.Test;

public class VectorsTest {
  @Test
  public void testEquals() {
    // WHEN/THEN
    EqualsVerifier.simple()
                  .forClass(Vectors.class)
                  .verify();
  }
}
