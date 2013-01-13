package inf.marcus.dv2web.web.controller;
import inf.marcus.dv2web.utils.business.flow.VideoConverterFlow;
import inf.marcus.dv2web.utils.business.storage.MediaStore;
import inf.marcus.dv2web.utils.constants.ConstantsAWS;
import inf.marcus.dv2web.web.business.DV2WEBFileUtils;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
public class Controller extends HttpServlet {
	
	private String sendFileName;
	private String errorMessage;
  
	/**
	 * Referência para upload de arquivos:
	 * http://stackoverflow.com/questions/2422468/how-to-upload-files-to-server-using-jsp-servlet
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Obtendo o arquivo de vídeo.
		// Recupera <input type="file" name="videofile">
		Part filePart = request.getPart("videofile");
	    this.sendFileName = this.getFilename(filePart);
	    
	    //Processando arquivo
	    if(this.sendFileName == null || this.sendFileName.equals("")){
	    	this.haddlerError(request, response, "Você deve especificar um arquivo a ser convertido");
	    	return;
	    }
	    DV2WEBFileUtils fileUtils = new DV2WEBFileUtils(this.sendFileName);
	    try{
	    	fileUtils.storeTemporaryFile(filePart.getInputStream());
	    } catch ( IOException e){
	    	this.haddlerError(request, response, "Ocorreu um erro ao gravar o arquivo no servidor da aplicação.");
	    }
	    
	    // Enviando arquivo para o Storage.
	    MediaStore storageClient = new MediaStore(fileUtils.getLocalFile());
	    try{
	    	storageClient.execute();
	    } catch ( IOException e){
	    	this.haddlerError(request, response, "Erro ao enviar arquivo de vídeo para Serviço de Armazenamento.");
	    }
	    
	    // Enviando arquivo para fila de conversão.
	    VideoConverterFlow convertVideo = new VideoConverterFlow(fileUtils.getLocalFile().getName());
	    try{
	    	convertVideo.execute();
	    } catch ( RuntimeException e){
	    	System.err.println("Erro ao converter arquivo de vídeo: " + e.getMessage());
	    	this.haddlerError(request, response, "Erro ao converter arquivo de vídeo.");
	    }
	    
	    this.showVideo(request, response, fileUtils.getLocalFile().getName());
	    
	    storageClient.setACLVideoConverted();
	    fileUtils.deleteLocalFile();	

	    
	    /**
	     * Apresentar o video convertido.
	     * <EMBED src="file.avi" loop="1" height="480" width="640" autostart="true" />
	     */
	    
	}
	/**
	 * Encaminha para a visualização do arquivo de vídeo convertido.
	 */
	private void showVideo(HttpServletRequest request, HttpServletResponse response, String filename){
		String videoURL = ConstantsAWS.AWS_S3_PUBLIC_URL + "/" + ConstantsAWS.AWS_S3_BUCKET_ENCODED_SUBDIR + "/" + filename; 
    	request.setAttribute("videosrc", videoURL);
	    RequestDispatcher disp = request.getRequestDispatcher("view.jsp");
    	try {
			disp.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Obtem o nome do arquivo através do cabeçalho da requisição: API V3.0
	 * @param part Objeto contendo o cabeçalho da requisição.
	 * @return O nome do Arquivo.
	 */
	private String getFilename(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	            return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
	        }
	    }
	    return null;
	}
	
	private void haddlerError (HttpServletRequest request, HttpServletResponse response, String msg){
    	request.setAttribute("errormsg", msg);
	    RequestDispatcher disp = request.getRequestDispatcher("index.jsp");
    	try {
			disp.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}