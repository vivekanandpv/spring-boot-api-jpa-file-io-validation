package com.vivekanandpv.springbootapijpafileiovalidation.apis;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vivekanandpv.springbootapijpafileiovalidation.services.CustomerService;
import com.vivekanandpv.springbootapijpafileiovalidation.viewmodels.CustomerCreateViewModel;
import com.vivekanandpv.springbootapijpafileiovalidation.viewmodels.CustomerUpdateViewModel;
import com.vivekanandpv.springbootapijpafileiovalidation.viewmodels.CustomerViewModel;
import io.swagger.annotations.Api;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
@CrossOrigin
@Api(value = "customers", tags = {"Customer API"})
public class CustomerApi {
    private final CustomerService customerService;


    public CustomerApi(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerViewModel>> getAll() {
        return ResponseEntity.ok(customerService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerViewModel> getById(@PathVariable int id) {
        return ResponseEntity.ok(customerService.getById(id));
    }

    @GetMapping("documents/identity-proof/{id}")
    public ResponseEntity<Resource> getIdentityProofById(@PathVariable int id) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(customerService.getIdentityProof(id));
    }

    @GetMapping("documents/address-proof/{id}")
    public ResponseEntity<Resource> getAddressProofById(@PathVariable int id) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(customerService.getAddressProof(id));
    }

    @PostMapping
    public ResponseEntity<CustomerViewModel> create(
            @RequestParam(required=false, value="identity-proof") MultipartFile identityProof,
            @RequestParam(required=false, value="address-proof") MultipartFile addressProof,
            String customer
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        var viewModel = objectMapper.readValue(customer, CustomerCreateViewModel.class);

        if (identityProof != null && !identityProof.isEmpty()) {
            viewModel.setIdentityProof(identityProof);
        }

        if (addressProof != null && !addressProof.isEmpty()) {
            viewModel.setAddressProof(addressProof);
        }

        return ResponseEntity.ok(customerService.create(viewModel));
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomerViewModel> update(@PathVariable int id, @Valid @RequestBody CustomerUpdateViewModel viewModel) {
        return ResponseEntity.ok(customerService.update(id, viewModel));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
