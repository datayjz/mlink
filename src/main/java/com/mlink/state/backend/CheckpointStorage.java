package com.mlink.state.backend;

/**
 * 定义了各StateBackend如何存储state来保证任务容错。
 *
 * 存储后端分为两种：JobManager和FS(HDFS, S3, GCS ...)
 */
public interface CheckpointStorage {

}
