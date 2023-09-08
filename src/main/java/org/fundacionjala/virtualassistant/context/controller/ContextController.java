package org.fundacionjala.virtualassistant.context.controller;

import lombok.NonNull;
import org.fundacionjala.virtualassistant.context.controller.Request.ContextRequest;
import org.fundacionjala.virtualassistant.context.controller.Response.ContextResponse;
import org.fundacionjala.virtualassistant.context.exception.ContextException;
import org.fundacionjala.virtualassistant.context.exception.ContextRequestException;
import org.fundacionjala.virtualassistant.context.service.ContextService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/context")
public class ContextController {
    private ContextService contextService;

    public ContextController(ContextService contextService) {
        this.contextService = contextService;
    }

    @GetMapping("/user/{idUser}")
    public List<ContextResponse> getContextByUser(@NonNull @PathVariable("idUser") Long idUser) throws ContextException {
        List<ContextResponse> contexts = contextService.findContextByUserId(idUser);
        return new ResponseEntity<>(contexts, HttpStatus.OK).getBody();
    }

    @GetMapping("/{idContext}")
    public ResponseEntity<ContextResponse> findById(@PathVariable Long idContext) throws ContextRequestException {
        Optional<ContextResponse> contextResponse = contextService.findById(idContext);
        return contextResponse.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ContextResponse getContextByUser(@Valid @RequestBody ContextRequest request)
            throws ContextException {
        ContextResponse context = contextService.saveContext(request);
        return new ResponseEntity<>(context, HttpStatus.OK).getBody();
    }

    @PutMapping("/{idContext}")
    public ContextResponse putContext(@NonNull @PathVariable("idContext") Long idContext,
                                      @Valid @RequestBody ContextRequest request) throws ContextException {
        ContextResponse context = contextService.editContext(idContext, request);
        return new ResponseEntity<>(context, HttpStatus.OK).getBody();
    }

    @DeleteMapping("/{idContext}")
    public ResponseEntity<Boolean> deleteContextById(@NonNull @PathVariable("idContext") Long idContext)
            throws ContextException{
        boolean isDelete = contextService.deleteContext(idContext);
        return ResponseEntity.status(HttpStatus.OK).body(isDelete);
    }
}