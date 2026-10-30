package com.identityos.policy_governance_service;
import com.identityos.policy_governance_service.controller.IntegrationPolicyController;
import com.identityos.policy_governance_service.service.PolicyEvaluationService;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class IntegrationPolicyTests {
 @Test void interactiveTokenCannotChooseAPolicy() {
  var service=mock(PolicyEvaluationService.class);var controller=new IntegrationPolicyController(service);
  var user=Jwt.withTokenValue("test").header("alg","RS256").subject("s1").claim("application_id","app1").claim("azp","identityos-app1").build();
  assertThrows(org.springframework.web.server.ResponseStatusException.class,()->controller.evaluate(user,new IntegrationPolicyController.Input(Map.of())));
  verifyNoInteractions(service);
 }
}
