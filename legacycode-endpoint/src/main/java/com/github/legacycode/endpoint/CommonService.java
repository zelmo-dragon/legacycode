package com.github.legacycode.endpoint;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import com.github.legacycode.endpoint.internal.AbstractService;
import com.github.legacycode.endpoint.internal.DAO;

@ApplicationScoped
@Transactional
public class CommonService extends AbstractService {

    @Inject
    public CommonService(final EntryManager entryManager, final DAO dao) {
        super(entryManager, dao);
    }
}
