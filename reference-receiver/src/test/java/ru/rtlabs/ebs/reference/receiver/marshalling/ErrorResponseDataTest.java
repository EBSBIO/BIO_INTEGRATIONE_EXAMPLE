package ru.rtlabs.ebs.reference.receiver.marshalling;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.testng.annotations.Test;

public class ErrorResponseDataTest {
  @Test
  public void testEquals() {
    // WHEN/THEN
    EqualsVerifier.simple()
                  .forClass(ErrorResponseData.class)
                  .verify();
  }
}
