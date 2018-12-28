package com.idemia.biosmart.utils

class ApiUrlManager {
    companion object {
        fun url(): String {
            // Https
            val useHttps = ""//getString(R.string.idemia_key_middleware_https)
            val middlewareHttpsValue = false//BioSmartApplication.preferenceManager?.getBoolean(useHttps, false)

            // IP
            val middlewareIp = ""//BioSmartApplication.instance?.getString(R.string.idemia_key_middleware_ip_address)
            val middleareIpValue = "200.66.76.220"//BioSmartApplication.preferenceManager?.getString(middlewareIp,"")

            // Port
            val middlewarePort = ""//BioSmartApplication.instance?.getString(R.string.idemia_key_middleware_port)
            val middlearePortValue = "8082"//BioSmartApplication.preferenceManager?.getString(middlewarePort,"")

            // Middleware name
            val middlewareName = ""//BioSmartApplication.instance?.getString(R.string.idemia_key_middleware_name)
            val middleareNameValue = "idemia"//BioSmartApplication.preferenceManager?.getString(middlewareName,"")

            val url = generateUrl(
                middlewareHttpsValue,
                middleareIpValue,
                middlearePortValue,
                middleareNameValue
            )
            return url
        }


        private fun generateUrl(useHttps: Boolean, ip: String, port: String, name: String): String{
            if (useHttps ){ return "https://$ip:$port/$name/" }
            return  "http://$ip:$port/$name/"
        }
    }
}