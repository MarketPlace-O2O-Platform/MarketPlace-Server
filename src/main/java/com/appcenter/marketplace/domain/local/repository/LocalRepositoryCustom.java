package com.appcenter.marketplace.domain.local.repository;

import com.appcenter.marketplace.domain.local.Local;

public interface LocalRepositoryCustom {

    Local findByAdress(String metroGovern, String localGovern);
}
