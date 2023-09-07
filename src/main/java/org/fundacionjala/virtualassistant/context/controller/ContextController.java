package org.fundacionjala.virtualassistant.context.controller;

import lombok.NonNull;
import org.fundacionjala.virtualassistant.context.controller.Request.ContextRequest;
import org.fundacionjala.virtualassistant.context.controller.Response.ContextResponse;
import org.fundacionjala.virtualassistant.context.exception.ContextException;
import org.fundacionjala.virtualassistant.context.service.ContextService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/context")
public class ContextController {
    private ContextService contextService;

    public ContextController(ContextService contextService){
        this.contextService =  contextService;
    }

    @GetMapping("/{idUser}")
    public List<ContextResponse> getContextByUser(@NonNull @PathVariable("idUser") Long idUser) throws ContextException {
        List<ContextResponse> contexts = contextService.findContextByUserId(idUser);
        return new ResponseEntity<>(contexts, HttpStatus.OK).getBody();
    }

    @PostMapping()
    public ContextResponse getContextByUser(@Valid @RequestBody ContextRequest request)
            throws ContextException {
        ContextResponse context = contextService.saveContext(request);
        return new ResponseEntity<>(context, HttpStatus.OK).getBody();
    }
}