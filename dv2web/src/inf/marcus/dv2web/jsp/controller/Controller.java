package inf.marcus.dv2web.jsp.controller;
import inf.marcus.dv2web.jsp.business.VideoUtils;
import inf.marcus.dv2web.jsp.business.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
public class Controller extends HttpServlet {
	
	private String descricaoVideo;
	private String nomeArquivoVideo;
	private String errorMessage;
  
	/**
	 * Referência para upload de arquivos:
	 * http://stackoverflow.com/questions/2422468/how-to-upload-files-to-server-using-jsp-servlet
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FileUtils fileUtils = new FileUtils();
		VideoUtils videoUtils = new VideoUtils();

		//Obtendo dados submetidos no formulário.
		this.descricaoVideo = request.getParameter("videoname");
		//Obtendo o arquivo de vídeo.
	    Part filePart = request.getPart("videofile"); // Recupera <input type="file" name="videofile">
	    this.nomeArquivoVideo = this.getFilename(filePart);
	    
	    //Processando arquivo
	    if(this.nomeArquivoVideo == null || this.nomeArquivoVideo.equals("")){
	    	this.haddlerError(request, response, "Você deve especificar um arquivo a ser convertido");
	    	return;
	    }
	    if(! fileUtils.writeOriginalFile(filePart.getInputStream(), this.nomeArquivoVideo)){
	    	this.haddlerError(request, response, "Ocorreu um erro ao manipular o arquivo enviado no servidor de conversão.");
	    	return;
	    }
	    
	    if(! videoUtils.convertVideoFile(this.nomeArquivoVideo)){
	    	this.haddlerError(request, response, "Ocorreu um erro ao manipular o arquivo enviado no servidor de conversão.");
	    	return;
	    }
	    
	    /**
	     * Apresentar o video convertido.
	     * <EMBED src="file.avi" loop="1" height="480" width="640" autostart="true" />
	     */
	    videoUtils.streamVideo(this.nomeArquivoVideo);
	    
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