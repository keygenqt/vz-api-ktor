package com.keygenqt.ps.extension

import com.keygenqt.core.utils.ConstantsMime.toMime
import com.keygenqt.ps.db.models.UploadEntity
import com.keygenqt.ps.utils.Constants
import java.io.File
import java.util.*


/**
 * Create upload from data for migrate project
 */
fun String.createFileUploadProject(fileName: String) = createFileUpload("projects", fileName)

/**
 * Create upload from data for migrate article
 */
fun String.createFileUploadArticle(fileName: String) = createFileUpload("articles", fileName)

/**
 * Create upload from data for migrate
 */
private fun String.createFileUpload(type: String, fileName: String): UploadEntity? {
    val file = File("${Constants.PATH_DATA_MIGRATION}/${type}/${this}/${fileName}")
    return if (file.isFile) {
        val upload = UploadEntity.new {
            this.fileName = "${UUID.randomUUID()}.${file.extension}"
            this.fileMime = file.extension.toMime()
            this.originalFileName = fileName
            this.createAt = System.currentTimeMillis()
        }
        file.copyTo(File("${Constants.PATH_UPLOAD}/${upload.fileName}"))
        upload
    } else null
}