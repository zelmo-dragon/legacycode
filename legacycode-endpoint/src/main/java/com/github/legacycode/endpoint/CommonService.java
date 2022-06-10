package com.github.legacycode.endpoint;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import com.github.legacycode.endpoint.internal.AbstractService;
import com.github.legacycode.endpoint.internal.DAO;

@ApplicationScoped
@Transactional
public class CommonService extends AbstractService {

    CommonService() {
        super(null, null);
    }

    @Inject
    public CommonService(final EndpointManager endpointManager, final DAO dao) {
        super(endpointManager, dao);
    }
}
