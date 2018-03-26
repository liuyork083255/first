package com.sumscope.cdh.webbond;

import java.security.GeneralSecurityException;

public interface ICryptography {

	String Encrypt(String clearText) throws GeneralSecurityException;
	
	String Decrypt(String cipherText) throws GeneralSecurityException;

}
