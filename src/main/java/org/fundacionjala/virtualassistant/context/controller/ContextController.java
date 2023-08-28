package org.fundacionjala.virtualassistant.context.controller;

import lombok.NonNull;
import org.fundacionjala.virtualassistant.context.controller.Response.ContextResponse;
import org.fundacionjala.virtualassistant.context.exception.ContextException;
import org.fundacionjala.virtualassistant.context.service.ContextService;
import org.fundacionjala.virtualassistant.models.ContextEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;


@RestController
@RequestMapping("/context/")
public class ContextController {
    ContextService contextService;

    public ContextController(ContextService contextService){
        this.contextService =  contextService;
    }

    @GetMapping("{idUser}")
    public List<ContextResponse> getContextByUser(@NonNull @PathVariable("idUser") Long idUser) throws ContextException {
        System.out.println(idUser);
        List<ContextResponse> contexts = contextService.findContextByUserId(idUser);
        return new ResponseEntity<>(contexts, HttpStatus.OK).getBody();
    }

    @PostMapping()
    public ContextEntity getContextByUser(@NotEmpty @RequestBody ContextResponse request)
            throws ContextException {
        ContextEntity context = contextService.saveContext(request);
        return new ResponseEntity<>(context, HttpStatus.OK).getBody();
    }


}
