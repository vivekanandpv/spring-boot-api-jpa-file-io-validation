package com.vivekanandpv.springbootapijpafileiovalidation.services;

import com.vivekanandpv.springbootapijpafileiovalidation.entities.Customer;
import com.vivekanandpv.springbootapijpafileiovalidation.exceptions.DomainException;
import com.vivekanandpv.springbootapijpafileiovalidation.exceptions.RecordNotFoundException;
import com.vivekanandpv.springbootapijpafileiovalidation.repositories.CustomerRepository;
import com.vivekanandpv.springbootapijpafileiovalidation.viewmodels.CustomerCreateViewModel;
import com.vivekanandpv.springbootapijpafileiovalidation.viewmodels.CustomerUpdateViewModel;
import com.vivekanandpv.springbootapijpafileiovalidation.viewmodels.CustomerViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImplementation implements CustomerService {
    private final CustomerRepository customerRepository;
    private final Logger logger;

    public CustomerServiceImplementation(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public List<CustomerViewModel> getAll() {
        return customerRepository
                .findAll()
                .stream()
                .map(this::toViewModel)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerViewModel getById(int id) {
        return toViewModel(getEntityById(id));
    }

    @Override
    public Resource getIdentityProof(int id) {
        var entity = getEntityById(id);
        return getFileFromPath(entity.getIdentityProofPath());
    }

    @Override
    public Resource getAddressProof(int id) {
        var entity = getEntityById(id);
        return getFileFromPath(entity.getAddressProofPath());
    }

    @Override
    public CustomerViewModel create(CustomerCreateViewModel viewModel) {
        var entity = toEntity(viewModel);
        var uuid = UUID.randomUUID();
        entity.setIdentityProofPath(saveFile(viewModel.getIdentityProof(), uuid.toString()).toString());
        entity.setAddressProofPath(saveFile(viewModel.getAddressProof(), uuid.toString()).toString());
        customerRepository.saveAndFlush(entity);
        return toViewModel(entity);
    }

    @Override
    public CustomerViewModel update(int id, CustomerUpdateViewModel viewModel) {
        var entity = getEntityById(id);
        BeanUtils.copyProperties(viewModel, entity);
        customerRepository.saveAndFlush(entity);
        return toViewModel(entity);
    }

    @Override
    public void deleteById(int id) {
        var entity = getEntityById(id);
        customerRepository.delete(entity);
    }

    private CustomerViewModel toViewModel(Customer entity) {
        var viewModel = new CustomerViewModel();
        BeanUtils.copyProperties(entity, viewModel);
        return viewModel;
    }

    private Customer toEntity(CustomerCreateViewModel viewModel) {
        var entity = new Customer();
        BeanUtils.copyProperties(viewModel, entity);
        return entity;
    }

    private Customer getEntityById(int id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Could not find the customer with id: %d", id)));
    }

    private Resource getFileFromPath(String path) {
        try {
            return new InputStreamResource(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            throw new DomainException(String.format("Could not get the file: %s", path));
        }
    }

    private Path saveFile(MultipartFile file, String subDirectory) {
        if (file.isEmpty()) {
            throw new DomainException(String.format("File %s is empty", file.getOriginalFilename()));
        }

        try {
            byte[] fileBytes = file.getBytes();
            Path directoryPath = Paths.get("uploads", subDirectory);

            if (!Files.exists(directoryPath)) {
                Files.createDirectory(directoryPath);
            }

            Path filePath = Paths.get("uploads", subDirectory, file.getOriginalFilename());
            Files.write(filePath, fileBytes);
            return filePath;
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            throw new DomainException(String.format("Error saving file %s", file.getOriginalFilename()));
        }
    }
}
