package br.senai.sp.FelipeNicolas.guiadefilmes.util;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class HashUtil {

	public static String hash(String palavra) {
		// "tempero" do hash
		String salt = "b@n@n@";
		// adicionar o "tempero" a palavra
		palavra = salt + palavra;
		String hash = Hashing.sha256().hashString(palavra, StandardCharsets.UTF_8).toString();
		return hash;
	}
}
