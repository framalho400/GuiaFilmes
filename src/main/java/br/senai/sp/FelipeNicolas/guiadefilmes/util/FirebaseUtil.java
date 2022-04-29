package br.senai.sp.FelipeNicolas.guiadefilmes.util;

import java.io.IOException;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;


@Service
public class FirebaseUtil {
	// variavel para guardar as credenciais de acesso
	private Credentials credenciais;
	//variavel para 
	private Storage storage;
	//constante para o nome do bucket 
	private final String BUCKET_NAME = "guiafilme-feh.appspot.com";
	//constante para o prefixo da URL
	private final String PREFIX = "https://firebasestorage.googleapis.com/v0/b/"+ BUCKET_NAME+"/o/";
	//constante para o sufix da url
	private final String SUFFIX = "?alt=media";
	//constante para a URl
	private final String DOWNLOAD_URL = PREFIX + "%s"+ SUFFIX;

	public FirebaseUtil(){
		//acessar o arquivo json com a chave privada
		Resource resource = new ClassPathResource("chavefirebase.json");
		//gera uma credencial no firebase atraves da chave do arquivo
		
		try {
			credenciais = GoogleCredentials.fromStream(resource.getInputStream());
			//cria o storage para manipular os dados FireBase
			storage = StorageOptions.newBuilder().setCredentials(credenciais).build().getService();
		
			
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	//metodo para extrair as extençoes da arquivo
	private String getExtensao(String nomeArquivo) {
		//extrai o trecho do aruivo onde está a extenção
		return nomeArquivo.substring(nomeArquivo.lastIndexOf("."));
		
	}
	//metodo que faz o upload
	public String upload(MultipartFile arquivo) throws IOException {
		// gera uma String aleatória
		String nomeArquivo = UUID.randomUUID().toString() + getExtensao(arquivo.getOriginalFilename());

		//criar um blobId através do nome gerado para o arquivo
		BlobId blobId = BlobId.of(BUCKET_NAME, nomeArquivo);

		//criar um blobInfo através do blobId
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

		// gravar o blobInfo do Storage passando os bytes do arquivo
		storage.create(blobInfo, arquivo.getBytes());

		//retorna a URL do arquivo gerado no Storage
		return String.format(DOWNLOAD_URL, nomeArquivo);
		}
	//metodo para excluir a foto do firebase
		public void deletar(String nomeArquivo) {
		//Retira o prefixo e o sufixo do arquivo
		nomeArquivo = nomeArquivo.replace(PREFIX, "").replace(SUFFIX, "");
		//pega um blob através do nome do arquivo
		Blob blob = storage.get(BlobId.of(BUCKET_NAME, nomeArquivo));
		// deleta o arquivo
		storage.delete(blob.getBlobId());
	}
	
}
