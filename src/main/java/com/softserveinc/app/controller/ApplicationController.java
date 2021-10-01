package com.softserveinc.app.controller;

import com.softserveinc.app.dto.ApplicationDTO;
import com.softserveinc.app.dto.payload.ApiResponseMessage;
import com.softserveinc.app.exceptions.InvalidContentRateException;
import com.softserveinc.app.exceptions.InvalidVersionValue;
import com.softserveinc.app.exceptions.NotEnoughQueryVariables;
import com.softserveinc.app.service.ApplicationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@RestController
public class ApplicationController {

    public final ApplicationServiceImpl applicationService;

    public ApplicationController(ApplicationServiceImpl applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ApplicationDTO>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(applicationService.getAll());
    }

    @PostMapping("/app")
    public ResponseEntity<ApiResponseMessage> save(@RequestBody ApplicationDTO applicationDTO) {
        ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        try {
            applicationService.saveApplication(applicationDTO);
            apiResponseMessage.setSuccess(true);
            apiResponseMessage.setMessage("New application was created!");
        } catch (InvalidContentRateException | InvalidVersionValue e) {
            apiResponseMessage.setSuccess(false);
            apiResponseMessage.setMessage(e.getMessage());
        }
        return ResponseEntity.ok().body(apiResponseMessage);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Integer id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(applicationService.getById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.ok()
                    .body(new ApiResponseMessage(false, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseMessage> deleteById(@PathVariable("id") Integer id) {
        ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        try {
            apiResponseMessage.setSuccess(applicationService.deleteById(id));
            apiResponseMessage.setMessage(String.format("Application %s deleted successfully", id));
        } catch (EntityNotFoundException e) {
            apiResponseMessage.setSuccess(false);
            apiResponseMessage.setMessage(e.getMessage());
        }
        return ResponseEntity.ok().body(apiResponseMessage);
    }

    @GetMapping("/compare")
    public ResponseEntity findNewestApplication(@RequestParam Set<Integer> ids) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(applicationService.getNewestApplication(ids));
        } catch (NotEnoughQueryVariables e) {
            return ResponseEntity.ok().body(new ApiResponseMessage(false, e.getMessage()));
        }
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponseMessage> countWithSpecifiedContentRates(@RequestParam Set<Integer> rates) {
        ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        try {
            apiResponseMessage.setSuccess(true);
            apiResponseMessage.setMessage(String.format("Count of apps with entered content rates =  %s",
                    applicationService.getAmountByContentRates(rates)));
        } catch (EntityNotFoundException e) {
            apiResponseMessage.setSuccess(false);
            apiResponseMessage.setMessage(e.getMessage());
        }
        return ResponseEntity.ok().body(apiResponseMessage);
    }
}
