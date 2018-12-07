/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.chilkatsoft;

public class CkSFtpDir {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected CkSFtpDir(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CkSFtpDir obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        chilkatJNI.delete_CkSFtpDir(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public CkSFtpDir() {
    this(chilkatJNI.new_CkSFtpDir(), true);
  }

  public boolean get_LastMethodSuccess() {
    return chilkatJNI.CkSFtpDir_get_LastMethodSuccess(swigCPtr, this);
  }

  public void put_LastMethodSuccess(boolean newVal) {
    chilkatJNI.CkSFtpDir_put_LastMethodSuccess(swigCPtr, this, newVal);
  }

  public int get_NumFilesAndDirs() {
    return chilkatJNI.CkSFtpDir_get_NumFilesAndDirs(swigCPtr, this);
  }

  public void get_OriginalPath(CkString str) {
    chilkatJNI.CkSFtpDir_get_OriginalPath(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String originalPath() {
    return chilkatJNI.CkSFtpDir_originalPath(swigCPtr, this);
  }

  public boolean GetFilename(int index, CkString outStr) {
    return chilkatJNI.CkSFtpDir_GetFilename(swigCPtr, this, index, CkString.getCPtr(outStr), outStr);
  }

  public String getFilename(int index) {
    return chilkatJNI.CkSFtpDir_getFilename(swigCPtr, this, index);
  }

  public String filename(int index) {
    return chilkatJNI.CkSFtpDir_filename(swigCPtr, this, index);
  }

  public CkSFtpFile GetFileObject(int index) {
    long cPtr = chilkatJNI.CkSFtpDir_GetFileObject(swigCPtr, this, index);
    return (cPtr == 0) ? null : new CkSFtpFile(cPtr, true);
  }

  public boolean LoadTaskResult(CkTask task) {
    return chilkatJNI.CkSFtpDir_LoadTaskResult(swigCPtr, this, CkTask.getCPtr(task), task);
  }

}
