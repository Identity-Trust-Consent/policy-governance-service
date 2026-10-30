package com.identityos.policy_governance_service.controller;
import com.identityos.policy_governance_service.dto.PolicyEvaluationRequest;
import com.identityos.policy_governance_service.service.PolicyEvaluationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
@RestController
@RequestMapping("/api/v1/policy/integration")
public class IntegrationPolicyController {
  public record Input(@NotNull Map<String,Object> input) {}
  private final PolicyEvaluationService policies;
  public IntegrationPolicyController(PolicyEvaluationService policies) { this.policies=policies; }
  @PostMapping("/evaluate") public Object evaluate(@AuthenticationPrincipal Jwt caller,@Valid @RequestBody Input request) {
    String app=caller.getClaimAsString("application_id");
    if(app==null || !("identityos-"+app+"-service").equals(caller.getClaimAsString("azp")))
      throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Registered service client required");
    return policies.evaluate(new PolicyEvaluationRequest("identityos.integration","decision",request.input()));
  }
}
