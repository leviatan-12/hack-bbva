package com.idemia.biosmart.scenes.license

import com.morpho.lkms.android.sdk.lkms_core.license.IFeature
import com.morpho.lkms.android.sdk.lkms_core.license.ILkmsLicense
import com.morpho.lkms.android.sdk.lkms_core.license.LicenseStatus

/**
 *  License Models
 *  biosmartapp-android
 *  Created by Alfredo on 15/01/2019.
 *  Copyright (c) 2019 Alfredo. All rights reserved.
 */
class LicenseModels {
    //region License Details
    class LicenseDetails {
        data class Request(val license: ILkmsLicense)
        data class Response(val license: ILkmsLicense)
        data class ViewModel(val licenseId: String,
                             val features: List<IFeature>,
                             val profileId: String,
                             val status: LicenseStatus)
    }
    //endregion
}