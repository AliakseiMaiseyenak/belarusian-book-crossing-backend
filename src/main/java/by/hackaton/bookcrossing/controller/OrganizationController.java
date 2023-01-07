package by.hackaton.bookcrossing.controller;

import by.hackaton.bookcrossing.dto.OrganizationDto;
import by.hackaton.bookcrossing.entity.enums.OrganizationType;
import by.hackaton.bookcrossing.service.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    private OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping
    public ResponseEntity<List<OrganizationDto>> getOrganizations(@RequestParam(required = false) OrganizationType type) {
        List<OrganizationDto> organizations;
        if (type != null) {
            organizations = organizationService.getOrganizationByType(type);
        } else {
            organizations = organizationService.getOrganizations();
        }
        return ok(organizations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDto> getOrganizationById(@PathVariable("id") Long id) {
        return ok(organizationService.getOrganizationById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createOrganization(@RequestBody @Valid OrganizationDto dto) {
        organizationService.createOrganization(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> updateOrganization(@PathVariable("id") Long id, @RequestParam OrganizationDto dto) {
        organizationService.updateOrganization(id, dto);
        return ok().build();
    }

    /*@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable("id") Long id) {
        organizationRepository.deleteById(id);
        return ok().build();
    }*/
}
