package com.idemia.biosmart.utils

import android.content.Context
import java.io.File
import java.io.FileOutputStream

object FilesManager {

    fun saveFile(context: Context, filename: String, data: ByteArray){
        val file = File(context.getExternalFilesDir(null), filename)
        if(!file.exists()){
            saveFile(file, data)
        }else{
            file.delete()
            saveFile(file, data)
        }
    }

    private fun saveFile(file: File, data: ByteArray){
        file.createNewFile()
        val fos = FileOutputStream(file, false)
        fos.write(data)
        fos.flush()
        fos.close()
    }
}