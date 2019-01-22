package com.idemia.biosmart.utils

import com.idemia.biosmart.base.android.BaseActivity

/**
 * Api Url Manager
 */
class ApiUrlManager {
    companion object {
        fun url(activity: BaseActivity): String {
            // Https
            val middlewareHttpsValue = activity.preferenceManager.getBoolean("IDEMIA_KEY_MIDDLEWARE_HTTPS", false)

            // IP
            val middlewareIpValue = activity.preferenceManager.getString("IDEMIA_KEY_MIDDLEWARE_IP_ADDRESS","200.66.76.220")

            // Port
            val middlewarePortValue = activity.preferenceManager.getString("IDEMIA_KEY_MIDDLEWARE_PORT","8081")

            // Middleware name
            val middlewareNameValue = activity.preferenceManager.getString("IDEMIA_KEY_MIDDLEWARE_NAME","idemia")

            return generateUrl(
                middlewareHttpsValue,
                middlewareIpValue!!,
                middlewarePortValue!!,
                middlewareNameValue!!
            )
        }

        /**
         * Generate URL
         * @param useHttps True to use https
         * @param ip IP address or domain name
         * @param port Port
         * @param name Middleware name
         * @return URL generated
         */
        private fun generateUrl(useHttps: Boolean, ip: String, port: String, name: String): String {
            if (useHttps)
                return "https://$ip:$port/$name/"
            return  "http://$ip:$port/$name/"
        }
    }
}