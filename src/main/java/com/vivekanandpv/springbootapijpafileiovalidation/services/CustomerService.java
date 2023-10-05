package com.vivekanandpv.springbootapijpafileiovalidation.services;

import com.vivekanandpv.springbootapijpafileiovalidation.viewmodels.CustomerCreateViewModel;
import com.vivekanandpv.springbootapijpafileiovalidation.viewmodels.CustomerUpdateViewModel;
import com.vivekanandpv.springbootapijpafileiovalidation.viewmodels.CustomerViewModel;
import org.springframework.core.io.Resource;

import java.util.List;

public interface CustomerService {
    List<CustomerViewModel> getAll();

    CustomerViewModel getById(int id);

    Resource getIdentityProof(int id);

    Resource getAddressProof(int id);

    CustomerViewModel create(CustomerCreateViewModel viewModel);

    CustomerViewModel update(int id, CustomerUpdateViewModel viewModel);

    void deleteById(int id);
}
