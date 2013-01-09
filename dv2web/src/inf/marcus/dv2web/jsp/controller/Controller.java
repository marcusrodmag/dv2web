package inf.marcus.dv2web.jsp.controller;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

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
  
	/**
	 * Referência para upload de arquivos:
	 * http://stackoverflow.com/questions/2422468/how-to-upload-files-to-server-using-jsp-servlet
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Obtendo a descrição do arquivo de vídeo.
		this.descricaoVideo = request.getParameter("videoname");
		
		//Obtendo o arquivo de vídeo.
	    Part filePart = request.getPart("videofile"); // Recupera <input type="file" name="videofile">
	    this.nomeArquivoVideo = getFilename(filePart);
	    if(this.nomeArquivoVideo == null){
	    	//voltar para index e exibir mensagem de erro: falta arquivo de vídeo.
	    }
	    InputStream filecontent = filePart.getInputStream();
	    /**
	     * TODO: gravar o arquivo em disco e preparar para recodificação. 
	     */
	}
	
	/**
	 * Obtem o nome do arquivo através do cabeçalho da requisição: API V3.0
	 * @param part Objeto contendo o cabeçalho da requisição.
	 * @return O nome do Arquivo.
	 */
	private static String getFilename(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	            return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
	        }
	    }
	    return null;
	}
}