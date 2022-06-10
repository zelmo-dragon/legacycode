package com.github.legacycode.jakarta;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import com.github.legacycode.endpoint.Action;
import com.github.legacycode.endpoint.CommonService;
import com.github.legacycode.endpoint.EndpointEntry;
import com.github.legacycode.endpoint.EndpointManager;
import com.github.legacycode.jakarta.customer.CustomerDTO;
import com.github.legacycode.jakarta.customer.CustomerEntity;
import com.github.legacycode.jakarta.customer.CustomerMapper;
import com.github.legacycode.jakarta.gender.GenderDTO;
import com.github.legacycode.jakarta.gender.GenderEntity;
import com.github.legacycode.jakarta.gender.GenderMapper;

@ApplicationScoped
public class StartUp {

    private final EndpointManager manager;

    @Inject
    public StartUp(final EndpointManager manager) {
        this.manager = manager;
    }

    public void start(@Observes @Initialized(ApplicationScoped.class) final Object pointless) {
        this.manager.register(new EndpointEntry<>("gender", Action.ALL, GenderEntity.class, GenderDTO.class, GenderMapper.class, CommonService.class));
        this.manager.register(new EndpointEntry<>("customer", Action.ALL, CustomerEntity.class, CustomerDTO.class, CustomerMapper.class, CommonService.class));
    }
}
